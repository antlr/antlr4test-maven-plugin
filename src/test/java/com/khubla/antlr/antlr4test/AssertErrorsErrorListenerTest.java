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
import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.easymock.EasyMock.*;

import org.apache.maven.plugin.logging.Log;

/**
 *
 * @author mario.schroeder
 */
public class AssertErrorsErrorListenerTest {
    
    private Scenario scenario = null;
    private Log log = null;
    private AssertErrorsErrorListener classUnderTest = null;
    
    @After
    public void tearDown() {
        this.scenario = null;
        this.log = null;
        this.classUnderTest = null;
    }
    
    @Before
    public void setUp() {
        this.scenario = mock(Scenario.class);
        this.log = mock(Log.class);
        classUnderTest = new AssertErrorsErrorListener(scenario, log);
    }

    /**
     * Test of replacePlaceholders method, of class AssertErrorsErrorListener.
     */
    @Test
    public void testReplacePlaceholders_Unchanged() {
        replay(this.scenario);
        replay(this.log);
        String line = "mismatched input '\\n' expecting 'foo'";
        List<String> lines = Collections.singletonList(line);
        classUnderTest.replacePlaceholders(lines);
        assertEquals(lines.get(0), line);
    }
    
    /**
     * Test of asserts method, of class AssertErrorsErrorListener.
     */
    @Test
    public void testAssertErrors_Matching() throws AssertErrorsException {
        expect(this.scenario.isVerbose()).andReturn(false).times(1);
        replay(this.scenario);
        replay(this.log);
        String line = "mismatched input '\\n' expecting 'foo'";
        classUnderTest.syntaxError(null, null, 1, 7, line, null);
        
        String expected = "line 1:7 "+line;
        List<String> lines = Collections.singletonList(expected);
        classUnderTest.asserts(lines, getClass().getSimpleName());
        assertEquals(lines.get(0), expected);
    }
    
    /**
     * Test of assertErrors method with empty file and an error.
     */
    @Test
    public void testAssertErrors_EmptyFile_Errors() {
        expect(this.scenario.isVerbose()).andReturn(false).times(1);
        replay(this.scenario);
        replay(this.log);
        File errorMessagesFile = new File ( ClassLoader.getSystemResource( "./SampleErrorsEmptyFile.errors").getFile() );
        String line = "mismatched input '\\n' expecting 'foo'";
        classUnderTest.syntaxError(null, null, 1, 7, line, null);
        try
        {
            classUnderTest.assertErrors( errorMessagesFile, "UTF-8" );
            fail("Expected exception AssertErrorsException does not thrown");
        }
        catch ( AssertErrorsException e )
        {
            assertEquals("SampleErrorsEmptyFile.errors : expected 0 errors, but was 1 errors", e.getMessage());
        }        
    }
    
    /**
     * Test of assertErrors method with empty file and no errors.
     */
    @Test
    public void testAssertErrors_EmptyFile_NoErrors() {
        replay(this.scenario);
        replay(this.log);
        File errorMessagesFile = new File ( ClassLoader.getSystemResource( "./SampleErrorsEmptyFile.errors").getFile() );
        try
        {
            classUnderTest.assertErrors( errorMessagesFile, "UTF-8" );
            fail("Expected exception AssertErrorsException does not thrown");
        }
        catch ( AssertErrorsException e )
        {
            assertTrue(e.getMessage().startsWith( "no errors found, but errors file exists" ));
            assertTrue(e.getMessage().endsWith( "SampleErrorsEmptyFile.errors" ));
        }        
    }
    
    /**
     * Test of assertErrors method with non existing file and no errors.
     */
    @Test
    public void testAssertErrors_NonExistingFile_NoErrors() {
        replay(this.scenario);
        replay(this.log);
        File errorMessagesFile = null;
        try
        {
            classUnderTest.assertErrors( errorMessagesFile, "UTF-8" );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            fail("Unexpected exception thrown: " + e.getMessage());
        }        
    }
    
    /**
     * Test of assertErrors method with non existing file and no errors.
     */
    @Test
    public void testAssertErrors_NullFile_Errors() {
        expect(this.scenario.isVerbose()).andReturn(false).times(1);
        replay(this.scenario);
        replay(this.log);
        File errorMessagesFile = null;
        String line = "mismatched input '\\n' expecting 'foo'";
        classUnderTest.syntaxError(null, null, 1, 7, line, null);
        try
        {
            classUnderTest.assertErrors( errorMessagesFile, "UTF-8" );
            fail("Expected exception AssertErrorsException does not thrown");
        }
        catch ( NullPointerException e )
        {
            assertNull(e.getMessage());
        }
        catch ( Exception e )
        {
            fail("Unexpected exception thrown: " + e.getMessage());
        }        
    }
    
