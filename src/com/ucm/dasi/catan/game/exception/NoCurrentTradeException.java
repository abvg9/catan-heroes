package com.ucm.dasi.catan.game.exception;

public class NoCurrentTradeException extends Exception {

  private static final long serialVersionUID = -8491134590419623868L;

  public NoCurrentTradeException() {
    super(composeMessage());
  }

  private static String composeMessage() {
    return "Expected a trade, but none was found";
  }
}
