package io.github.notaphplover.catan.core.resource.exception;

public class NegativeNumberException extends RuntimeException {

  private static final long serialVersionUID = -3979940713503153971L;

  public NegativeNumberException(String name) {
    super(String.format("Value of %s can't be negative.", name));
  }
}
