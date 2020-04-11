package io.github.notaphplover.catan.core.game.handler.trade;

import io.github.notaphplover.catan.core.command.Command;
import io.github.notaphplover.catan.core.command.CommandType;
import io.github.notaphplover.catan.core.exception.NonNullInputException;
import io.github.notaphplover.catan.core.game.GameState;
import io.github.notaphplover.catan.core.game.exception.InvalidReferenceException;
import io.github.notaphplover.catan.core.game.exception.NoCurrentTradeException;
import io.github.notaphplover.catan.core.game.handler.StandardRequestHandler;
import io.github.notaphplover.catan.core.game.hearth.ICatanGameHearth;
import io.github.notaphplover.catan.core.request.trade.ITradeDiscardRequest;
import java.util.LinkedList;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class TradeDiscardRequestHandler extends StandardRequestHandler<ITradeDiscardRequest> {

  public TradeDiscardRequestHandler() {
    super(getBuilder());
  }

  public static TradeDiscardRequestHandlerBuilder getBuilder() {

    LinkedList<BiConsumer<ICatanGameHearth, ITradeDiscardRequest>> afterSuccessActions =
        new LinkedList<>();

    afterSuccessActions.add(
        (ICatanGameHearth hearth, ITradeDiscardRequest request) -> {
          hearth
              .getCommandSender()
              .send(new Command(request.getPlayer(), CommandType.SEND_NORMAL_REQUEST));
        });

    TradeDiscardRequestHandlerBuilder builder =
        new TradeDiscardRequestHandlerBuilder()
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

    BiFunction<ICatanGameHearth, ITradeDiscardRequest, Boolean> preconditionsFullfilledAction =
        (ICatanGameHearth hearth, ITradeDiscardRequest request) -> {
          try {
            hearth.getTradeManager().discard(request.getDiscard());
            return true;
          } catch (InvalidReferenceException | NoCurrentTradeException | NonNullInputException e) {
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
