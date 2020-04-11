package io.github.notaphplover.catan.core.game.handler.trade;

import io.github.notaphplover.catan.core.command.Command;
import io.github.notaphplover.catan.core.command.CommandType;
import io.github.notaphplover.catan.core.game.GameState;
import io.github.notaphplover.catan.core.game.exception.InvalidReferenceException;
import io.github.notaphplover.catan.core.game.exception.NoCurrentTradeException;
import io.github.notaphplover.catan.core.game.handler.StandardRequestHandler;
import io.github.notaphplover.catan.core.game.hearth.ICatanGameHearth;
import io.github.notaphplover.catan.core.request.trade.ITradeConfirmationRequest;
import java.util.LinkedList;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class TradeConfirmationRequestHandler
    extends StandardRequestHandler<ITradeConfirmationRequest> {

  public TradeConfirmationRequestHandler() {
    super(getBuilder());
  }

  private static TradeConfirmationRequestHandlerBuilder getBuilder() {

    LinkedList<BiConsumer<ICatanGameHearth, ITradeConfirmationRequest>> afterSuccessActions =
        new LinkedList<>();

    afterSuccessActions.add(
        (ICatanGameHearth hearth, ITradeConfirmationRequest request) -> {
          hearth
              .getCommandSender()
              .send(new Command(request.getPlayer(), CommandType.SEND_NORMAL_REQUEST));
        });

    TradeConfirmationRequestHandlerBuilder builder =
        new TradeConfirmationRequestHandlerBuilder()
            .setAfterFailureActions(new LinkedList<>())
            .setAfterSuccessActions(afterSuccessActions)
            .setLogRequestAfterAction(true)
            .setPreconditionRejectedAction(null)
            .setPreconditionsList(new LinkedList<>())
            .setRejectActivePlayer(false)
            .setRejectIfTurnNotStarted(true)
            .setRejectIfTurnStarted(false)
            .setRejectUnactivePlayers(true)
            .setStateAllowed(GameState.NORMAL);

    BiFunction<ICatanGameHearth, ITradeConfirmationRequest, Boolean> preconditionsFullfilledAction =
        (ICatanGameHearth hearth, ITradeConfirmationRequest request) -> {
          try {
            hearth.getTradeManager().confirm(request.getConfirmation());
            return true;
          } catch (InvalidReferenceException | NoCurrentTradeException e) {
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
