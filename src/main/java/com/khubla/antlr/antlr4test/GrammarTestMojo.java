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
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

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
	private String exampleFiles = "/src/test/resources/examples";
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
	private String fileEncoding = "UTF-8";

	/**
	 * Full qualified class name to initialize grammar (Lexer and/or Parser) before
	 * test starts
	 */
	@Parameter
	private String grammarInitializer = null;

	/**
	 * List of test scenarios to be executed.
	 */
	@Parameter
	private List<Scenario> scenarios = null;

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
			if (scenarios == null) {
				// No scenario configuration has been given. Creates a default one.
				this.scenarios = new ArrayList<Scenario>();
			}
			if (this.grammarName != null && !"".equals(this.grammarName)) {
				//
				// Create default scenario if grammar name is given.
				// Injects values from plugin configurations.
				// This allows backward compatibility with old configuration style.
				//
				// Does not check if plugin configuration is ok to maintain same behavior as
				// before.
				//
				Scenario defaultScenario = new Scenario();
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
				this.scenarios.add(defaultScenario);
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

	public String getEntryPoint() {
		return entryPoint;
	}

	public String getExampleFiles() {
		return exampleFiles;
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

	public List<Scenario> getScenarios() {
		return Collections.unmodifiableList(scenarios);
	}

	public void setScenarios(List<Scenario> scenarios) {
		this.scenarios = scenarios;
	}

	private void testScenarios() throws Exception {
		Log mojoLogger = getLog();
		// check if baseDir has been set with some value.
		// fix for issue #21
		if (baseDir == null) {
			// If not, set to the current directory
			baseDir = new File(".");
		}
		for (Scenario scenario : scenarios) {
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
				ScenarioExecutor executor = new ScenarioExecutor(scenario, mojoLogger);
				executor.testGrammars();
				executor = null;
			} else {
				mojoLogger.warn("Scenario " + scenario.getScenarioName() + " is disabled. Skipping.");
			}
		}
	}

}
