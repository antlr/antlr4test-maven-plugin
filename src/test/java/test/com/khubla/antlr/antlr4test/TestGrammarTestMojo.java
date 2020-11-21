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
package test.com.khubla.antlr.antlr4test;

import java.io.*;
import java.net.*;
import java.util.*;

import org.apache.maven.plugin.testing.*;
import org.junit.*;

import com.khubla.antlr.antlr4test.*;

/**
 * @author Tom Everett
 */
public class TestGrammarTestMojo extends AbstractMojoTestCase {
	/**
	 * file
	 */
	private static final String GENERIC_POMFILE = "src/test/resources/generic-pom.xml";
	private static final String CASEINSENSITIVE_lower_POMFILE = "src/test/resources/caseInsensitive_lower-pom.xml";
	private static final String CASEINSENSITIVE_UPPER_POMFILE = "src/test/resources/caseInsensitive_UPPER-pom.xml";
	private static final String GRAMMAR_INIT_POMFILE = "src/test/resources/grammarInitializer-pom.xml";
	private static final String SCENARIO_POMFILE = "src/test/resources/scenarios-pom.xml";
	private static final String BINARY_POMFILE = "src/test/resources/binary-pom.xml";
	/**
	 * Scenario Names
	 */
	// private static final String GENERIC_POM = "generic-pom";
	// private static final String CASE_UPPER = "CaseInsensitive_UPPER";
	// private static final String CASE_LOWER = "CaseInsensitive_lower";
	// private static final String PACKAGE_NO_INIT = "Package-Without-Initialization";
	private static final String PACKAGE_INIT = "Package-Initialize-IgnoreSpaces";
	/**
	 * goal
	 */
	private static final String TEST_GOAL = "test";

	protected String getAbsolutePath(String relativePath) {
		final ClassLoader classLoader = getClass().getClassLoader();
		final URL resource = classLoader.getResource(".");
		final File file = new File(resource.getFile(), relativePath);
		return file.getAbsolutePath();
	}

	@Override
	protected void setUp() throws Exception {
		// required
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		// required
		super.tearDown();
	}

	/**
	 * Basic test of binary
	 */
	public void testBinary() throws Exception {
		try {
			System.out.println("Testing '" + BINARY_POMFILE + "'");
			final File pom = getTestFile(BINARY_POMFILE);
			assertNotNull(pom);
			assertTrue(pom.exists());
			/*
			 * test
			 */
			final GrammarTestMojo grammarTestMojo = (GrammarTestMojo) lookupMojo(TEST_GOAL, pom);
			assertNotNull(grammarTestMojo);
			assertTrue(grammarTestMojo.getBinary());
		} catch (final Exception e) {
			e.printStackTrace();
			Assert.fail("Unexpected exception " + e);
		}
	}

