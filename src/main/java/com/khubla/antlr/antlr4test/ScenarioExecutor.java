/*
 [The "BSD license"]
 Copyright (c) 2020 Gil Cesar Faria
 All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions
 are met:
 1. Redistributions of source code must retain the above copyright
    notice, this list of conditions and the following disclaimer.
 2. Redistributions in binary form must reproduce the above copyright
    notice, this list of conditions and the following disclaimer in the
    documentation and/or other materials provided with the distribution.
 3. The name of the author may not be used to endorse or promote products
    derived from this software without specific prior written permission.

 THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.khubla.antlr.antlr4test;

import java.io.*;
import java.lang.reflect.*;
import java.net.*;
import java.nio.charset.*;
import java.util.*;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import org.apache.maven.plugin.logging.*;
import org.bitbucket.cowwoc.diffmatchpatch.*;
import org.codehaus.plexus.util.*;

import com.khubla.antlr.antlr4test.charstream.*;
import com.khubla.antlr.antlr4test.filestream.*;

public class ScenarioExecutor {
	private Scenario scenario = null;
	private Log log = null;
	private GrammarTestMojo mojo = null;
	private final HashMap<URL, ClassLoader> classLoaderMap = new HashMap<>();

	public ScenarioExecutor(GrammarTestMojo mojo, Scenario scenario, Log log) {
		this.mojo = mojo;
		this.scenario = scenario;
		this.log = log;
	}

	private ClassLoader getClassLoader(String path) throws MalformedURLException, ClassNotFoundException {
		/*
		 * create a ClassLoader child of Thread.currentThread().getContextClassLoader().
		 */
		return getClassLoader(path, Thread.currentThread().getContextClassLoader());
	}

	/**
	 * build a ClassLoader that can find the files we need
	 */
	private ClassLoader getClassLoader(String path, ClassLoader parent) throws MalformedURLException, ClassNotFoundException {
		final URL antlrGeneratedURL = new File(path).toURI().toURL();
		/*
		 * check if ClassLoader for this URL was already created.
		 */
		ClassLoader ret = classLoaderMap.get(antlrGeneratedURL);
		if (ret == null) {
			// if not, create a new one and cache it to avoid recreating.
			final URL[] urls = new URL[] { antlrGeneratedURL };
			ret = new URLClassLoader(urls, parent);
			classLoaderMap.put(antlrGeneratedURL, ret);
		}
		return ret;
	}

	/**
	 * test a single grammar
	 */
	private void testGrammar(Scenario scenario, File grammarFile) throws Exception {
		/*
		 * figure out class names
		 */
		String nn = scenario.getGrammarName();
		if ((null != scenario.getPackageName()) && !"".equals(scenario.getPackageName())) {
			nn = scenario.getPackageName() + "." + scenario.getGrammarName();
		}
		final String lexerClassName = nn + "Lexer";
		final String parserClassName = nn + "Parser";
		if (scenario.isVerbose()) {
			log.info("Lexer classname is: " + lexerClassName);
			log.info("Parser classname is: " + parserClassName);
		}
		/*
		 * ClassLoader
		 */
		// create grammarClassLoader as child of current thread's context ClassLoader
		final ClassLoader grammarClassLoader = getClassLoader(mojo.getOutputDirectory());
		// testClassLoader should be grammarClassLoader's child so test classes can find
		// grammar classes
		final ClassLoader testClassLoader = getClassLoader(mojo.getTestOutputDirectory(), grammarClassLoader);
		/*
		 * get the classes we need
		 */
		final Class<? extends Lexer> lexerClass = grammarClassLoader.loadClass(lexerClassName).asSubclass(Lexer.class);
		final Class<? extends Parser> parserClass = grammarClassLoader.loadClass(parserClassName).asSubclass(Parser.class);
		Class<? extends GrammarInitializer> initializerClass = null;
		final String grammarInitializer = scenario.getGrammarInitializer();
		if ((grammarInitializer != null) && !"".equals(grammarInitializer)) {
			initializerClass = testClassLoader.loadClass(grammarInitializer).asSubclass(GrammarInitializer.class);
		}
		/*
		 * get ctors
		 */
		final Constructor<?> lexerConstructor = lexerClass.getConstructor(CharStream.class);
		final Constructor<?> parserConstructor = parserClass.getConstructor(TokenStream.class);
		log.info("Parsing :" + grammarFile.getAbsolutePath());
		CharStream antlrCharStream;
		/*
		 * case
		 */
		if (scenario.getCaseInsensitiveType() == CaseInsensitiveType.None) {
			antlrCharStream = CharStreams.fromPath(grammarFile.toPath(), Charset.forName(scenario.getFileEncoding()));
		} else {
			antlrCharStream = new AntlrCaseInsensitiveFileStream(grammarFile.getAbsolutePath(), scenario.getFileEncoding(), scenario.getCaseInsensitiveType());
		}
		/*
		 * binary
		 */
		if (true == scenario.getBinary()) {
			antlrCharStream = new BinaryCharStream(antlrCharStream);
		}
		/*
		 * build lexer
		 */
		final AssertErrorsErrorListener assertErrorsErrorListener = new AssertErrorsErrorListener(this.scenario, log);
		Lexer lexer = (Lexer) lexerConstructor.newInstance(antlrCharStream);
		lexer.addErrorListener(assertErrorsErrorListener);
		/*
		 * build token stream
		 */
		final CommonTokenStream tokens = new CommonTokenStream(lexer);
		/*
		 * build parser
		 */
		Parser parser = (Parser) parserConstructor.newInstance(tokens);
		parser.addErrorListener(assertErrorsErrorListener);
		/*
		 * initializer lexer and parser
		 */
		if (initializerClass != null) {
			log.info(initializerClass.getName());
			final GrammarInitializer initializer = initializerClass.getDeclaredConstructor().newInstance();
			initializer.initialize(lexer, parser);
		}
		/*
		 * show tokens
		 */
		if (scenario.isVerbose()) {
			tokens.fill();
			log.info("Token List: ");
			for (final Token tok : tokens.getTokens()) {
				final String logmessage = tok.toString() + " {" + tokToHex(tok) + "}";
				log.info(logmessage);
			}
		}
		final Method method = parserClass.getMethod(scenario.getEntryPoint());
		ParserRuleContext parserRuleContext = (ParserRuleContext) method.invoke(parser);
		assertErrorsErrorListener.assertErrors(new File(grammarFile.getAbsolutePath() + GrammarTestMojo.ERRORS_SUFFIX), scenario.getFileEncoding());
		/*
		 * show the tree
		 */
		if (scenario.isShowTree()) {
			final String lispTree = Trees.toStringTree(parserRuleContext, parser);
			log.info("Parsed Tree: ");
			log.info(lispTree);
		}
		/*
		 * check syntax
		 */
		final File treeFile = new File(grammarFile.getAbsolutePath() + GrammarTestMojo.TREE_SUFFIX);
		if (treeFile.exists()) {
			final String lispTree = Trees.toStringTree(parserRuleContext, parser);
			if (null != lispTree) {
				final String treeFileData = FileUtils.fileRead(treeFile, scenario.getFileEncoding());
				if (null != treeFileData) {
					if (0 != treeFileData.compareTo(lispTree)) {
						final StringBuilder sb = new StringBuilder("Parse tree does not match '" + treeFile.getName() + "'. Differences: ");
						for (final DiffMatchPatch.Diff diff : new DiffMatchPatch().diffMain(treeFileData, lispTree)) {
							sb.append(diff.toString());
							sb.append(", ");
						}
						throw new Exception(sb.toString());
					} else {
						log.info("Parse tree for '" + grammarFile.getName() + "' matches '" + treeFile.getName() + "'");
					}
				}
			}
		}
		/*
		 * yup
		 */
		parser = null;
		lexer = null;
		parserRuleContext = null;
		antlrCharStream = null;
	}

	public void testGrammars() throws Exception {
		/*
		 * iterate examples
		 */
		final List<File> exampleFiles = FileUtil.getAllFiles(scenario.getExampleFilesDir().getAbsolutePath());
		if (null != exampleFiles) {
			for (final File file : exampleFiles) {
				/*
				 * test grammar
				 */
				if ((!file.getName().endsWith(GrammarTestMojo.ERRORS_SUFFIX)) && (!file.getName().endsWith(GrammarTestMojo.TREE_SUFFIX))) {
					/*
					 * file extension
					 */
					if ((scenario.getTestFileExtension() == null) || ((scenario.getTestFileExtension() != null) && (file.getName().endsWith(scenario.getTestFileExtension())))) {
						testGrammar(scenario, file);
					}
				}
				/*
				 * gc
				 */
				System.gc();
			}
		}
	}

	/**
	 * return a string of hex bytes for a token
	 *
	 * @param token Token
	 * @return hex bytes
	 */
	private String tokToHex(Token token) {
		String ret = "";
		token.getInputStream().seek(0);
		boolean first = true;
		for (int i = token.getStartIndex(); i < (token.getStopIndex() + 1); i++) {
			if (true == first) {
				first = false;
			} else {
				ret += ",";
			}
			final int t = token.getInputStream().LA(i + 1);
			ret += "0x" + String.format("%02X", (byte) t);
		}
		return ret;
	}
}
