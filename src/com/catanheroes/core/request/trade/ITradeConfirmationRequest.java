package com.catanheroes.core.request.trade;

import com.catanheroes.core.game.trade.ITradeConfirmation;
import com.catanheroes.core.request.IRequest;

public interface ITradeConfirmationRequest extends IRequest {

  ITradeConfirmation getConfirmation();
}
