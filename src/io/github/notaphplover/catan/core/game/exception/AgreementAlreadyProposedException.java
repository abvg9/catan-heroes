package io.github.notaphplover.catan.core.game.exception;

import io.github.notaphplover.catan.core.game.trade.ITradeAgreement;
import io.github.notaphplover.catan.core.player.IPlayer;

public class AgreementAlreadyProposedException extends Exception {

  private static final long serialVersionUID = -4789837171051833669L;

  public AgreementAlreadyProposedException(ITradeAgreement agreement, IPlayer player) {
    super(composeMessage(agreement, player));
  }

  private static String composeMessage(ITradeAgreement agreement, IPlayer player) {
    return String.format(
        "There is already an agreement equivalent to %s proposed by player %d",
        agreement.getId(), player.getId());
  }
}
