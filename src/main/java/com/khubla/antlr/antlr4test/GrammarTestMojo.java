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

import java.io.*;
import java.net.*;
import java.util.*;

import org.apache.maven.plugin.*;
import org.apache.maven.plugin.logging.*;
import org.apache.maven.plugins.annotations.*;
import org.apache.maven.plugins.annotations.Mojo;

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
	 * case
	 */
	@Parameter
	private CaseInsensitiveType caseInsensitiveType = CaseInsensitiveType.None;
	/**
	 * binary
	 */
	@Parameter
	private boolean binary = false;
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
	private String exampleFiles = "/src/test/resources/examples";
	/**
	 * packageName
	 */
	@Parameter
	private String packageName;
	/**
	 * testFileExtension
	 */
	@Parameter
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
	private String fileEncoding = "UTF-8";
	/**
	 * Full qualified class name to initialize grammar (Lexer and/or Parser) before test starts
	 */
	@Parameter
	private String grammarInitializer = null;
	/**
	 * List of test scenarios to be executed.
	 */
	@Parameter
	private List<Scenario> scenarios = null;
	/**
	 * read outputDirectory from pom project.build.outputDirectory
	 */
	@Parameter(defaultValue = "${project.build.outputDirectory}", readonly = true)
	private String outputDirectory = "/target/classes";
	/**
	 * read testOutputDirectory from pom project.build.testOutputDirectory
	 */
	@Parameter(defaultValue = "${project.build.testOutputDirectory}", readonly = true)
	private String testOutputDirectory = "/target/test-classes";

	/**
	 * ctor
	 *
	 * @throws MalformedURLException exception for malformed url *eye roll*
	 */
	public GrammarTestMojo() throws MalformedURLException {
	}

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		try {
			/*
			 * No scenario configuration has been given. Creates a default one.
			 */
			if (scenarios == null) {
				scenarios = new ArrayList<Scenario>();
			}
			if ((grammarName != null) && !"".equals(grammarName)) {
				//
				// Create default scenario if grammar name is given.
				// Injects values from plugin configurations.
				// This allows backward compatibility with old configuration style.
				//
				// Does not check if plugin configuration is ok to maintain same behavior as
				// before.
				//
				final Scenario defaultScenario = new Scenario();
				defaultScenario.setScenarioName("Default Scenario");
				defaultScenario.setGrammarName(grammarName);
				defaultScenario.setCaseInsensitiveType(caseInsensitiveType);
				defaultScenario.setBaseDir(baseDir);
				defaultScenario.setEnabled(enabled);
				defaultScenario.setEntryPoint(entryPoint);
				defaultScenario.setTestFileExtension(testFileExtension);
				defaultScenario.setExampleFiles(exampleFiles);
				defaultScenario.setPackageName(packageName);
				defaultScenario.setFileEncoding(fileEncoding);
				defaultScenario.setGrammarInitializer(grammarInitializer);
				defaultScenario.setShowTree(showTree);
				defaultScenario.setVerbose(verbose);
				defaultScenario.setBinary(binary);
				scenarios.add(defaultScenario);
			}
			/*
			 * test grammars
			 */
			testScenarios();
		} catch (final Exception e) {
			e.printStackTrace();
			throw new MojoExecutionException("Unable execute mojo", e);
		}
	}

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

	public String getFileEncoding() {
		return fileEncoding;
	}

	public String getGrammarInitializer() {
		return grammarInitializer;
	}

	public String getGrammarName() {
		return grammarName;
	}

	public String getOutputDirectory() {
		return outputDirectory;
	}

	public String getPackageName() {
		return packageName;
	}

	public List<Scenario> getScenarios() {
		return Collections.unmodifiableList(scenarios);
	}

	public String getTestFileExtension() {
		return testFileExtension;
	}

	public String getTestOutputDirectory() {
		return testOutputDirectory;
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

	public void setOutputDirectory(String outputDirectory) {
		this.outputDirectory = outputDirectory;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public void setScenarios(List<Scenario> scenarios) {
		this.scenarios = scenarios;
	}

	public void setShowTree(boolean showTree) {
		this.showTree = showTree;
	}

	public void setTestFileExtension(String testFileExtension) {
		this.testFileExtension = testFileExtension;
	}

	public void setTestOutputDirectory(String testOutputDirectory) {
		this.testOutputDirectory = testOutputDirectory;
	}

	public void setVerbose(boolean verbose) {
		this.verbose = verbose;
	}

	private void testScenarios() throws Exception {
		final Log mojoLogger = getLog();
		// check if baseDir has been set with some value.
		// fix for issue #21
		if (baseDir == null) {
			// If not, set to the current directory
			baseDir = new File(".");
		}
		for (final Scenario scenario : scenarios) {
			/*
			 * drop message
			 */
			mojoLogger.info("Evaluating Scenario: " + scenario.getScenarioName());
			// fix for issue #21
			if (scenario.getBaseDir() == null) {
				// The base dir does not get its default value
				// in Scenario class under certain unknown conditions
				// if it is the case, inject the default value from Mojo
				scenario.setBaseDir(baseDir);
			}
			// fix for issue #21
			if (scenario.getExampleFiles() == null) {
				// The examples dir does not get its default value
				// in Scenario class under certain unknown conditions
				// if it is the case, inject the default value from Mojo
				scenario.setExampleFiles(exampleFiles);
			}
			if (scenario.isVerbose()) {
				mojoLogger.info("baseDir: " + scenario.getBaseDir());
				mojoLogger.info("exampleFiles: " + scenario.getExampleFiles());
				mojoLogger.info("binary: " + binary);
				mojoLogger.info("case: " + caseInsensitiveType);
				mojoLogger.info("encoding: " + fileEncoding);
				// only check errors if scenario is enabled
				// so other scenarios are not prevented of being executed.
				if (scenario.isEnabled()) {
					if (!scenario.getBaseDir().exists()) {
						throw new MojoExecutionException("baseDir '" + baseDir.getAbsolutePath() + "' does not exist");
					}
					if (!scenario.getExampleFilesDir().exists()) {
						throw new MojoExecutionException("exampleFiles directory'" + exampleFiles + "' does not exist");
					}
				}
			}
			if (scenario.isEnabled()) {
				ScenarioExecutor executor = new ScenarioExecutor(this, scenario, mojoLogger);
				executor.testGrammars();
				executor = null;
			} else {
				mojoLogger.warn("Scenario " + scenario.getScenarioName() + " is disabled. Skipping.");
			}
		}
	}
}
