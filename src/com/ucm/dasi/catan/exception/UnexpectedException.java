package com.ucm.dasi.catan.exception;

public class UnexpectedException extends RuntimeException {

  private static final long serialVersionUID = -5432659214736071111L;

  public UnexpectedException(Throwable previous) {
    super(previous);
  }
}
