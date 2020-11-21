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
import java.util.*;

import org.antlr.v4.runtime.*;
import org.apache.maven.plugin.logging.*;

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
	private Scenario scenario = null;
	private Log log = null;

	public AssertErrorsErrorListener(Scenario scenario, Log log) {
		this.scenario = scenario;
		this.log = log;
	}

	public void assertErrors(File errorMessagesFile, String encoding) throws AssertErrorsException {
		if (!errorMessages.isEmpty()) {
			List<String> expectedErrorMessages = null;
			String errorMessageFileName = null;
			if (errorMessagesFile != null) {
				errorMessageFileName = errorMessagesFile.getName();
			}
			try {
				expectedErrorMessages = FileUtil.getNonEmptyLines(errorMessagesFile, encoding);
			} catch (final FileNotFoundException ex) {
				throw new AssertErrorsException(String.format("found %d errors, but missing file %s", errorMessages.size(), errorMessageFileName), ex);
			} catch (final IOException ex) {
				throw new AssertErrorsException(String.format("found %d errors, unable to read file %s", errorMessages.size(), errorMessageFileName), ex);
			}
			asserts(expectedErrorMessages, errorMessageFileName);
		} else {
			if ((errorMessagesFile != null) && errorMessagesFile.exists()) {
				throw new AssertErrorsException(String.format("no errors found, but errors file exists %s", errorMessagesFile.getAbsolutePath()));
			}
		}
	}

	public void asserts(List<String> expectedErrorMessages, String errorMessageFileName) throws AssertErrorsException {
		final List<String> expectedErrors = replacePlaceholders(expectedErrorMessages);
		final List<String> actualErrors = replacePlaceholders(errorMessages);
		final int expectedSize = expectedErrors.size();
		final int actualSize = actualErrors.size();
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

	public List<String> replacePlaceholders(List<String> stringList) {
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
		if (scenario.isVerbose()) {
			log.warn(errorMessage);
		}
		errorMessages.add(errorMessage);
	}
}
