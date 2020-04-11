package io.github.notaphplover.catan.core.game.handler;

import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.notaphplover.catan.core.exception.NonNullInputException;
import io.github.notaphplover.catan.core.exception.NonVoidCollectionException;
import io.github.notaphplover.catan.core.game.GameState;
import io.github.notaphplover.catan.core.game.generator.ConstantNumberGenerator;
import io.github.notaphplover.catan.core.game.generator.INumberGenerator;
import io.github.notaphplover.catan.core.game.hearth.CatanGameHearth;
import io.github.notaphplover.catan.core.game.hearth.CatanGameHearthBuilder;
import io.github.notaphplover.catan.core.game.hearth.ICatanGameHearth;
import io.github.notaphplover.catan.core.game.hearth.ICatanGameHearthBuilder;
import io.github.notaphplover.catan.core.game.log.IGameLog;
import io.github.notaphplover.catan.core.game.log.ILogEntry;
import io.github.notaphplover.catan.core.game.log.LinearGameLog;
import io.github.notaphplover.catan.core.game.log.LogEntry;
import io.github.notaphplover.catan.core.game.player.IPlayerManager;
import io.github.notaphplover.catan.core.game.player.PlayerManager;
import io.github.notaphplover.catan.core.player.IPlayer;
import io.github.notaphplover.catan.core.player.Player;
import io.github.notaphplover.catan.core.request.EndTurnRequest;
import io.github.notaphplover.catan.core.request.IRequest;
import io.github.notaphplover.catan.core.request.MinimunRequest;
import io.github.notaphplover.catan.core.request.RequestType;
import io.github.notaphplover.catan.core.request.StartTurnRequest;
import io.github.notaphplover.catan.core.resource.ResourceManager;
import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Standard request handler tests")
public class StandardRequestHandlerTest {

  @Nested
  @DisplayName("StandardRequestHandler.handle")
  class Handle {

    @DisplayName("It should log after success action if logRequestAfterAction is enabled")
    @Test
    public void itShouldLogAfterSuccessAction()
        throws NonNullInputException, NonVoidCollectionException {

      MinimunStandardRequestHandlerBuilder builder =
          generateStandardBuilder()
              .setLogRequestAfterAction(true)
              .setRejectActivePlayer(false)
              .setRejectIfTurnNotStarted(false)
              .setRejectIfTurnStarted(false)
              .setRejectUnactivePlayers(false)
              .setStateAllowed(null);

      MinimunStandardRequestHandler handler = new MinimunStandardRequestHandler(builder);

      int turn = 0;
      boolean turnStarted = true;
      int productionNumber = 2;
      GameState state = GameState.NORMAL;

      ICatanGameHearth hearth =
          new CatanGameHearth(generateHearthBuilder(turn, turnStarted, productionNumber, state));

      IRequest request =
          new MinimunRequest(
              hearth.getPlayerManager().getActivePlayer(), RequestType.BUILD_CONNECTION);

      handler.handle(hearth, request);

      AtomicBoolean isContained = new AtomicBoolean();

      hearth
          .getGameLog()
          .get(hearth.getPlayerManager().getTurnNumber())
          .getRequests()
          .forEach(
              (IRequest requestIn) -> {
                if (requestIn == request) {
                  isContained.set(true);
                }
              });

      assertTrue(isContained.get());
    }

    @DisplayName("It should reject the active player request if rejectActivePlayer is enabled")
    @Test
    public void itShouldRejectActivePlayer()
        throws NonNullInputException, NonVoidCollectionException {
      MinimunStandardRequestHandlerBuilder builder =
          generateStandardBuilder()
              .setLogRequestAfterAction(false)
              .setRejectActivePlayer(true)
              .setRejectIfTurnNotStarted(false)
              .setRejectIfTurnStarted(false)
              .setRejectUnactivePlayers(false)
              .setStateAllowed(null);

      MinimunStandardRequestHandler handler = new MinimunStandardRequestHandler(builder);

      int turn = 0;
      boolean turnStarted = true;
      int productionNumber = 2;
      GameState state = GameState.NORMAL;

      ICatanGameHearthBuilder hearthBuilder =
          generateHearthBuilder(turn, turnStarted, productionNumber, state);

      AtomicBoolean called = new AtomicBoolean(false);
      hearthBuilder.setErrorHandler(
          (IRequest request) -> {
            called.set(true);
          });

      ICatanGameHearth hearth = new CatanGameHearth(hearthBuilder);

      IRequest request =
          new MinimunRequest(
              hearth.getPlayerManager().getActivePlayer(), RequestType.BUILD_CONNECTION);

      handler.handle(hearth, request);

      assertTrue(called.get());
    }

