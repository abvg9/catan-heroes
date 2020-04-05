package com.ucm.dasi.catan.request.trade;

import com.ucm.dasi.catan.game.trade.ITradeDiscard;
import com.ucm.dasi.catan.request.IRequest;

public interface ITradeDiscardRequest extends IRequest {
  ITradeDiscard getDiscard();
}
