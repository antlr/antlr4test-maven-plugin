/*
 [The "BSD license"]
 Copyright (c) 2014 Tom Everett
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

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.Charset;
import java.util.List;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.Trees;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch;
import org.codehaus.plexus.util.FileUtils;

/**
 * @author Tom Everett
 */
@Mojo(name = "test", defaultPhase = LifecyclePhase.TEST, requiresProject = true, threadSafe = false)
public class GrammarTestMojo extends AbstractMojo {
   /**
    * errors file
    */
   protected static final String ERRORS_SUFFIX = ".errors";
   /**
    * tree file
    */
   protected static final String TREE_SUFFIX = ".tree";
   /**
    * grammar Name
    */
   @Parameter
   private String grammarName;
   /**
    * grammar Name
    */
   @Parameter
   private CaseInsensitiveType caseInsensitiveType = CaseInsensitiveType.None;
   /**
    * entry point method on the parser
    */
   @Parameter
   private String entryPoint;
   /**
    * verbose
    */
   @Parameter
   private boolean enabled = true;
   /**
    * verbose
    */
   @Parameter
   private boolean verbose = false;
   /**
    * show LISP tree
    */
   @Parameter
   private boolean showTree = true;
   /**
    * example files
    */
   @Parameter(defaultValue = "/src/test/resources/examples")
   private String exampleFiles;
   /**
    * packageName
    */
   @Parameter
   private String packageName;
   /**
    * testFileExtension
    */
   private String testFileExtension = null;
   /**
    * basedir dir
    */
   @Parameter(defaultValue = "${basedir}")
   private File baseDir;
   /**
    * file encoding
    */
   @Parameter(defaultValue = "UTF-8")
   private String fileEncoding;

   /**
    * ctor
    */
   public GrammarTestMojo() throws MalformedURLException {
   }

   @Override
   public void execute() throws MojoExecutionException, MojoFailureException {
      try {
         /*
          * drop message
          */
         if (verbose) {
            System.out.println("baseDir: " + baseDir);
            System.out.println("exampleFiles: " + exampleFiles);
            if (false == baseDir.exists()) {
               throw new MojoExecutionException("baseDir '" + baseDir.getAbsolutePath() + "' does not exist");
            }
            if (false == getExampleFilesDir().exists()) {
               throw new MojoExecutionException("exampleFiles directory'" + exampleFiles + "' does not exist");
            }
         }
         /*
          * test grammars
          */
         if (enabled) {
            testGrammars();
         }
      } catch (final Exception e) {
         e.printStackTrace();
         throw new MojoExecutionException("Unable execute mojo", e);
      }
   }

   public File getBaseDir() {
      return baseDir;
   }

   /**
    * get a classloader that can find the files we need
    */
   private ClassLoader getClassLoader() throws MalformedURLException, ClassNotFoundException {
      final URL antlrGeneratedURL = new File(baseDir + "/target/classes").toURI().toURL();
      final URL[] urls = new URL[] { antlrGeneratedURL };
      return new URLClassLoader(urls, Thread.currentThread().getContextClassLoader());
   }

   public String getEntryPoint() {
      return entryPoint;
   }

   public String getExampleFiles() {
      return exampleFiles;
   }

   private File getExampleFilesDir() {
      return new File(baseDir + "/" + exampleFiles);
   }

   public String getGrammarName() {
      return grammarName;
   }

   public String getPackageName() {
      return packageName;
   }

   public String getTestFileExtension() {
      return testFileExtension;
   }

   public CaseInsensitiveType getCaseInsensitiveType() {
      return caseInsensitiveType;
   }

   public boolean isEnabled() {
      return enabled;
   }

   public boolean isShowTree() {
      return showTree;
   }

   public boolean isVerbose() {
      return verbose;
   }

   public String getFileEncoding() {
      return fileEncoding;
   }

   public void setBaseDir(File baseDir) {
      this.baseDir = baseDir;
   }

   public void setCaseInsensitiveType(CaseInsensitiveType caseInsensitiveType) {
      this.caseInsensitiveType = caseInsensitiveType;
   }

   public void setEnabled(boolean enabled) {
      this.enabled = enabled;
   }

   public void setEntryPoint(String entryPoint) {
      this.entryPoint = entryPoint;
   }

   public void setExampleFiles(String exampleFiles) {
      this.exampleFiles = exampleFiles;
   }

   public void setGrammarName(String grammarName) {
      this.grammarName = grammarName;
   }

   public void setPackageName(String packageName) {
      this.packageName = packageName;
   }

   public void setShowTree(boolean showTree) {
      this.showTree = showTree;
   }

