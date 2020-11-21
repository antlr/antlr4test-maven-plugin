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

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import java.io.*;
import java.util.*;

import org.apache.maven.plugin.logging.*;
import org.junit.*;

import com.khubla.antlr.antlr4test.*;

/**
 * @author mario.schroeder
 */
public class AssertErrorsErrorListenerTest {
	private Scenario scenario = null;
	private Log log = null;
	private AssertErrorsErrorListener classUnderTest = null;

	@Before
	public void setUp() {
		scenario = mock(Scenario.class);
		log = mock(Log.class);
		classUnderTest = new AssertErrorsErrorListener(scenario, log);
	}

	@After
	public void tearDown() {
		scenario = null;
		log = null;
		classUnderTest = null;
	}

	/**
	 * Test of assertErrors method with empty file and an error.
	 */
	@Test
	public void testAssertErrors_EmptyFile_Errors() {
		expect(scenario.isVerbose()).andReturn(false).times(1);
		replay(scenario);
		replay(log);
		final File errorMessagesFile = new File(ClassLoader.getSystemResource("./SampleErrorsEmptyFile.errors").getFile());
		final String line = "mismatched input '\\n' expecting 'foo'";
		classUnderTest.syntaxError(null, null, 1, 7, line, null);
		try {
			classUnderTest.assertErrors(errorMessagesFile, "UTF-8");
			fail("Expected exception AssertErrorsException does not thrown");
		} catch (final AssertErrorsException e) {
			assertEquals("SampleErrorsEmptyFile.errors : expected 0 errors, but was 1 errors", e.getMessage());
		}
	}

	/**
	 * Test of assertErrors method with empty file and no errors.
	 */
	@Test
	public void testAssertErrors_EmptyFile_NoErrors() {
		replay(scenario);
		replay(log);
		final File errorMessagesFile = new File(ClassLoader.getSystemResource("./SampleErrorsEmptyFile.errors").getFile());
		try {
			classUnderTest.assertErrors(errorMessagesFile, "UTF-8");
			fail("Expected exception AssertErrorsException does not thrown");
		} catch (final AssertErrorsException e) {
			assertTrue(e.getMessage().startsWith("no errors found, but errors file exists"));
			assertTrue(e.getMessage().endsWith("SampleErrorsEmptyFile.errors"));
		}
	}

	/**
	 * Test of asserts method, of class AssertErrorsErrorListener.
	 */
	@Test
	public void testAssertErrors_Matching() throws AssertErrorsException {
		expect(scenario.isVerbose()).andReturn(false).times(1);
		replay(scenario);
		replay(log);
		final String line = "mismatched input '\\n' expecting 'foo'";
		classUnderTest.syntaxError(null, null, 1, 7, line, null);
		final String expected = "line 1:7 " + line;
		final List<String> lines = Collections.singletonList(expected);
		classUnderTest.asserts(lines, getClass().getSimpleName());
		assertEquals(lines.get(0), expected);
	}

	/**
	 * Test of assertErrors method with non existing file and no errors.
	 */
	@Test
	public void testAssertErrors_Matching_Errors() {
		expect(scenario.isVerbose()).andReturn(false).times(2);
		replay(scenario);
		replay(log);
		final File errorMessagesFile = new File(ClassLoader.getSystemResource("./SampleErrorsFileNonEmpty.errors").getFile());
		final String line = "mismatched input '\\n' expecting 'foo'";
		classUnderTest.syntaxError(null, null, 1, 7, line, null);
		classUnderTest.syntaxError(null, null, 2, 8, line, null);
		try {
			classUnderTest.assertErrors(errorMessagesFile, "UTF-8");
		} catch (final Exception e) {
			fail("Unexpected exception thrown: " + e.getMessage());
		}
	}

