package com.ucm.dasi.catan.game;

import com.ucm.dasi.catan.board.BoardElementType;
import com.ucm.dasi.catan.board.ICatanEditableBoard;
import com.ucm.dasi.catan.board.connection.BoardConnection;
import com.ucm.dasi.catan.board.connection.ConnectionDirection;
import com.ucm.dasi.catan.board.connection.ConnectionType;
import com.ucm.dasi.catan.board.element.IOwnedElement;
import com.ucm.dasi.catan.board.exception.InvalidBoardElementException;
import com.ucm.dasi.catan.board.structure.BoardStructure;
import com.ucm.dasi.catan.board.structure.StructureType;
import com.ucm.dasi.catan.exception.NonNullInputException;
import com.ucm.dasi.catan.exception.NonVoidCollectionException;
import com.ucm.dasi.catan.game.exception.InvalidLogException;
import com.ucm.dasi.catan.game.generator.INumberGenerator;
import com.ucm.dasi.catan.game.handler.GameEngineHandlersMap;
import com.ucm.dasi.catan.game.handler.IGameEngineHandlersMap;
import com.ucm.dasi.catan.game.log.IGameLog;
import com.ucm.dasi.catan.game.log.ILogEntry;
import com.ucm.dasi.catan.game.log.LogEntry;
import com.ucm.dasi.catan.player.IPlayer;
import com.ucm.dasi.catan.request.IBuildConnectionRequest;
import com.ucm.dasi.catan.request.IBuildStructureRequest;
import com.ucm.dasi.catan.request.IEndTurnRequest;
import com.ucm.dasi.catan.request.IRequest;
import com.ucm.dasi.catan.request.IStartTurnRequest;
import com.ucm.dasi.catan.request.IUpgradeStructureRequest;
import com.ucm.dasi.catan.request.RequestType;
import com.ucm.dasi.catan.resource.exception.NotEnoughtResourcesException;
import com.ucm.dasi.catan.resource.production.IResourceProduction;
import com.ucm.dasi.catan.resource.provider.DefaultConnectionCostProvider;
import com.ucm.dasi.catan.resource.provider.DefaultStructureCostProvider;
import com.ucm.dasi.catan.resource.provider.IResourceManagerProvider;
import java.util.ArrayList;
import java.util.function.Consumer;

public class CatanGameEngine extends CatanGame<ICatanEditableBoard> implements ICatanGameEngine {

  private IResourceManagerProvider<ConnectionType> connectionCostProvider;

  private Consumer<IRequest> errorHandler;

  private IGameLog gameLog;

  private IGameEngineHandlersMap handlersMap;

  private INumberGenerator numberGenerator;

  private IResourceManagerProvider<StructureType> structureCostProvider;

  public CatanGameEngine(
      ICatanEditableBoard board,
      IPlayer[] players,
      int pointsToWin,
      GameState state,
      int turnIndex,
      boolean turnStarted,
      Consumer<IRequest> errorHandler,
      IGameLog gameLog,
      INumberGenerator numberGenerator)
      throws NonNullInputException, NonVoidCollectionException, InvalidLogException {

    super(board, players, pointsToWin, state, turnIndex, turnStarted);

    checkLog(gameLog);

    connectionCostProvider = new DefaultConnectionCostProvider();
    this.errorHandler = errorHandler;
    this.gameLog = gameLog;
    handlersMap = new GameEngineHandlersMap(generateMap());
    this.numberGenerator = numberGenerator;
    structureCostProvider = new DefaultStructureCostProvider();
  }

  @Override
  public ILogEntry getLog(int turn) {
    return gameLog.get(turn);
  }

  @Override
  public void processRequests(IRequest[] requests) {
    for (IRequest request : requests) {
      processTurnRequest(request);
    }
  }

  protected void handleRequestError(IRequest request) {
    errorHandler.accept(request);
  }

  private void checkLog(IGameLog log) throws InvalidLogException {
    int entries = log.size();
    int expectedEntries = isTurnStarted() ? getTurnNumber() + 1 : getTurnNumber();

    if (entries != expectedEntries) {
      throw new InvalidLogException(expectedEntries);
    }
  }

  private boolean isConnectionConnected(IPlayer player, int x, int y) {
    if (getBoard().get(x, y).getElementType() != BoardElementType.CONNECTION) {
      return false;
    }

    ConnectionDirection direction = getBoard().getConnectionDirection(x, y);

    if (direction == ConnectionDirection.HORIZONTAL) {
      return this.isHorizontalConnectionConnected(player, x, y);
    } else if (direction == ConnectionDirection.VERTICAL) {
      return this.isVerticalConnectionConnected(player, x, y);
    } else {
      return false;
    }
  }

