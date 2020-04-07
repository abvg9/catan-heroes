package io.github.notaphplover.catan.core.command;

import io.github.notaphplover.catan.core.game.trade.ITradeAgreement;
import io.github.notaphplover.catan.core.player.IPlayer;
import java.util.ArrayList;
import java.util.Collection;

public class SendTradeConfirmationRequestCommand extends Command
    implements ISendTradeConfirmationRequestCommand {

  Collection<ITradeAgreement> agreements;

  public SendTradeConfirmationRequestCommand(
      IPlayer destinatary, Collection<ITradeAgreement> agreements) {
    super(destinatary, CommandType.SEND_TRADE_CONFIRMATION_REQUEST);

    this.agreements = new ArrayList<>(agreements);
  }

  @Override
  public Collection<ITradeAgreement> getAgreements() {
    return agreements;
  }
}
