package com.ucm.dasi.catan.resource.exception;

public class NegativeNumberException extends Exception {

  private static final long serialVersionUID = 1L;

  public NegativeNumberException(String name) {
    super("Value of " + name + "can't be negative.");
  }
}