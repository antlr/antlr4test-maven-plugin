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

import java.io.File;
import java.net.URL;

import org.apache.maven.plugin.testing.AbstractMojoTestCase;

import com.khubla.antlr.antlr4test.GrammarTestMojo;

import junit.framework.Assert;

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
   public void testInstatiation() throws Exception {
      try {
         final File pom = getTestFile(GENERIC_POMFILE);
         assertNotNull(pom);
         assertTrue(pom.exists());
         final GrammarTestMojo grammarTestMojo = (GrammarTestMojo) lookupMojo(TEST_GOAL, pom);
         assertNotNull(grammarTestMojo);
         assertTrue(grammarTestMojo.isVerbose() == true);
         assertTrue(grammarTestMojo.getExampleFiles().compareTo("src/test/resources/examples") == 0);
         assertTrue(grammarTestMojo.getEntryPoint().compareTo("equation") == 0);
         assertTrue(grammarTestMojo.getTestFileExtension().compareTo(".txt") == 0);
      } catch (final Exception e) {
         e.printStackTrace();
         Assert.fail();
      }
   }
}
