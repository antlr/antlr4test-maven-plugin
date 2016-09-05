package com.khubla.antlr.antlr4test;

public class AssertErrorsException extends Exception {
   /**
    *
    */
   private static final long serialVersionUID = 1L;

   public AssertErrorsException(String message) {
      super(message);
   }

   public AssertErrorsException(String message, Throwable cause) {
      super(message, cause);
   }
}
