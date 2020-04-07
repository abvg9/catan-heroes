package io.github.notaphplover.catan.core.game.exception;

import io.github.notaphplover.catan.core.game.trade.ITrade;

public class NotAnAcceptableExchangeException extends Exception {

  private static final long serialVersionUID = 2769501599565308319L;

  public NotAnAcceptableExchangeException(ITrade trade) {
    super(composeMessage(trade));
  }

  private static String composeMessage(ITrade trade) {
    return String.format(
        "The exchange provided is not an acceptable exchange of the trade %s",
        trade.getId().toString());
  }
}
