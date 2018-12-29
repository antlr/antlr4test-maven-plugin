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
