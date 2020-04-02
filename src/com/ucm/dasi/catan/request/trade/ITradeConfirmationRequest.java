package com.ucm.dasi.catan.request.trade;

import com.ucm.dasi.catan.game.trade.ITradeConfirmation;
import com.ucm.dasi.catan.request.IRequest;

public interface ITradeConfirmationRequest extends IRequest {

  ITradeConfirmation getConfirmation();
}
