package io.github.notaphplover.catan.core.game;

import io.github.notaphplover.catan.core.board.ICatanBoard;
import io.github.notaphplover.catan.core.exception.NonNullInputException;
import io.github.notaphplover.catan.core.game.exception.InvalidLogException;
import io.github.notaphplover.catan.core.game.handler.GameEngineHandlersMap;
import io.github.notaphplover.catan.core.game.handler.IGameEngineHandlersMap;
import io.github.notaphplover.catan.core.game.handler.IRequestHandler;
import io.github.notaphplover.catan.core.game.handler.element.connection.BuildConnectionRequestAtFoundationPhaseHandler;
import io.github.notaphplover.catan.core.game.handler.element.connection.BuildConnectionRequestAtNormalPhaseHandler;
import io.github.notaphplover.catan.core.game.handler.element.structure.BuildStructureRequestAtFoundationPhaseHandler;
import io.github.notaphplover.catan.core.game.handler.element.structure.BuildStructureRequestAtNormalPhaseHandler;
import io.github.notaphplover.catan.core.game.handler.element.structure.UpgradeStructureRequestAtNormalPhaseHandler;
import io.github.notaphplover.catan.core.game.handler.trade.TradeAgreementRequestHandler;
import io.github.notaphplover.catan.core.game.handler.trade.TradeConfirmationRequestHandler;
import io.github.notaphplover.catan.core.game.handler.trade.TradeDiscardRequestHandler;
import io.github.notaphplover.catan.core.game.handler.trade.TradeRequestHandler;
import io.github.notaphplover.catan.core.game.handler.turn.EndTurnRequestHandler;
import io.github.notaphplover.catan.core.game.handler.turn.StartTurnRequestHandler;
import io.github.notaphplover.catan.core.game.hearth.CatanGameHearth;
import io.github.notaphplover.catan.core.game.hearth.CatanGameHearthBuilder;
import io.github.notaphplover.catan.core.game.hearth.ICatanGameHearth;
import io.github.notaphplover.catan.core.game.hearth.ICatanGameHearthBuilder;
import io.github.notaphplover.catan.core.game.log.IGameLog;
import io.github.notaphplover.catan.core.game.log.ILogEntry;
import io.github.notaphplover.catan.core.game.point.PointsCalculator;
import io.github.notaphplover.catan.core.game.trade.TradeManager;
import io.github.notaphplover.catan.core.player.IPlayer;
import io.github.notaphplover.catan.core.request.IBuildConnectionRequest;
import io.github.notaphplover.catan.core.request.IBuildStructureRequest;
import io.github.notaphplover.catan.core.request.IEndTurnRequest;
import io.github.notaphplover.catan.core.request.IRequest;
import io.github.notaphplover.catan.core.request.IStartTurnRequest;
import io.github.notaphplover.catan.core.request.IUpgradeStructureRequest;
import io.github.notaphplover.catan.core.request.RequestType;
import io.github.notaphplover.catan.core.request.trade.ITradeAgreementRequest;
import io.github.notaphplover.catan.core.request.trade.ITradeConfirmationRequest;
import io.github.notaphplover.catan.core.request.trade.ITradeDiscardRequest;
import io.github.notaphplover.catan.core.request.trade.ITradeRequest;
import io.github.notaphplover.catan.core.resource.provider.DefaultConnectionCostProvider;
import io.github.notaphplover.catan.core.resource.provider.DefaultStructureCostProvider;
import java.util.Map;
import java.util.function.Consumer;

public class CatanGame implements ICatanGame {

  private IGameEngineHandlersMap handlersMap;

  private ICatanGameHearth hearth;

  public CatanGame(ICatanGameBuilder builder)
      throws NonNullInputException, InvalidLogException {

    ICatanGameHearthBuilder hearthBuilder =
        new CatanGameHearthBuilder()
            .setBoard(builder.getBoard())
            .setCommandSender(builder.getCommandSender())
            .setConnectionCostProvider(new DefaultConnectionCostProvider())
            .setErrorHandler(builder.getErrorHandler())
            .setGameLog(builder.getGameLog())
            .setNumberGenerator(builder.getNumberGenerator())
            .setPlayerManager(builder.getPlayerManager())
            .setPointsCalculator(new PointsCalculator(this))
            .setPointsToWin(builder.getPointsToWin())
            .setState(builder.getState())
            .setStructureCostProvider(new DefaultStructureCostProvider())
            .setTradeManager(new TradeManager());

    hearth = new CatanGameHearth(hearthBuilder);

    checkBoard(hearth.getBoard());
    checkState(hearth.getState());
    checkLog(hearth.getGameLog());

    handlersMap = new GameEngineHandlersMap(generateMap());
  }

  @Override
  public IPlayer getActivePlayer() {
    return hearth.getPlayerManager().getActivePlayer();
  }

  @Override
  public ICatanBoard getBoard() {
    return hearth.getBoard();
  }

  @Override
  public ILogEntry getLog(int turn) {
    return hearth.getGameLog().get(turn);
  }

