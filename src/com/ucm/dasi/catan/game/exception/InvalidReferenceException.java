package com.ucm.dasi.catan.game.exception;

import com.ucm.dasi.catan.game.trade.IReference;

public class InvalidReferenceException extends RuntimeException {

  private static final long serialVersionUID = 7640762256694875499L;

  public InvalidReferenceException(IReference actual) {
    super(composeMessage(actual));
  }

  public InvalidReferenceException(IReference expected, IReference actual) {
    super(composeMessage(expected, actual));
  }

  private static String composeMessage(IReference actual) {
    return String.format("Reference %s is not valid", actual.getId().toString());
  }

  private static String composeMessage(IReference expected, IReference actual) {
    return String.format(
        "Expected a reference to %s, got %s",
        expected.getId().toString(), actual.getId().toString());
  }
}
