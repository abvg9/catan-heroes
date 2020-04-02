package com.ucm.dasi.catan.request.trade;

import com.ucm.dasi.catan.game.trade.ITradeAgreement;
import com.ucm.dasi.catan.request.IRequest;

public interface ITradeAgreementRequest extends IRequest {

  ITradeAgreement getTradeAgreement();
}
