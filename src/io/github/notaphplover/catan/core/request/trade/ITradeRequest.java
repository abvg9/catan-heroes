package io.github.notaphplover.catan.core.request.trade;

import io.github.notaphplover.catan.core.game.trade.ITrade;
import io.github.notaphplover.catan.core.request.IRequest;

public interface ITradeRequest extends IRequest {

  ITrade getTrade();
}