  @Override
  public IPlayer[] getPlayers() {
    return hearth.getPlayerManager().getPlayers();
  }

  @Override
  public int getPointsToWin() {
    return hearth.getPointsToWin();
  }

  @Override
  public Map<IPlayer, Integer> getPoints() {
    return hearth.getPointsCalculator().getPoints();
  }

  @Override
  public GameState getState() {
    return hearth.getState();
  }

  @Override
  public int getTurnNumber() {
    return hearth.getPlayerManager().getTurnNumber();
  }

  @Override
  public boolean isTurnStarted() {
    return hearth.getPlayerManager().isTurnStarted();
  }

  @Override
  public void processRequest(IRequest request) {
    processTurnRequest(request);
  }

  private void checkLog(IGameLog log) throws InvalidLogException {
    int entries = log.size();
    int expectedEntries = isTurnStarted() ? getTurnNumber() + 1 : getTurnNumber();

    if (entries != expectedEntries) {
      throw new InvalidLogException(expectedEntries);
    }
  }

  private void checkBoard(ICatanBoard board) throws NonNullInputException {
    if (board == null) {
      throw new NonNullInputException();
    }
  }

  private void checkState(GameState state) throws NonNullInputException {
    if (state == null) {
      throw new NonNullInputException();
    }
  }

  @SuppressWarnings("unchecked")
  private <T extends IRequest> void consumeRequest(Consumer<T> consumer, IRequest request) {
    consumer.accept((T) request);
  }

  private IGameEngineHandlersMap generateMap() {
    IGameEngineHandlersMap map = new GameEngineHandlersMap();

    final IRequestHandler<IBuildConnectionRequest> buildConnectionHandler =
        new BuildConnectionRequestAtNormalPhaseHandler();
    final IRequestHandler<IBuildConnectionRequest> buildInitialConnectionHandler =
        new BuildConnectionRequestAtFoundationPhaseHandler();
    final IRequestHandler<IBuildStructureRequest> buildInitialStructureHandler =
        new BuildStructureRequestAtFoundationPhaseHandler();
    final IRequestHandler<IBuildStructureRequest> buildStructureHandler =
        new BuildStructureRequestAtNormalPhaseHandler();
    final IRequestHandler<IEndTurnRequest> endTurnHandler = new EndTurnRequestHandler();
    final IRequestHandler<IStartTurnRequest> startTurnHandler = new StartTurnRequestHandler();
    final IRequestHandler<ITradeRequest> tradeHandler = new TradeRequestHandler();
    final IRequestHandler<ITradeAgreementRequest> tradeAgreementHandler =
        new TradeAgreementRequestHandler();
    final IRequestHandler<ITradeConfirmationRequest> tradeConfirmationHandler =
        new TradeConfirmationRequestHandler();
    final IRequestHandler<ITradeDiscardRequest> tradeDiscardHandler =
        new TradeDiscardRequestHandler();
    final IRequestHandler<IUpgradeStructureRequest> upgradeStructureHandler =
        new UpgradeStructureRequestAtNormalPhaseHandler();

    map.put(
        RequestType.BUILD_CONNECTION,
        (IBuildConnectionRequest request) -> buildConnectionHandler.handle(hearth, request));
    map.put(
        RequestType.BUILD_INITIAL_CONNECTION,
        (IBuildConnectionRequest request) -> buildInitialConnectionHandler.handle(hearth, request));
    map.put(
        RequestType.BUILD_INITIAL_STRUCTURE,
        (IBuildStructureRequest request) -> buildInitialStructureHandler.handle(hearth, request));
    map.put(
        RequestType.BUILD_STRUCTURE,
        (IBuildStructureRequest request) -> buildStructureHandler.handle(hearth, request));
    map.put(
        RequestType.END_TURN, (IEndTurnRequest request) -> endTurnHandler.handle(hearth, request));
    map.put(
        RequestType.START_TURN,
        (IStartTurnRequest request) -> startTurnHandler.handle(hearth, request));
    map.put(RequestType.TRADE, (ITradeRequest request) -> tradeHandler.handle(hearth, request));
    map.put(
        RequestType.TRADE_AGREEMENT,
        (ITradeAgreementRequest request) -> tradeAgreementHandler.handle(hearth, request));
    map.put(
        RequestType.TRADE_CONFIRMATION,
        (ITradeConfirmationRequest request) -> tradeConfirmationHandler.handle(hearth, request));
    map.put(
        RequestType.TRADE_DISCARD,
        (ITradeDiscardRequest request) -> tradeDiscardHandler.handle(hearth, request));
    map.put(
        RequestType.UPGRADE_STRUCTURE,
        (IUpgradeStructureRequest request) -> upgradeStructureHandler.handle(hearth, request));

    return map;
  }

  private void processTurnRequest(IRequest request) {

    Consumer<? extends IRequest> consumer = handlersMap.get(request.getType());
    if (consumer != null) {
      consumeRequest(consumer, request);
    }
  }
}
