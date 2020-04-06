package com.catanheroes.core.game.trade;

import com.catanheroes.core.exception.NonNullInputException;
import com.catanheroes.core.exception.NonVoidCollectionException;
import com.catanheroes.core.game.exception.AgreementAlreadyProposedException;
import com.catanheroes.core.game.exception.InvalidReferenceException;
import com.catanheroes.core.game.exception.NoCurrentTradeException;
import com.catanheroes.core.game.exception.NotAnAcceptableExchangeException;
import com.catanheroes.core.player.IPlayer;
import com.catanheroes.core.resource.exception.NotEnoughtResourcesException;
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
