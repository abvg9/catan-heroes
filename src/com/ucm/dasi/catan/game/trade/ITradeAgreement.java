package com.ucm.dasi.catan.game.trade;

import com.ucm.dasi.catan.resource.IResourceStorage;

public interface ITradeAgreement extends IReference {

  IResourceStorage getExchange();

  IReference getTrade();
}