    @DisplayName(
        "It should reject the request if the turn has not started and rejectIfTurnNotStarted is enabled")
    @Test
    public void itShouldRejectTurnNotStarted()
        throws NonNullInputException, NonVoidCollectionException {
      MinimunStandardRequestHandlerBuilder builder =
          generateStandardBuilder()
              .setLogRequestAfterAction(false)
              .setRejectActivePlayer(false)
              .setRejectIfTurnNotStarted(true)
              .setRejectIfTurnStarted(false)
              .setRejectUnactivePlayers(false)
              .setStateAllowed(null);

      MinimunStandardRequestHandler handler = new MinimunStandardRequestHandler(builder);

      int turn = 0;
      boolean turnStarted = false;
      int productionNumber = 2;
      GameState state = GameState.NORMAL;

      ICatanGameHearthBuilder hearthBuilder =
          generateHearthBuilder(turn, turnStarted, productionNumber, state);

      AtomicBoolean called = new AtomicBoolean(false);
      hearthBuilder.setErrorHandler(
          (IRequest request) -> {
            called.set(true);
          });

      ICatanGameHearth hearth = new CatanGameHearth(hearthBuilder);

      IRequest request =
          new MinimunRequest(
              hearth.getPlayerManager().getActivePlayer(), RequestType.BUILD_CONNECTION);

      handler.handle(hearth, request);

      assertTrue(called.get());
    }

    @DisplayName(
        "It should reject the request if the turn has not started and rejectIfTurnStarted is enabled")
    @Test
    public void itShouldRejectTurnStarted()
        throws NonNullInputException, NonVoidCollectionException {
      MinimunStandardRequestHandlerBuilder builder =
          generateStandardBuilder()
              .setLogRequestAfterAction(false)
              .setRejectActivePlayer(false)
              .setRejectIfTurnNotStarted(false)
              .setRejectIfTurnStarted(true)
              .setRejectUnactivePlayers(false)
              .setStateAllowed(null);

      MinimunStandardRequestHandler handler = new MinimunStandardRequestHandler(builder);

      int turn = 0;
      boolean turnStarted = true;
      int productionNumber = 2;
      GameState state = GameState.NORMAL;

      ICatanGameHearthBuilder hearthBuilder =
          generateHearthBuilder(turn, turnStarted, productionNumber, state);

      AtomicBoolean called = new AtomicBoolean(false);
      hearthBuilder.setErrorHandler(
          (IRequest request) -> {
            called.set(true);
          });

      ICatanGameHearth hearth = new CatanGameHearth(hearthBuilder);

      IRequest request =
          new MinimunRequest(
              hearth.getPlayerManager().getActivePlayer(), RequestType.BUILD_CONNECTION);

      handler.handle(hearth, request);

      assertTrue(called.get());
    }

    @DisplayName("It should reject an unactive player request if rejectUnactivePlayers is enabled")
    @Test
    public void itShouldRejectUnactivePlayer()
        throws NonNullInputException, NonVoidCollectionException {
      MinimunStandardRequestHandlerBuilder builder =
          generateStandardBuilder()
              .setLogRequestAfterAction(false)
              .setRejectActivePlayer(false)
              .setRejectIfTurnNotStarted(false)
              .setRejectIfTurnStarted(false)
              .setRejectUnactivePlayers(true)
              .setStateAllowed(null);

      MinimunStandardRequestHandler handler = new MinimunStandardRequestHandler(builder);

      int turn = 0;
      boolean turnStarted = true;
      int productionNumber = 2;
      GameState state = GameState.NORMAL;

      ICatanGameHearthBuilder hearthBuilder =
          generateHearthBuilder(turn, turnStarted, productionNumber, state);

      AtomicBoolean called = new AtomicBoolean(false);
      hearthBuilder.setErrorHandler(
          (IRequest request) -> {
            called.set(true);
          });

      ICatanGameHearth hearth = new CatanGameHearth(hearthBuilder);

      IRequest request =
          new MinimunRequest(
              findUnactivePlayer(hearth.getPlayerManager()), RequestType.BUILD_CONNECTION);

      handler.handle(hearth, request);

      assertTrue(called.get());
    }

