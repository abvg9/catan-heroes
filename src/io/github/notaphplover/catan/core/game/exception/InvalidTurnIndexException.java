package io.github.notaphplover.catan.core.game.exception;

public class InvalidTurnIndexException extends RuntimeException {

  private static final long serialVersionUID = 4226663098277647119L;

  public InvalidTurnIndexException(int current, int min, int max) {
    super(composeMessage(current, min, max));
  }

  private static String composeMessage(int current, int min, int max) {
    return String.format(
        "Invalid turn index. Expected an index between %d and %d, found %d", min, max, current);
  }
}
