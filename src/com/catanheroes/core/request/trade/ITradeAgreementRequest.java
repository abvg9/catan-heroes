package com.catanheroes.core.request.trade;

import com.catanheroes.core.game.trade.ITradeAgreement;
import com.catanheroes.core.request.IRequest;

public interface ITradeAgreementRequest extends IRequest {

  ITradeAgreement getTradeAgreement();
}
