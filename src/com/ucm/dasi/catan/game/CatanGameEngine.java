package com.ucm.dasi.catan.game;

import com.ucm.dasi.catan.board.BoardElementType;
import com.ucm.dasi.catan.board.ICatanEditableBoard;
import com.ucm.dasi.catan.board.connection.BoardConnection;
import com.ucm.dasi.catan.board.connection.ConnectionDirection;
import com.ucm.dasi.catan.board.element.IOwnedElement;
import com.ucm.dasi.catan.board.exception.InvalidBoardElementException;
import com.ucm.dasi.catan.board.structure.BoardStructure;
import com.ucm.dasi.catan.exception.NonNullInputException;
import com.ucm.dasi.catan.exception.NonVoidCollectionException;
import com.ucm.dasi.catan.game.handler.GameEngineHandlersMap;
import com.ucm.dasi.catan.game.handler.IGameEngineHandlersMap;
import com.ucm.dasi.catan.player.IPlayer;
import com.ucm.dasi.catan.request.IBuildConnectionRequest;
import com.ucm.dasi.catan.request.IBuildStructureRequest;
import com.ucm.dasi.catan.request.IEndTurnRequest;
import com.ucm.dasi.catan.request.IRequest;
import com.ucm.dasi.catan.request.IStartTurnRequest;
import com.ucm.dasi.catan.request.IUpgradeStructureRequest;
import com.ucm.dasi.catan.request.RequestType;
import com.ucm.dasi.catan.resource.exception.NotEnoughtResourcesException;
import com.ucm.dasi.catan.resource.provider.ConnectionCostProvider;
import com.ucm.dasi.catan.resource.provider.StructureCostProvider;
import java.util.function.Consumer;

public class CatanGameEngine extends CatanGame<ICatanEditableBoard> implements ICatanGameEngine {

  private ConnectionCostProvider connectionCostProvider;

  private StructureCostProvider structureCostProvider;

  private Consumer<IRequest> errorHandler;

  private IGameEngineHandlersMap handlersMap;

  public CatanGameEngine(
      ICatanEditableBoard board,
      IPlayer[] players,
      int turnIndex,
      boolean turnStarted,
      Consumer<IRequest> errorHandler)
      throws NonNullInputException, NonVoidCollectionException {

    super(board, players, turnIndex, turnStarted);

    connectionCostProvider = ConnectionCostProvider.buildDefaultProvider();
    structureCostProvider = StructureCostProvider.buildDefaultProvider();
    this.errorHandler = errorHandler;
    this.handlersMap = new GameEngineHandlersMap(generateMap());
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

  private boolean isConnectionConnected(IPlayer player, int x, int y) {
    if (getBoard().get(x, y).getElementType() != BoardElementType.Connection) {
      return false;
    }

    ConnectionDirection direction = getBoard().getConnectionDirection(x, y);

    if (direction == ConnectionDirection.Horizontal) {
      return this.isHorizontalConnectionConnected(player, x, y);
    } else if (direction == ConnectionDirection.Vertical) {
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
    return getBoard().get(x, y).getElementType() == BoardElementType.Structure
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
    return (getBoard().get(x, y).getElementType() == BoardElementType.Structure
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

  private void processTurnRequest(IRequest request) {

    Consumer<? extends IRequest> consumer = handlersMap.get(request.getType());
    if (consumer != null) {
      consumeRequest(consumer, request);
    }
  }

  @SuppressWarnings("unchecked")
  private <T extends IRequest> void consumeRequest(Consumer<T> consumer, IRequest request) {
    consumer.accept((T) request);
  }

  private IGameEngineHandlersMap generateMap() {
    IGameEngineHandlersMap map = new GameEngineHandlersMap();

    map.put(
        RequestType.BuildConnection,
        (IBuildConnectionRequest request) -> handleBuildConnectionRequest(request));
    map.put(
        RequestType.BuildStructure,
        (IBuildStructureRequest request) -> handleBuildStructureRequest(request));
    map.put(RequestType.EndTurn, (IEndTurnRequest request) -> handleEndTurnRequest(request));
    map.put(RequestType.StartTurn, (IStartTurnRequest request) -> handleStartTurnRequest(request));
    map.put(
        RequestType.UpgradeStructure,
        (IUpgradeStructureRequest request) -> handleUpgradeStructureRequest(request));

    return map;
  }

  private void handleBuildConnectionRequest(IBuildConnectionRequest request) {
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
  }

  private void handleBuildStructureRequest(IBuildStructureRequest request) {
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
  }

  private void handleEndTurnRequest(IEndTurnRequest request) {
    if (!request.getPlayer().equals(getActivePlayer())) {
      handleRequestError(request);
      return;
    }

    if (!isTurnStarted()) {
      handleRequestError(request);
      return;
    }

    switchTurnStarted();
    passTurn();
  }

  private void handleStartTurnRequest(IStartTurnRequest request) {
    if (!request.getPlayer().equals(getActivePlayer())) {
      handleRequestError(request);
      return;
    }

    if (isTurnStarted()) {
      handleRequestError(request);
      return;
    }

    switchTurnStarted();
  }

  private void handleUpgradeStructureRequest(IUpgradeStructureRequest request) {
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
  }
}
