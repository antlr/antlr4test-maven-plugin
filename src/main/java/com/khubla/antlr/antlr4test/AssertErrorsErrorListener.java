package com.khubla.antlr.antlr4test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

public class AssertErrorsErrorListener extends BaseErrorListener {
   protected static final String CHAR_CR_PLACEHOLDER_REGEXP = "\\\\r"; // to match \r
   protected static final String CHAR_CR = String.valueOf((char) 13);
   protected static final String CHAR_LF_PLACEHOLDER_REGEXP = "\\\\n"; // to match \n
   protected static final String CHAR_LF = String.valueOf((char) 10);
   protected static final String LITERAL_BACKSLASH_R_PLACEHOLDER = "literal-backslash-r";
   protected static final String LITERAL_BACKSLASH_R = "\\\\r";
   protected static final String LITERAL_BACKSLASH_N_PLACEHOLDER = "literal-backslash-n";
   protected static final String LITERAL_BACKSLASH_N = "\\\\n";
   protected List<String> errorMessages = new ArrayList<>();

   public void assertErrors(File errorMessagesFile, String encoding) throws AssertErrorsException {
      if (!errorMessages.isEmpty()) {
         List<String> expectedErrorMessages = null;
         String errorMessageFileName = errorMessagesFile.getName();
         try {
            expectedErrorMessages = FileUtil.getNonEmptyLines(errorMessagesFile, encoding);
         } catch (final FileNotFoundException ex) {
            throw new AssertErrorsException(String.format("found %d errors, but missing file %s", errorMessages.size(), errorMessageFileName), ex);
         } catch (final IOException ex) {
            throw new AssertErrorsException(String.format("found %d errors, unable to read file %s", errorMessages.size(), errorMessageFileName), ex);
         }
         asserts(expectedErrorMessages, errorMessageFileName);
      } else {
         if (errorMessagesFile.exists()) {
            throw new AssertErrorsException(String.format("no errors found, but errors file exists %s", errorMessagesFile.getAbsolutePath()));
         }
      }
   }

    void asserts(List<String> expectedErrorMessages, String errorMessageFileName) throws AssertErrorsException {
        List<String> expectedErrors = replacePlaceholders(expectedErrorMessages);
        List<String> actualErrors = replacePlaceholders(errorMessages);
        int expectedSize = expectedErrors.size();
        int actualSize = actualErrors.size();
        if (expectedSize != actualSize) {
            throw new AssertErrorsException(String.format("%s : expected %d errors, but was %d errors", errorMessageFileName, expectedSize, actualSize));
        }
        for (int i = 0; i < expectedSize; i++) {
            final String expectedError = expectedErrors.get(i);
            final String error = actualErrors.get(i);
            if (!expectedError.equals(error)) {
                throw new AssertErrorsException(String.format("%s : expected (%s), but was (%s)", errorMessageFileName, expectedError, error));
            }
        }
    }

   protected List<String> replacePlaceholders(List<String> stringList) {
      final List<String> replacedStringList = new ArrayList<>();
      for (final String vString : stringList) {
         replacedStringList.add(replacePlaceholders(vString));
      }
      return replacedStringList;
   }

   protected String replacePlaceholders(String pString) {
      String result = pString;
      if (result != null) {
         result = result.replaceAll(CHAR_CR_PLACEHOLDER_REGEXP, CHAR_CR);
         result = result.replaceAll(CHAR_LF_PLACEHOLDER_REGEXP, CHAR_LF);
         result = result.replaceAll(LITERAL_BACKSLASH_R_PLACEHOLDER, LITERAL_BACKSLASH_R);
         result = result.replaceAll(LITERAL_BACKSLASH_N_PLACEHOLDER, LITERAL_BACKSLASH_N);
      }
      return result;
   }

   @Override
   public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
      final String errorMessage = String.format("line %d:%d %s", line, charPositionInLine, msg);
      errorMessages.add(errorMessage);
   }
}