	/**
	 * Test of assertErrors method with non existing file and no errors.
	 */
	@Test
	public void testAssertErrors_Mismatching_Errors() {
		expect(scenario.isVerbose()).andReturn(false).times(2);
		replay(scenario);
		replay(log);
		final File errorMessagesFile = new File(ClassLoader.getSystemResource("./SampleErrorsFileNonEmpty.errors").getFile());
		final String line = "mismatched input '\\n' expecting 'foo'";
		classUnderTest.syntaxError(null, null, 1, 7, line, null);
		classUnderTest.syntaxError(null, null, 13, 5, line, null);
		try {
			classUnderTest.assertErrors(errorMessagesFile, "UTF-8");
			fail("Expected exception AssertErrorsException does not thrown");
		} catch (final NullPointerException e) {
			assertNull(e.getMessage());
		} catch (final AssertErrorsException e) {
			assertEquals("SampleErrorsFileNonEmpty.errors : expected (line 2:8 mismatched input '\n" + "' expecting 'foo'), but was (line 13:5 mismatched input '\n" + "' expecting 'foo')",
					e.getMessage());
		} catch (final Exception e) {
			fail("Unexpected exception thrown: " + e.getMessage());
		}
	}

	/**
	 * Test of assertErrors method with non existing file and no errors.
	 */
	@Test
	public void testAssertErrors_NonExistingFile_Errors() {
		expect(scenario.isVerbose()).andReturn(false).times(1);
		replay(scenario);
		replay(log);
		final File errorMessagesFile = new File("Foo.bar");
		final String line = "mismatched input '\\n' expecting 'foo'";
		classUnderTest.syntaxError(null, null, 1, 7, line, null);
		try {
			classUnderTest.assertErrors(errorMessagesFile, "UTF-8");
			fail("Expected exception AssertErrorsException does not thrown");
		} catch (final AssertErrorsException e) {
			assertEquals("found 1 errors, but missing file Foo.bar", e.getMessage());
		} catch (final Exception e) {
			fail("Unexpected exception thrown: " + e.getMessage());
		}
	}

	/**
	 * Test of assertErrors method with non existing file and no errors.
	 */
	@Test
	public void testAssertErrors_NonExistingFile_NoErrors() {
		replay(scenario);
		replay(log);
		final File errorMessagesFile = null;
		try {
			classUnderTest.assertErrors(errorMessagesFile, "UTF-8");
		} catch (final Exception e) {
			e.printStackTrace();
			fail("Unexpected exception thrown: " + e.getMessage());
		}
	}

	/**
	 * Test of assertErrors method with non existing file and no errors.
	 */
	@Test
	public void testAssertErrors_NullFile_Errors() {
		expect(scenario.isVerbose()).andReturn(false).times(1);
		replay(scenario);
		replay(log);
		final File errorMessagesFile = null;
		final String line = "mismatched input '\\n' expecting 'foo'";
		classUnderTest.syntaxError(null, null, 1, 7, line, null);
		try {
			classUnderTest.assertErrors(errorMessagesFile, "UTF-8");
			fail("Expected exception AssertErrorsException does not thrown");
		} catch (final NullPointerException e) {
			assertNull(e.getMessage());
		} catch (final Exception e) {
			fail("Unexpected exception thrown: " + e.getMessage());
		}
	}

	/**
	 * Test of assertErrors method with non existing file and no errors.
	 */
	@Test
	public void testAssertLog() {
		final File errorMessagesFile = new File(ClassLoader.getSystemResource("./SampleErrorsFileNonEmpty.errors").getFile());
		final String line = "mismatched input '\\n' expecting 'foo'";
		expect(scenario.isVerbose()).andReturn(true).times(2);
		log.warn("line 1:7 mismatched input '\\n' expecting 'foo'");
		expectLastCall().times(1);
		log.warn("line 2:8 mismatched input '\\n' expecting 'foo'");
		expectLastCall().times(1);
		replay(scenario);
		replay(log);
		classUnderTest.syntaxError(null, null, 1, 7, line, null);
		classUnderTest.syntaxError(null, null, 2, 8, line, null);
		try {
			classUnderTest.assertErrors(errorMessagesFile, "UTF-8");
		} catch (final Exception e) {
			fail("Unexpected exception thrown: " + e.getMessage());
		}
	}

	/**
	 * Test of replacePlaceholders method, of class AssertErrorsErrorListener.
	 */
	@Test
	public void testReplacePlaceholders_Unchanged() {
		replay(scenario);
		replay(log);
		final String line = "mismatched input '\\n' expecting 'foo'";
		final List<String> lines = Collections.singletonList(line);
		classUnderTest.replacePlaceholders(lines);
		assertEquals(lines.get(0), line);
	}
}
