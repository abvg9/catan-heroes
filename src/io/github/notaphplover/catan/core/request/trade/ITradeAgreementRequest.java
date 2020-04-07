package io.github.notaphplover.catan.core.request.trade;

import io.github.notaphplover.catan.core.game.trade.ITradeAgreement;
import io.github.notaphplover.catan.core.request.IRequest;

public interface ITradeAgreementRequest extends IRequest {

  ITradeAgreement getTradeAgreement();
}
