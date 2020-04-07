package io.github.notaphplover.catan.core.command;

import io.github.notaphplover.catan.core.game.trade.ITrade;

public interface ISendTradeAgreementRequestCommand extends ICommand {

  ITrade getTrade();
}
