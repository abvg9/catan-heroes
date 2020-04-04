package com.ucm.dasi.catan.game.trade;

import com.ucm.dasi.catan.exception.NonNullInputException;
import com.ucm.dasi.catan.exception.NonVoidCollectionException;
import com.ucm.dasi.catan.game.exception.AgreementAlreadyProposedException;
import com.ucm.dasi.catan.game.exception.InvalidReferenceException;
import com.ucm.dasi.catan.game.exception.NoCurrentTradeException;
import com.ucm.dasi.catan.game.exception.NotAnAcceptableExchangeException;
import com.ucm.dasi.catan.player.IPlayer;
import com.ucm.dasi.catan.resource.exception.NotEnoughtResourcesException;
import java.util.Collection;

public interface ITradeManager {

  void addAgreement(IPlayer player, ITradeAgreement agreement)
      throws NonNullInputException, NotAnAcceptableExchangeException, InvalidReferenceException,
          NoCurrentTradeException, NotEnoughtResourcesException, AgreementAlreadyProposedException;

  ITradeAgreement confirm(ITradeConfirmation confirmation)
      throws InvalidReferenceException, NoCurrentTradeException;

  void discard(ITradeDiscard discard)
      throws NoCurrentTradeException, NonNullInputException, InvalidReferenceException;

  Collection<ITradeAgreement> getAgreements() throws NoCurrentTradeException;

  IPlayer getBuyer();

  ITrade getTrade();

  void start(IPlayer player, ITrade trade)
      throws NonNullInputException, NonVoidCollectionException, NotEnoughtResourcesException;
}
