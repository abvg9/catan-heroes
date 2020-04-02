package com.ucm.dasi.catan.game.trade;

import java.util.Collection;

public interface ITradeManager {

  void addAgreement(ITradeAgreement agreement);

  void confirm(ITradeConfirmation confirmation);

  void discard();

  Collection<ITradeAgreement> getAgreements();

  ITrade getTrade();

  void start(ITrade trade);
}