  private boolean isHorizontalConnectionConnected(IPlayer player, int x, int y) {
    // Get the structure locations. The board topology ensures their existence
    return isStructurePointConnectedOrControlled(player, x - 1, y)
        || isStructurePointConnectedOrControlled(player, x + 1, y);
  }

  private boolean isStructurePointConnected(IPlayer player, int x, int y) {
    return getBoard().get(x, y).getElementType() == BoardElementType.STRUCTURE
        && ((x > 0
                && isStructureConnectedCheckConnection(
                    player, (IOwnedElement) getBoard().get(x - 1, y)))
            || (x + 1 < getBoard().getWidth()
                && isStructureConnectedCheckConnection(
                    player, (IOwnedElement) getBoard().get(x + 1, y)))
            || (y > 0
                && isStructureConnectedCheckConnection(
                    player, (IOwnedElement) getBoard().get(x, y - 1)))
            || (y + 1 > getBoard().getHeight()
                && isStructureConnectedCheckConnection(
                    player, (IOwnedElement) getBoard().get(x, y + 1))));
  }

  private boolean isStructurePointConnectedOrControlled(IPlayer player, int x, int y) {
    return (getBoard().get(x, y).getElementType() == BoardElementType.STRUCTURE
            && ((IOwnedElement) getBoard().get(x, y)).getOwner() != null
            && ((IOwnedElement) getBoard().get(x, y)).getOwner().equals(player))
        || isStructurePointConnected(player, x, y);
  }

  private boolean isStructureConnectedCheckConnection(IPlayer player, IOwnedElement element) {
    return element.getOwner() != null && element.getOwner().equals(player);
  }

  private boolean isVerticalConnectionConnected(IPlayer player, int x, int y) {
    // Get the structure locations. The board topology ensures their existence
    return isStructurePointConnectedOrControlled(player, x, y - 1)
        || isStructurePointConnectedOrControlled(player, x, y + 1);
  }

  @SuppressWarnings("unchecked")
  private <T extends IRequest> void consumeRequest(Consumer<T> consumer, IRequest request) {
    consumer.accept((T) request);
  }

  private IGameEngineHandlersMap generateMap() {
    IGameEngineHandlersMap map = new GameEngineHandlersMap();

    map.put(
        RequestType.BUILD_CONNECTION,
        (IBuildConnectionRequest request) -> handleBuildConnectionRequest(request));
    map.put(
        RequestType.BUILD_INITIAL_STRUCTURE,
        (IBuildStructureRequest request) -> handleBuildInitialStructureRequest(request));
    map.put(
        RequestType.BUILD_STRUCTURE,
        (IBuildStructureRequest request) -> handleBuildStructureRequest(request));
    map.put(RequestType.END_TURN, (IEndTurnRequest request) -> handleEndTurnRequest(request));
    map.put(RequestType.START_TURN, (IStartTurnRequest request) -> handleStartTurnRequest(request));
    map.put(
        RequestType.UPGRADE_STRUCTURE,
        (IUpgradeStructureRequest request) -> handleUpgradeStructureRequest(request));

    return map;
  }

  private void handleBuildConnectionRequest(IBuildConnectionRequest request) {

    if (getState() != GameState.NORMAL) {
      handleRequestError(request);
      return;
    }

    if (!request.getPlayer().equals(getActivePlayer())) {
      handleRequestError(request);
      return;
    }

    if (!isTurnStarted()) {
      handleRequestError(request);
      return;
    }

    if (!isConnectionConnected(request.getPlayer(), request.getX(), request.getY())) {
      handleRequestError(request);
      return;
    }

    BoardConnection element =
        new BoardConnection(
            request.getPlayer(),
            connectionCostProvider.getResourceManager(request.getConnectionType()),
            request.getConnectionType());

    try {
      getBoard().build(element, request.getX(), request.getY());
      getActivePlayer().getResourceManager().substract(element.getCost());
    } catch (InvalidBoardElementException | NotEnoughtResourcesException e) {
      handleRequestError(request);
    }

    gameLog.get(getTurnNumber()).add(request);
  }

