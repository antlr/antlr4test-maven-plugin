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

import org.apache.maven.plugins.annotations.*;

public class Scenario {
	/**
	 * scenario Name
	 */
	@Parameter
	private String scenarioName;
	/**
	 * grammar Name
	 */
	@Parameter
	private String grammarName;
	/**
	 * case
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
	 * binary
	 */
	@Parameter
	private boolean binary = false;
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
	 * testFileExtension
	 */
	@Parameter(defaultValue = ".txt")
	private String testFileExtension = ".txt";
	/**
	 * example files
	 */
	@Parameter(defaultValue = "/src/test/resources/examples")
	private String exampleFiles = "/src/test/resources/examples";
	/**
	 * packageName
	 */
	@Parameter
	private String packageName;
	/**
	 * basedir dir
	 */
	@Parameter(defaultValue = "${basedir}")
	private File baseDir;
	/**
	 * file encoding
	 */
	@Parameter(defaultValue = "UTF-8")
	private String fileEncoding = "UTF-8";
	/**
	 * Full qualified class name to initialize grammar (Lexer and/or Parser) before test starts
	 */
	@Parameter
	private String grammarInitializer = null;

	public File getBaseDir() {
		return baseDir;
	}

	public boolean getBinary() {
		return binary;
	}

	public CaseInsensitiveType getCaseInsensitiveType() {
		return caseInsensitiveType;
	}

	public String getEntryPoint() {
		return entryPoint;
	}

	public String getExampleFiles() {
		return exampleFiles;
	}

	/**
	 * Returns the File pointing to the directory where example files can be found.
	 *
	 * @return File
	 */
	public File getExampleFilesDir() {
		return new File(getBaseDir() + "/" + getExampleFiles());
	}

	public String getFileEncoding() {
		return fileEncoding;
	}

	public String getGrammarInitializer() {
		return grammarInitializer;
	}

	public String getGrammarName() {
		return grammarName;
	}

	public String getPackageName() {
		return packageName;
	}

	public String getScenarioName() {
		return scenarioName;
	}

	public String getTestFileExtension() {
		return testFileExtension;
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

	public void setBaseDir(File baseDir) {
		this.baseDir = baseDir;
	}

	public void setBinary(boolean binary) {
		this.binary = binary;
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

	public void setFileEncoding(String fileEncoding) {
		this.fileEncoding = fileEncoding;
	}

	public void setGrammarInitializer(String grammarInitializer) {
		this.grammarInitializer = grammarInitializer;
	}

	public void setGrammarName(String grammarName) {
		this.grammarName = grammarName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public void setScenarioName(String scenarioName) {
		this.scenarioName = scenarioName;
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
}
