package com.ucm.dasi.catan.exception;

public class NonNullInputException extends Exception {

  private static final long serialVersionUID = -8557942017327063691L;

  public NonNullInputException() {
    super("Expected a non null input");
  }
}
