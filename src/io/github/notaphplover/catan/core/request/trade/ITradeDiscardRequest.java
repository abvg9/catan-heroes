package io.github.notaphplover.catan.core.request.trade;

import io.github.notaphplover.catan.core.game.trade.ITradeDiscard;
import io.github.notaphplover.catan.core.request.IRequest;

public interface ITradeDiscardRequest extends IRequest {
  ITradeDiscard getDiscard();
}
