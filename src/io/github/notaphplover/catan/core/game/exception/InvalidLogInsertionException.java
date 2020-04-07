package io.github.notaphplover.catan.core.game.exception;

public class InvalidLogInsertionException extends RuntimeException {

  private static final long serialVersionUID = 9004079903353540623L;

  public InvalidLogInsertionException(int entries) {
    super(composeMessage(entries));
  }

  private static String composeMessage(int entries) {
    return String.format(
        "It's not allowed to insert an entry in a turn greater than %d. The log entry for the turn %d is missing.",
        entries, entries);
  }
}
