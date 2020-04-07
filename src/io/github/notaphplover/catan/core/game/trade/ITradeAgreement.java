package io.github.notaphplover.catan.core.game.trade;

import io.github.notaphplover.catan.core.resource.IResourceStorage;

public interface ITradeAgreement extends IReference {

  IResourceStorage getExchange();

  IReference getTrade();
}
