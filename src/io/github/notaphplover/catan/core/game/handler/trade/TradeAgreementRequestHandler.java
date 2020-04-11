package io.github.notaphplover.catan.core.game.handler.trade;

import io.github.notaphplover.catan.core.exception.NonNullInputException;
import io.github.notaphplover.catan.core.game.GameState;
import io.github.notaphplover.catan.core.game.exception.AgreementAlreadyProposedException;
import io.github.notaphplover.catan.core.game.exception.InvalidReferenceException;
import io.github.notaphplover.catan.core.game.exception.NoCurrentTradeException;
import io.github.notaphplover.catan.core.game.exception.NotAnAcceptableExchangeException;
import io.github.notaphplover.catan.core.game.handler.StandardRequestHandler;
import io.github.notaphplover.catan.core.game.hearth.ICatanGameHearth;
import io.github.notaphplover.catan.core.request.trade.ITradeAgreementRequest;
import io.github.notaphplover.catan.core.resource.exception.NotEnoughtResourcesException;
import java.util.LinkedList;
import java.util.function.BiFunction;

public class TradeAgreementRequestHandler extends StandardRequestHandler<ITradeAgreementRequest> {

  public TradeAgreementRequestHandler() {
    super(getBuilder());
  }

  private static TradeAgreementRequestHandlerBuilder getBuilder() {

    TradeAgreementRequestHandlerBuilder builder =
        new TradeAgreementRequestHandlerBuilder()
            .setAfterFailureActions(new LinkedList<>())
            .setAfterSuccessActions(new LinkedList<>())
            .setLogRequestAfterAction(true)
            .setPreconditionRejectedAction(null)
            .setPreconditionsList(new LinkedList<>())
            .setRejectActivePlayer(true)
            .setRejectIfTurnNotStarted(true)
            .setRejectIfTurnStarted(false)
            .setRejectUnactivePlayers(false)
            .setStateAllowed(GameState.NORMAL);

    BiFunction<ICatanGameHearth, ITradeAgreementRequest, Boolean> preconditionsFullfilledAction =
        (ICatanGameHearth hearth, ITradeAgreementRequest request) -> {
          try {
            hearth.getTradeManager().addAgreement(request.getPlayer(), request.getTradeAgreement());
            return true;
          } catch (NotAnAcceptableExchangeException
              | InvalidReferenceException
              | NoCurrentTradeException
              | NonNullInputException
              | NotEnoughtResourcesException
              | AgreementAlreadyProposedException e) {
            if (builder.getPreconditionRejectedAction() != null) {
              builder.getPreconditionRejectedAction().accept(hearth, request);
            }
            return false;
          }
        };

    builder.setPreconditionFullfilledAction(preconditionsFullfilledAction);

    return builder;
  }
}
