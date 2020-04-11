package io.github.notaphplover.catan.core.game.handler.trade;

import io.github.notaphplover.catan.core.command.Command;
import io.github.notaphplover.catan.core.command.CommandType;
import io.github.notaphplover.catan.core.exception.NonNullInputException;
import io.github.notaphplover.catan.core.exception.NonVoidCollectionException;
import io.github.notaphplover.catan.core.game.GameState;
import io.github.notaphplover.catan.core.game.handler.StandardRequestHandler;
import io.github.notaphplover.catan.core.game.hearth.ICatanGameHearth;
import io.github.notaphplover.catan.core.player.IPlayer;
import io.github.notaphplover.catan.core.request.trade.ITradeRequest;
import io.github.notaphplover.catan.core.resource.exception.NotEnoughtResourcesException;
import java.util.LinkedList;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class TradeRequestHandler extends StandardRequestHandler<ITradeRequest> {

  public TradeRequestHandler() {
    super(getBuilder());
  }

  private static TradeRequestHandlerBuilder getBuilder() {

    LinkedList<BiConsumer<ICatanGameHearth, ITradeRequest>> afterSuccessActions =
        new LinkedList<>();

    afterSuccessActions.add(
        (ICatanGameHearth hearth, ITradeRequest request) -> {
          IPlayer active = hearth.getPlayerManager().getActivePlayer();

          for (IPlayer player : hearth.getPlayerManager().getPlayers()) {
            if (!active.equals(player)) {
              hearth
                  .getCommandSender()
                  .send(new Command(player, CommandType.SEND_TRADE_AGREEMENT_REQUEST));
            }
          }
        });

    TradeRequestHandlerBuilder builder =
        new TradeRequestHandlerBuilder()
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

    BiFunction<ICatanGameHearth, ITradeRequest, Boolean> preconditionsFullfilledAction =
        (ICatanGameHearth hearth, ITradeRequest request) -> {
          try {
            hearth.getTradeManager().start(request.getPlayer(), request.getTrade());
            return true;
          } catch (NonNullInputException
              | NonVoidCollectionException
              | NotEnoughtResourcesException e) {
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
