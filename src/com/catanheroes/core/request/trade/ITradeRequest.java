package com.catanheroes.core.request.trade;

import com.catanheroes.core.game.trade.ITrade;
import com.catanheroes.core.request.IRequest;

public interface ITradeRequest extends IRequest {

  ITrade getTrade();
}