  private void handleBuildInitialStructureRequest(IBuildStructureRequest request) {
    if (!isTurnToBuildInitialStructure()) {
      handleRequestError(request);
      return;
    }

    if (!request.getPlayer().equals(getActivePlayer())) {
      handleRequestError(request);
      return;
    }

    if (!isTurnStarted()) {
      handleRequestError(request);
      return;
    }

    BoardStructure element =
        new BoardStructure(
            request.getPlayer(),
            structureCostProvider.getResourceManager(request.getStructureType()),
            request.getStructureType());

    try {
      getBoard().build(element, request.getX(), request.getY());
    } catch (InvalidBoardElementException e) {
      handleRequestError(request);
    }

    gameLog.get(getTurnNumber()).add(request);
  }

  private void handleBuildStructureRequest(IBuildStructureRequest request) {

    if (getState() != GameState.NORMAL) {
      handleRequestError(request);
      return;
    }

    if (!request.getPlayer().equals(getActivePlayer())) {
      handleRequestError(request);
      return;
    }

    if (!isTurnStarted()) {
      handleRequestError(request);
      return;
    }

    if (!isStructurePointConnected(request.getPlayer(), request.getX(), request.getY())) {
      handleRequestError(request);
      return;
    }

    BoardStructure element =
        new BoardStructure(
            request.getPlayer(),
            structureCostProvider.getResourceManager(request.getStructureType()),
            request.getStructureType());

    try {
      getBoard().build(element, request.getX(), request.getY());
      getActivePlayer().getResourceManager().substract(element.getCost());
    } catch (InvalidBoardElementException | NotEnoughtResourcesException e) {
      handleRequestError(request);
    }

    gameLog.get(getTurnNumber()).add(request);
  }

  private void handleEndTurnRequest(IEndTurnRequest request) {

    if (getState() == GameState.ENDED) {
      handleRequestError(request);
      return;
    }

    if (!request.getPlayer().equals(getActivePlayer())) {
      handleRequestError(request);
      return;
    }

    if (!isTurnStarted()) {
      handleRequestError(request);
      return;
    }

    switchTurnStarted();

    gameLog.get(getTurnNumber()).add(request);

    if (hasActivePlayerWon()) {
      endGame();
    } else {
      passTurn();
    }
  }

  private void handleStartTurnRequest(IStartTurnRequest request) {

    if (getState() == GameState.ENDED) {
      handleRequestError(request);
      return;
    }

    if (!request.getPlayer().equals(getActivePlayer())) {
      handleRequestError(request);
      return;
    }

    if (isTurnStarted()) {
      handleRequestError(request);
      return;
    }

    switchTurnStarted();

    int productionNumber = numberGenerator.getNextProductionNumber();
    produceResources(productionNumber);

    ArrayList<IRequest> requestList = new ArrayList<IRequest>();
    requestList.add(request);

    gameLog.set(getTurnNumber(), new LogEntry(productionNumber, requestList));
  }

  private void handleUpgradeStructureRequest(IUpgradeStructureRequest request) {

    if (getState() != GameState.NORMAL) {
      handleRequestError(request);
      return;
    }

    if (request.getPlayer().getId() != getActivePlayer().getId()) {
      handleRequestError(request);
      return;
    }

    if (!isTurnStarted()) {
      handleRequestError(request);
      return;
    }

    BoardStructure element =
        new BoardStructure(
            request.getPlayer(),
            structureCostProvider.getResourceManager(request.getStructureType()),
            request.getStructureType());

    try {
      getBoard().upgrade(element, request.getX(), request.getY());
      getActivePlayer().getResourceManager().substract(element.getCost());
    } catch (InvalidBoardElementException | NotEnoughtResourcesException e) {
      handleRequestError(request);
    }

    gameLog.get(getTurnNumber()).add(request);
  }

  private boolean isTurnToBuildInitialStructure() {
    return getState() == GameState.FOUNDATION
        && Math.floor(getTurnNumber() / getPlayers().length) % 2 == 0;
  }

  private void processTurnRequest(IRequest request) {

    Consumer<? extends IRequest> consumer = handlersMap.get(request.getType());
    if (consumer != null) {
      consumeRequest(consumer, request);
    }
  }

  /**
   * Produces resources
   *
   * @param productionNumber Production number to use
   */
  private void produceResources(int productionNumber) {

    IResourceProduction production = getBoard().getProduction(productionNumber);

    for (IPlayer player : getPlayers()) {
      player.getResourceManager().add(production.getProduction(player));
    }
  }
}
