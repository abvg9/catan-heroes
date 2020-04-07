package io.github.notaphplover.catan.core.command;

import io.github.notaphplover.catan.core.game.trade.ITrade;
import io.github.notaphplover.catan.core.player.IPlayer;

public class SendTradeAgreementRequestCommand extends Command
    implements ISendTradeAgreementRequestCommand {

  private ITrade trade;

  public SendTradeAgreementRequestCommand(IPlayer destinatary, ITrade trade) {
    super(destinatary, CommandType.SEND_TRADE_AGREEMENT_REQUEST);

    this.trade = trade;
  }

  @Override
  public ITrade getTrade() {
    return trade;
  }
}