    /**
     * Test of assertErrors method with non existing file and no errors.
     */
    @Test
    public void testAssertErrors_NonExistingFile_Errors() {
        expect(this.scenario.isVerbose()).andReturn(false).times(1);
        replay(this.scenario);
        replay(this.log);
        File errorMessagesFile = new File("Foo.bar");
        String line = "mismatched input '\\n' expecting 'foo'";
        classUnderTest.syntaxError(null, null, 1, 7, line, null);
        try
        {
            classUnderTest.assertErrors( errorMessagesFile, "UTF-8" );
            fail("Expected exception AssertErrorsException does not thrown");
        }
        catch ( AssertErrorsException e )
        {
            assertEquals("found 1 errors, but missing file Foo.bar", e.getMessage());
        }        
        catch ( Exception e )
        {
            fail("Unexpected exception thrown: " + e.getMessage());
        }        
    }
    
    /**
     * Test of assertErrors method with non existing file and no errors.
     */
    @Test
    public void testAssertErrors_Mismatching_Errors() {
        expect(this.scenario.isVerbose()).andReturn(false).times(2);
        replay(this.scenario);
        replay(this.log);
        File errorMessagesFile = new File ( ClassLoader.getSystemResource( "./SampleErrorsFileNonEmpty.errors").getFile() );
        String line = "mismatched input '\\n' expecting 'foo'";
        classUnderTest.syntaxError(null, null, 1, 7, line, null);
        classUnderTest.syntaxError(null, null, 13, 5, line, null);
        try
        {
            classUnderTest.assertErrors( errorMessagesFile, "UTF-8" );
            fail("Expected exception AssertErrorsException does not thrown");
        }
        catch ( NullPointerException e )
        {
            assertNull(e.getMessage());
        }
        catch ( AssertErrorsException e )
        {
            assertEquals("SampleErrorsFileNonEmpty.errors : expected (line 2:8 mismatched input '\n" + 
                "' expecting 'foo'), but was (line 13:5 mismatched input '\n" + 
                "' expecting 'foo')", e.getMessage());
        }        
        catch ( Exception e )
        {
            fail("Unexpected exception thrown: " + e.getMessage());
        }        
    }

    /**
     * Test of assertErrors method with non existing file and no errors.
     */
    @Test
    public void testAssertErrors_Matching_Errors() {
        expect(this.scenario.isVerbose()).andReturn(false).times(2);
        replay(this.scenario);
        replay(this.log);
        File errorMessagesFile = new File ( ClassLoader.getSystemResource( "./SampleErrorsFileNonEmpty.errors").getFile() );
        String line = "mismatched input '\\n' expecting 'foo'";
        classUnderTest.syntaxError(null, null, 1, 7, line, null);
        classUnderTest.syntaxError(null, null, 2, 8, line, null);
        try
        {
            classUnderTest.assertErrors( errorMessagesFile, "UTF-8" );
        }
        catch ( Exception e )
        {
            fail("Unexpected exception thrown: " + e.getMessage());
        }        
    }
    /**
     * Test of assertErrors method with non existing file and no errors.
     */
    @Test
    public void testAssertLog() {
        File errorMessagesFile = new File ( ClassLoader.getSystemResource( "./SampleErrorsFileNonEmpty.errors").getFile() );
        String line = "mismatched input '\\n' expecting 'foo'";
        expect(this.scenario.isVerbose()).andReturn(true).times(2);
        this.log.warn("line 1:7 mismatched input '\\n' expecting 'foo'");
        expectLastCall().times(1);
        this.log.warn("line 2:8 mismatched input '\\n' expecting 'foo'");
        expectLastCall().times(1);
        replay(this.scenario);
        replay(this.log);
        classUnderTest.syntaxError(null, null, 1, 7, line, null);
        classUnderTest.syntaxError(null, null, 2, 8, line, null);
        try
        {
            classUnderTest.assertErrors( errorMessagesFile, "UTF-8" );
        }
        catch ( Exception e )
        {
            fail("Unexpected exception thrown: " + e.getMessage());
        }        
    }
}
