package io.github.notaphplover.catan.core.exception;

public class NonVoidCollectionException extends Exception {

  private static final long serialVersionUID = 1652590365359961275L;

  public NonVoidCollectionException() {
    super("Expected a non void collection");
  }
}
