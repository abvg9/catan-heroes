package com.ucm.dasi.catan.game.trade;

import com.ucm.dasi.catan.exception.NonNullInputException;
import com.ucm.dasi.catan.exception.NonVoidCollectionException;
import com.ucm.dasi.catan.game.exception.InvalidReferenceException;
import com.ucm.dasi.catan.game.exception.NoCurrentTradeException;
import com.ucm.dasi.catan.game.exception.NotAnAcceptableExchangeException;
import java.util.Collection;

public interface ITradeManager {

  void addAgreement(ITradeAgreement agreement)
      throws NonNullInputException, NotAnAcceptableExchangeException, InvalidReferenceException,
          NoCurrentTradeException;

  void confirm(ITradeConfirmation confirmation)
      throws InvalidReferenceException, NoCurrentTradeException;

  void discard() throws NoCurrentTradeException;

  Collection<ITradeAgreement> getAgreements() throws NoCurrentTradeException;

  ITrade getTrade();

  void start(ITrade trade) throws NonNullInputException, NonVoidCollectionException;
}
