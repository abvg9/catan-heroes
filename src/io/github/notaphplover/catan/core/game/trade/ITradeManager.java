package io.github.notaphplover.catan.core.game.trade;

import io.github.notaphplover.catan.core.exception.NonNullInputException;
import io.github.notaphplover.catan.core.exception.NonVoidCollectionException;
import io.github.notaphplover.catan.core.game.exception.AgreementAlreadyProposedException;
import io.github.notaphplover.catan.core.game.exception.InvalidReferenceException;
import io.github.notaphplover.catan.core.game.exception.NoCurrentTradeException;
import io.github.notaphplover.catan.core.game.exception.NotAnAcceptableExchangeException;
import io.github.notaphplover.catan.core.player.IPlayer;
import io.github.notaphplover.catan.core.resource.exception.NotEnoughtResourcesException;
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