    @DisplayName("It should reject the request if the phase is not the right one")
    @Test
    public void itShouldRejectStateNotAllowed()
        throws NonNullInputException, NonVoidCollectionException {
      MinimunStandardRequestHandlerBuilder builder =
          generateStandardBuilder()
              .setLogRequestAfterAction(false)
              .setRejectActivePlayer(false)
              .setRejectIfTurnNotStarted(false)
              .setRejectIfTurnStarted(false)
              .setRejectUnactivePlayers(false)
              .setStateAllowed(GameState.FOUNDATION);

      MinimunStandardRequestHandler handler = new MinimunStandardRequestHandler(builder);

      int turn = 0;
      boolean turnStarted = true;
      int productionNumber = 2;
      GameState state = GameState.NORMAL;

      ICatanGameHearthBuilder hearthBuilder =
          generateHearthBuilder(turn, turnStarted, productionNumber, state);

      AtomicBoolean called = new AtomicBoolean(false);
      hearthBuilder.setErrorHandler(
          (IRequest request) -> {
            called.set(true);
          });

      ICatanGameHearth hearth = new CatanGameHearth(hearthBuilder);

      IRequest request =
          new MinimunRequest(
              hearth.getPlayerManager().getActivePlayer(), RequestType.BUILD_CONNECTION);

      handler.handle(hearth, request);

      assertTrue(called.get());
    }
  }

  private static IPlayer findUnactivePlayer(IPlayerManager playerManger) {

    for (IPlayer player : playerManger.getPlayers()) {
      if (player != playerManger.getActivePlayer()) {
        return player;
      }
    }

    return null;
  }

  private static ICatanGameHearthBuilder generateHearthBuilder(
      int turn, boolean turnStarted, int productionNumber, GameState state)
      throws NonNullInputException, NonVoidCollectionException {
    IPlayer player0 = new Player(0, new ResourceManager());
    IPlayer player1 = new Player(1, new ResourceManager());

    IPlayer[] players = {player0, player1};

    INumberGenerator generator = new ConstantNumberGenerator(productionNumber);

    IGameLog log = generateLog(players, turn, generator, turnStarted);

    IPlayerManager playerManager = new PlayerManager(players, turn, turnStarted);

    int pointsToWin = 10;

    ICatanGameHearthBuilder hearthBuilder =
        new CatanGameHearthBuilder()
            .setBoard(null)
            .setCommandSender(null)
            .setConnectionCostProvider(null)
            .setErrorHandler(null)
            .setGameLog(log)
            .setNumberGenerator(generator)
            .setPlayerManager(playerManager)
            .setPointsCalculator(null)
            .setPointsToWin(pointsToWin)
            .setState(state)
            .setStructureCostProvider(null)
            .setTradeManager(null);

    return hearthBuilder;
  }

  private static IGameLog generateLog(
      IPlayer[] players, int currentTurn, INumberGenerator generator, boolean turnStarted)
      throws NonNullInputException, NonVoidCollectionException {
    IPlayerManager playerManager = new PlayerManager(players, 0, false);

    Collection<ILogEntry> entries = new LinkedList<>();

    for (int i = 0; i < currentTurn; ++i) {
      Collection<IRequest> requests = new LinkedList<>();

      requests.add(new StartTurnRequest(playerManager.getActivePlayer()));
      requests.add(new EndTurnRequest(playerManager.getActivePlayer()));

      entries.add(new LogEntry(generator.getNextProductionNumber(), requests));

      playerManager.passTurn();
    }

    Collection<IRequest> requests = new LinkedList<>();

    if (turnStarted) {
      requests.add(new StartTurnRequest(playerManager.getActivePlayer()));
    }

    entries.add(new LogEntry(generator.getNextProductionNumber(), requests));

    return new LinearGameLog(entries);
  }

  private static MinimunStandardRequestHandlerBuilder generateStandardBuilder() {

    LinkedList<BiConsumer<ICatanGameHearth, IRequest>> afterFailureActions = new LinkedList<>();

    LinkedList<BiConsumer<ICatanGameHearth, IRequest>> afterSuccessActions = new LinkedList<>();
    afterSuccessActions.add((ICatanGameHearth hearth, IRequest request) -> {});

    BiFunction<ICatanGameHearth, IRequest, Boolean> preconditionFullfilledAction =
        (ICatanGameHearth hearth, IRequest request) -> true;
    BiConsumer<ICatanGameHearth, IRequest> preconditionRejectedAction =
        (ICatanGameHearth hearth, IRequest request) -> {};

    LinkedList<BiFunction<ICatanGameHearth, IRequest, Boolean>> preconditionList =
        new LinkedList<>();

    return new MinimunStandardRequestHandlerBuilder()
        .setAfterFailureActions(afterFailureActions)
        .setAfterSuccessActions(afterSuccessActions)
        .setPreconditionFullfilledAction(preconditionFullfilledAction)
        .setPreconditionRejectedAction(preconditionRejectedAction)
        .setPreconditionsList(preconditionList);
  }
}