   public void setTestFileExtension(String testFileExtension) {
      this.testFileExtension = testFileExtension;
   }

   public void setVerbose(boolean verbose) {
      this.verbose = verbose;
   }

   public void setFileEncoding(String fileEncoding) {
      this.fileEncoding = fileEncoding;
   }

   /**
    * test a single grammar
    */
   private void testGrammar(File grammarFile) throws Exception {
      /*
       * figure out class names
       */
      String nn = grammarName;
      if (null != packageName) {
         nn = packageName + "." + grammarName;
      }
      final String lexerClassName = nn + "Lexer";
      final String parserClassName = nn + "Parser";
      if (verbose) {
         System.out.println("Lexer classname is: " + lexerClassName);
         System.out.println("Parser classname is: " + parserClassName);
      }
      /*
       * classloader
       */
      final ClassLoader classLoader = getClassLoader();
      /*
       * get the classes we need
       */
      final Class<? extends Lexer> lexerClass = classLoader.loadClass(lexerClassName).asSubclass(Lexer.class);
      final Class<? extends Parser> parserClass = classLoader.loadClass(parserClassName).asSubclass(Parser.class);
      /*
       * get ctors
       */
      final Constructor<?> lexerConstructor = lexerClass.getConstructor(CharStream.class);
      final Constructor<?> parserConstructor = parserClass.getConstructor(TokenStream.class);
      System.out.println("Parsing :" + grammarFile.getAbsolutePath());
      CharStream antlrFileStream;
      if (caseInsensitiveType == CaseInsensitiveType.None) {
         antlrFileStream = CharStreams.fromPath(grammarFile.toPath(), Charset.forName(fileEncoding));
      } else {
         antlrFileStream = new AntlrCaseInsensitiveFileStream(grammarFile.getAbsolutePath(), fileEncoding, caseInsensitiveType);
      }
      final AssertErrorsErrorListener assertErrorsErrorListener = new AssertErrorsErrorListener();
      Lexer lexer = (Lexer) lexerConstructor.newInstance(antlrFileStream);
      lexer.addErrorListener(assertErrorsErrorListener);
      final CommonTokenStream tokens = new CommonTokenStream(lexer);
      if (verbose) {
         tokens.fill();
         for (final Object tok : tokens.getTokens()) {
            System.out.println(tok);
         }
      }
      /*
       * get parser
       */
      Parser parser = (Parser) parserConstructor.newInstance(tokens);
      parser.addErrorListener(assertErrorsErrorListener);
      final Method method = parserClass.getMethod(entryPoint);
      ParserRuleContext parserRuleContext = (ParserRuleContext) method.invoke(parser);
      assertErrorsErrorListener.assertErrors(new File(grammarFile.getAbsolutePath() + ERRORS_SUFFIX), fileEncoding);
      /*
       * show the tree
       */
      if (showTree) {
         final String lispTree = Trees.toStringTree(parserRuleContext, parser);
         System.out.println(lispTree);
      }
      /*
       * check syntax
       */
      final File treeFile = new File(grammarFile.getAbsolutePath() + TREE_SUFFIX);
      if (treeFile.exists()) {
         final String lispTree = Trees.toStringTree(parserRuleContext, parser);
         if (null != lispTree) {
            final String treeFileData = FileUtils.fileRead(treeFile, fileEncoding);
            if (null != treeFileData) {
               if (0 != treeFileData.compareTo(lispTree)) {
                  StringBuilder sb = new StringBuilder("Parse tree does not match '" + treeFile.getName() + "'. Differences: ");
                  for (DiffMatchPatch.Diff diff : new DiffMatchPatch().diffMain(treeFileData, lispTree)) {
                     sb.append(diff.toString());
                     sb.append(", ");
                  }
                  throw new Exception(sb.toString());
               } else {
                  System.out.println("Parse tree for '" + grammarFile.getName() + "' matches '" + treeFile.getName() + "'");
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
      antlrFileStream = null;
   }

   private void testGrammars() throws Exception {
      /*
       * iterate examples
       */
      final List<File> exampleFiles = FileUtil.getAllFiles(getExampleFilesDir().getAbsolutePath());
      if (null != exampleFiles) {
         for (final File file : exampleFiles) {
            /*
             * test grammar
             */
            if ((!file.getName().endsWith(ERRORS_SUFFIX)) && (!file.getName().endsWith(TREE_SUFFIX))) {
               /*
                * file extension
                */
               if ((testFileExtension == null) || ((testFileExtension != null) && (file.getName().endsWith(testFileExtension)))) {
                  testGrammar(file);
               }
            }
            /*
             * gc
             */
            System.gc();
         }
      }
   }
}
