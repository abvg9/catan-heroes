package com.catanheroes.core.request.trade;

import com.catanheroes.core.game.trade.ITradeDiscard;
import com.catanheroes.core.request.IRequest;

public interface ITradeDiscardRequest extends IRequest {
  ITradeDiscard getDiscard();
}
