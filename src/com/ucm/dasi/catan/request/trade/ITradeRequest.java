package com.ucm.dasi.catan.request.trade;

import com.ucm.dasi.catan.game.trade.ITrade;
import com.ucm.dasi.catan.request.IRequest;

public interface ITradeRequest extends IRequest {

  ITrade getTrade();
}
