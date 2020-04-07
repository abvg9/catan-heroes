package io.github.notaphplover.catan.core.request.trade;

import io.github.notaphplover.catan.core.game.trade.ITradeConfirmation;
import io.github.notaphplover.catan.core.request.IRequest;

public interface ITradeConfirmationRequest extends IRequest {

  ITradeConfirmation getConfirmation();
}
