package io.github.notaphplover.catan.core.board.exception;

public class InvalidBoardDimensionsException extends Exception {

  private static final long serialVersionUID = -7651089991744581056L;

  public InvalidBoardDimensionsException() {
    super("Invalid board dimensions");
  }
}
