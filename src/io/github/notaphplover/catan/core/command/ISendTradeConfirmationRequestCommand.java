package io.github.notaphplover.catan.core.command;

import io.github.notaphplover.catan.core.game.trade.ITradeAgreement;
import java.util.Collection;

public interface ISendTradeConfirmationRequestCommand extends ICommand {

  Collection<ITradeAgreement> getAgreements();
}
