package com.catanheroes.core.game.exception;

public class InvalidLogException extends Exception {

  private static final long serialVersionUID = -5689231778061304408L;

  public InvalidLogException(int turns) {
    super(composeMessage(turns));
  }

  private static String composeMessage(int turns) {
    return String.format("Invalid log. Expected a log with %d entries", turns);
  }
}
