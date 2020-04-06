package com.catanheroes.core.game.trade;

import com.catanheroes.core.resource.IResourceStorage;

public interface ITradeAgreement extends IReference {

  IResourceStorage getExchange();

  IReference getTrade();
}
