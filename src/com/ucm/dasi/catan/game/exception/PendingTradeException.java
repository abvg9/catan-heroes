package com.ucm.dasi.catan.game.exception;

import com.ucm.dasi.catan.game.trade.ITrade;

public class PendingTradeException extends RuntimeException {

  private static final long serialVersionUID = 5139370746659549947L;

  public PendingTradeException(ITrade trade) {
    super(composeMessage(trade));
  }

  private static String composeMessage(ITrade trade) {
    return String.format(
        "The current operation can not be performed. The trade %s is pending",
        trade.getId().toString());
  }
}