	private void testCaseInsensitiveExecution(String caseInsensitivePomXml) {
		try {
			/*
			 * pom
			 */
			System.out.println("Testing '" + caseInsensitivePomXml + "'");
			final File pom = getTestFile(caseInsensitivePomXml);
			assertNotNull(pom);
			assertTrue(pom.exists());
			/*
			 * test
			 */
			final GrammarTestMojo grammarTestMojo = (GrammarTestMojo) lookupMojo(TEST_GOAL, pom);
			assertNotNull(grammarTestMojo);
			grammarTestMojo.setBaseDir(new File(getAbsolutePath("../..")).getCanonicalFile());
			grammarTestMojo.setFileEncoding("UTF-8");
			grammarTestMojo.execute();
		} catch (final Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	/**
	 * Basic test of case insensitive execution
	 */
	public void testCaseInsensitiveLowerExecution() throws Exception {
		testCaseInsensitiveExecution(CASEINSENSITIVE_lower_POMFILE);
	}

	/**
	 * Basic test of case insensitive execution
	 */
	public void testCaseInsensitiveUpperExecution() throws Exception {
		testCaseInsensitiveExecution(CASEINSENSITIVE_UPPER_POMFILE);
	}

	/**
	 * Basic test of execution
	 */
	public void testGenericExecution() throws Exception {
		try {
			/*
			 * pom
			 */
			System.out.println("Testing '" + GENERIC_POMFILE + "'");
			final File pom = getTestFile(GENERIC_POMFILE);
			assertNotNull(pom);
			assertTrue(pom.exists());
			/*
			 * test
			 */
			final GrammarTestMojo grammarTestMojo = (GrammarTestMojo) lookupMojo(TEST_GOAL, pom);
			assertNotNull(grammarTestMojo);
			grammarTestMojo.setBaseDir(new File(getAbsolutePath("../..")).getCanonicalFile());
			grammarTestMojo.setFileEncoding("UTF-8");
			grammarTestMojo.execute();
		} catch (final Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	/**
	 * Basic test of instantiation
	 */
	public void testGrammarInitializer() throws Exception {
		try {
			System.out.println("Testing '" + GRAMMAR_INIT_POMFILE + "'");
			final File pom = getTestFile(GRAMMAR_INIT_POMFILE);
			assertNotNull(pom);
			assertTrue(pom.exists());
			/*
			 * test
			 */
			final GrammarTestMojo grammarTestMojo = (GrammarTestMojo) lookupMojo(TEST_GOAL, pom);
			assertNotNull(grammarTestMojo);
			grammarTestMojo.setBaseDir(new File(getAbsolutePath("../..")).getCanonicalFile());
			grammarTestMojo.setFileEncoding("UTF-8");
			grammarTestMojo.execute();
		} catch (final Exception e) {
			e.printStackTrace();
			Assert.fail("Unexpected exception " + e);
		}
	}

	/**
	 * Basic test of instantiation
	 */
	public void testInstatiation() throws Exception {
		try {
			final File pom = getTestFile(GENERIC_POMFILE);
			assertNotNull(pom);
			assertTrue(pom.exists());
			final GrammarTestMojo grammarTestMojo = (GrammarTestMojo) lookupMojo(TEST_GOAL, pom);
			assertNotNull(grammarTestMojo);
			assertTrue(grammarTestMojo.isVerbose());
			assertTrue(grammarTestMojo.getExampleFiles().compareTo("src/test/resources/examples") == 0);
			assertTrue(grammarTestMojo.getEntryPoint().compareTo("equation") == 0);
			assertTrue(grammarTestMojo.getTestFileExtension().compareTo(".txt") == 0);
		} catch (final Exception e) {
			e.printStackTrace();
			Assert.fail("Unexpected exception " + e);
		}
	}

	/**
	 * Test scenario based configuration
	 */
	public void testScenarioConfiguration() throws Exception {
		try {
			/*
			 * pom
			 */
			System.out.println("Testing '" + SCENARIO_POMFILE + "'");
			final File pom = getTestFile(SCENARIO_POMFILE);
			assertNotNull(pom);
			assertTrue(pom.exists());
			/*
			 * test
			 */
			final GrammarTestMojo grammarTestMojo = (GrammarTestMojo) lookupMojo(TEST_GOAL, pom);
			assertNotNull(grammarTestMojo);
			final List<Scenario> scenarios = grammarTestMojo.getScenarios();
			assertEquals(5, scenarios.size());
			final HashMap<String, Scenario> scenarioMap = new HashMap<>();
			for (final Scenario scenario : scenarios) {
				// commented to simulate conditions described in issue #21 to replicate the issue
				// https://github.com/antlr/antlr4test-maven-plugin/issues/21
				// scenario.setBaseDir(new File(getAbsolutePath("../..")).getCanonicalFile());
				scenario.setFileEncoding("UTF-8");
				scenarioMap.put(scenario.getScenarioName(), scenario);
			}
			assertEquals("dummy.TestGrammarInitializer", scenarioMap.get(PACKAGE_INIT).getGrammarInitializer());
			grammarTestMojo.execute();
		} catch (final Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
}
