package com.ucm.dasi.catan.game.exception;

import com.ucm.dasi.catan.game.trade.ITrade;

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
