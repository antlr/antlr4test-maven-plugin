/*
 * 
 */
package com.khubla.antlr.antlr4test;

import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 * @author mario.schroeder
 */
public class AssertErrorsErrorListenerTest {
    
    private AssertErrorsErrorListener classUnderTest;
    
    @Before
    public void setUp() {
        classUnderTest = new AssertErrorsErrorListener();
    }

    /**
     * Test of replacePlaceholders method, of class AssertErrorsErrorListener.
     */
    @Test
    public void testReplacePlaceholders_Unchanged() {
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
        String line = "mismatched input '\\n' expecting 'foo'";
        classUnderTest.syntaxError(null, null, 1, 7, line, null);
        
        String expected = "line 1:7 "+line;
        List<String> lines = Collections.singletonList(expected);
        classUnderTest.asserts(lines, getClass().getSimpleName());
        assertEquals(lines.get(0), expected);
    }
    
}
