package com.ucm.dasi.catan.game;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.fail;

import com.ucm.dasi.catan.board.BoardElementType;
import com.ucm.dasi.catan.board.CatanEditableBoard;
import com.ucm.dasi.catan.board.ICatanEditableBoard;
import com.ucm.dasi.catan.board.connection.BoardConnection;
import com.ucm.dasi.catan.board.connection.ConnectionType;
import com.ucm.dasi.catan.board.connection.IBoardConnection;
import com.ucm.dasi.catan.board.element.IBoardElement;
import com.ucm.dasi.catan.board.element.IOwnedElement;
import com.ucm.dasi.catan.board.exception.InvalidBoardDimensionsException;
import com.ucm.dasi.catan.board.exception.InvalidBoardElementException;
import com.ucm.dasi.catan.board.structure.BoardStructure;
import com.ucm.dasi.catan.board.structure.IBoardStructure;
import com.ucm.dasi.catan.board.structure.StructureType;
import com.ucm.dasi.catan.board.terrain.BoardTerrain;
import com.ucm.dasi.catan.board.terrain.IBoardTerrain;
import com.ucm.dasi.catan.board.terrain.TerrainType;
import com.ucm.dasi.catan.exception.NonNullInputException;
import com.ucm.dasi.catan.exception.NonVoidCollectionException;
import com.ucm.dasi.catan.player.IPlayer;
import com.ucm.dasi.catan.player.Player;
import com.ucm.dasi.catan.request.BuildConnectionRequest;
import com.ucm.dasi.catan.request.BuildStructureRequest;
import com.ucm.dasi.catan.request.IRequest;
import com.ucm.dasi.catan.resource.ResourceManager;
import com.ucm.dasi.catan.resource.ResourceType;
import com.ucm.dasi.catan.resource.exception.NegativeNumberException;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class CatanGameEngineTest {

  @DisplayName(
      "It must not process a valid build connection request if the player has not enought resources")
  @Tag(value = "CatanBoardEngine")
  @Test
  public void itMustNotProcessAValidBuildConnectionRequestI()
      throws NegativeNumberException, InvalidBoardDimensionsException, InvalidBoardElementException,
          NonNullInputException, NonVoidCollectionException {

    Map<ResourceType, Integer> playerResources = new TreeMap<ResourceType, Integer>();

    IPlayer player1 = new Player(0, new ResourceManager(playerResources));
    IPlayer[] players = {player1};
    ICatanEditableBoard board = buildStandardBoard(player1);

    AtomicBoolean requestFailed = new AtomicBoolean(false);

    Consumer<IRequest> errorHandler =
        (request) -> {
          requestFailed.set(true);
        };

    CatanGameEngine engine = new CatanGameEngine(board, players, 0, true, errorHandler);

    int requestX = 3;
    int requestY = 2;
    IRequest[] requests = {
      new BuildConnectionRequest(player1, ConnectionType.Road, requestX, requestY)
    };

    engine.processRequests(requests);

    assertSame(true, requestFailed.get());
  }

  @DisplayName(
      "It must not process a valid build connection request if the player is not the active one")
  @Tag(value = "CatanBoardEngine")
  @Test
  public void itMustNotProcessAValidBuildConnectionRequestII()
      throws NegativeNumberException, InvalidBoardDimensionsException, InvalidBoardElementException,
          NonNullInputException, NonVoidCollectionException {

    Map<ResourceType, Integer> playerResources = new TreeMap<ResourceType, Integer>();

    playerResources.put(ResourceType.Brick, 1);
    playerResources.put(ResourceType.Grain, 1);
    playerResources.put(ResourceType.Lumber, 1);
    playerResources.put(ResourceType.Wool, 1);

    IPlayer player1 = new Player(0, new ResourceManager(playerResources));
    IPlayer player2 = new Player(1, new ResourceManager(playerResources));
    IPlayer[] players = {player1, player2};
    ICatanEditableBoard board = buildStandardBoard(player2);

    AtomicBoolean requestFailed = new AtomicBoolean(false);

    Consumer<IRequest> errorHandler =
        (request) -> {
          requestFailed.set(true);
        };

    CatanGameEngine engine = new CatanGameEngine(board, players, 0, true, errorHandler);

    int requestX = 3;
    int requestY = 2;
    IRequest[] requests = {
      new BuildConnectionRequest(player2, ConnectionType.Road, requestX, requestY)
    };

    engine.processRequests(requests);

    assertSame(true, requestFailed.get());
  }

  @DisplayName("It must not process a valid build connection request if the turn is not started")
  @Tag(value = "CatanBoardEngine")
  @Test
  public void itMustNotProcessAValidBuildConnectionRequestIII()
      throws NegativeNumberException, InvalidBoardDimensionsException, InvalidBoardElementException,
          NonNullInputException, NonVoidCollectionException {

    Map<ResourceType, Integer> playerResources = new TreeMap<ResourceType, Integer>();

    playerResources.put(ResourceType.Brick, 1);
    playerResources.put(ResourceType.Grain, 1);
    playerResources.put(ResourceType.Lumber, 1);
    playerResources.put(ResourceType.Wool, 1);

    IPlayer player = new Player(0, new ResourceManager(playerResources));
    IPlayer[] players = {player};
    ICatanEditableBoard board = buildStandardBoard(player);

    AtomicBoolean requestFailed = new AtomicBoolean(false);

    Consumer<IRequest> errorHandler =
        (request) -> {
          requestFailed.set(true);
        };

    CatanGameEngine engine = new CatanGameEngine(board, players, 0, false, errorHandler);

    int requestX = 3;
    int requestY = 2;
    IRequest[] requests = {
      new BuildConnectionRequest(player, ConnectionType.Road, requestX, requestY)
    };

    engine.processRequests(requests);

    assertSame(true, requestFailed.get());
  }

  @DisplayName(
      "It must not process a valid build structure request if the player has not enought resources")
  @Tag(value = "CatanBoardEngine")
  @Test
  public void itMustNotProcessAValidBuildStructureRequestI()
      throws InvalidBoardDimensionsException, InvalidBoardElementException, NegativeNumberException,
          NonNullInputException, NonVoidCollectionException {

    Map<ResourceType, Integer> playerResources = new TreeMap<ResourceType, Integer>();

    IPlayer player1 = new Player(0, new ResourceManager(playerResources));
    IPlayer[] players = {player1};
    ICatanEditableBoard board = buildStandardBoard(player1);

    AtomicBoolean requestFailed = new AtomicBoolean(false);

    Consumer<IRequest> errorHandler =
        (request) -> {
          requestFailed.set(true);
        };

    CatanGameEngine engine = new CatanGameEngine(board, players, 0, true, errorHandler);

    int requestX = 2;
    int requestY = 2;

    IRequest[] requests = {
      new BuildStructureRequest(player1, StructureType.Settlement, requestX, requestY)
    };

    engine.processRequests(requests);

    assertSame(true, requestFailed.get());
  }

  @DisplayName(
      "It must not process a valid build structure request if the player is not the active one")
  @Tag(value = "CatanBoardEngine")
  @Test
  public void itMustNotProcessAValidBuildStructureRequestII()
      throws InvalidBoardDimensionsException, InvalidBoardElementException, NegativeNumberException,
          NonNullInputException, NonVoidCollectionException {

    Map<ResourceType, Integer> playerResources = new TreeMap<ResourceType, Integer>();

    playerResources.put(ResourceType.Brick, 1);
    playerResources.put(ResourceType.Grain, 1);
    playerResources.put(ResourceType.Lumber, 1);
    playerResources.put(ResourceType.Wool, 1);

    IPlayer player1 = new Player(0, new ResourceManager(playerResources));
    IPlayer player2 = new Player(1, new ResourceManager(playerResources));
    IPlayer[] players = {player1, player2};
    ICatanEditableBoard board = buildStandardBoard(player2);

    AtomicBoolean requestFailed = new AtomicBoolean(false);

    Consumer<IRequest> errorHandler =
        (request) -> {
          requestFailed.set(true);
        };

    CatanGameEngine engine = new CatanGameEngine(board, players, 0, true, errorHandler);

    int requestX = 2;
    int requestY = 2;

    IRequest[] requests = {
      new BuildStructureRequest(player2, StructureType.Settlement, requestX, requestY)
    };

    engine.processRequests(requests);

    assertSame(true, requestFailed.get());
  }

  @DisplayName("It must not process a valid build structure request if the turn is not started")
  @Tag(value = "CatanBoardEngine")
  @Test
  public void itMustNotProcessAValidBuildStructureRequestIII()
      throws InvalidBoardDimensionsException, InvalidBoardElementException, NegativeNumberException,
          NonNullInputException, NonVoidCollectionException {

    Map<ResourceType, Integer> playerResources = new TreeMap<ResourceType, Integer>();

    playerResources.put(ResourceType.Brick, 1);
    playerResources.put(ResourceType.Grain, 1);
    playerResources.put(ResourceType.Lumber, 1);
    playerResources.put(ResourceType.Wool, 1);

    IPlayer player = new Player(0, new ResourceManager(playerResources));
    IPlayer[] players = {player};
    ICatanEditableBoard board = buildStandardBoard(player);

    AtomicBoolean requestFailed = new AtomicBoolean(false);

    Consumer<IRequest> errorHandler =
        (request) -> {
          requestFailed.set(true);
        };

    CatanGameEngine engine = new CatanGameEngine(board, players, 0, false, errorHandler);

    int requestX = 2;
    int requestY = 2;

    IRequest[] requests = {
      new BuildStructureRequest(player, StructureType.Settlement, requestX, requestY)
    };

    engine.processRequests(requests);

    assertSame(true, requestFailed.get());
  }

  @DisplayName("It must process a valid build connection request")
  @Tag(value = "CatanBoardEngine")
  @Test
  public void itMustProcessAValidBuildConnectionRequest()
      throws NegativeNumberException, InvalidBoardDimensionsException, InvalidBoardElementException,
          NonNullInputException, NonVoidCollectionException {

    Map<ResourceType, Integer> playerResources = new TreeMap<ResourceType, Integer>();

    playerResources.put(ResourceType.Brick, 1);
    playerResources.put(ResourceType.Grain, 1);
    playerResources.put(ResourceType.Lumber, 1);
    playerResources.put(ResourceType.Wool, 1);

    IPlayer player = new Player(0, new ResourceManager(playerResources));
    IPlayer[] players = {player};
    ICatanEditableBoard board = buildStandardBoard(player);
    Consumer<IRequest> errorHandler =
        (request) -> {
          fail();
        };

    CatanGameEngine engine = new CatanGameEngine(board, players, 0, true, errorHandler);

    int requestX = 3;
    int requestY = 2;
    IRequest[] requests = {
      new BuildConnectionRequest(player, ConnectionType.Road, requestX, requestY)
    };

    engine.processRequests(requests);

    IBoardElement elementBuilt = board.get(requestX, requestY);

    assertSame(BoardElementType.Connection, elementBuilt.getElementType());
    assertSame(ConnectionType.Road, ((IBoardConnection) elementBuilt).getType());
    assertSame(player.getId(), ((IOwnedElement) elementBuilt).getOwner().getId());
    assertSame(0, player.getResourceManager().getResource(ResourceType.Brick));
    assertSame(1, player.getResourceManager().getResource(ResourceType.Grain));
    assertSame(0, player.getResourceManager().getResource(ResourceType.Lumber));
    assertSame(1, player.getResourceManager().getResource(ResourceType.Wool));
  }

  @DisplayName("It must process a valid build structure request")
  @Tag(value = "CatanBoardEngine")
  @Test
  public void itMustProcessAValidBuildStructureRequest()
      throws InvalidBoardDimensionsException, InvalidBoardElementException, NegativeNumberException,
          NonNullInputException, NonVoidCollectionException {

    Map<ResourceType, Integer> playerResources = new TreeMap<ResourceType, Integer>();

    playerResources.put(ResourceType.Brick, 1);
    playerResources.put(ResourceType.Grain, 1);
    playerResources.put(ResourceType.Lumber, 1);
    playerResources.put(ResourceType.Wool, 1);

    IPlayer player = new Player(0, new ResourceManager(playerResources));
    IPlayer[] players = {player};
    ICatanEditableBoard board = buildStandardBoard(player);
    Consumer<IRequest> errorHandler =
        (request) -> {
          fail();
        };

    CatanGameEngine engine = new CatanGameEngine(board, players, 0, true, errorHandler);

    int requestX = 2;
    int requestY = 2;

    IRequest[] requests = {
      new BuildStructureRequest(player, StructureType.Settlement, requestX, requestY)
    };

    engine.processRequests(requests);

    IBoardElement elementBuilt = board.get(requestX, requestY);

    assertSame(BoardElementType.Structure, elementBuilt.getElementType());
    assertSame(StructureType.Settlement, ((IBoardStructure) elementBuilt).getType());
    assertSame(player.getId(), ((IOwnedElement) elementBuilt).getOwner().getId());
    assertSame(0, player.getResourceManager().getResource(ResourceType.Brick));
    assertSame(0, player.getResourceManager().getResource(ResourceType.Grain));
    assertSame(0, player.getResourceManager().getResource(ResourceType.Lumber));
    assertSame(0, player.getResourceManager().getResource(ResourceType.Wool));
  }

  private IBoardTerrain buildMountainTerrain() {
    return new BoardTerrain(6, TerrainType.Mountains);
  }

  private IBoardStructure buildNoneStructure() {
    return new BoardStructure(null, new ResourceManager(), StructureType.None);
  }

  private IBoardTerrain buildNoneTerrain() {
    return new BoardTerrain(0, TerrainType.None);
  }

  private IBoardConnection buildPlayerConnection(IPlayer player) {
    return new BoardConnection(player, new ResourceManager(), ConnectionType.Road);
  }

  private ICatanEditableBoard buildStandardBoard(IPlayer player1)
      throws InvalidBoardDimensionsException, InvalidBoardElementException {
    IBoardElement[][] elements = {
      {
        buildNoneStructure(),
        buildVoidConnection(),
        buildNoneStructure(),
        buildVoidConnection(),
        buildNoneStructure(),
      },
      {
        buildVoidConnection(),
        buildNoneTerrain(),
        buildPlayerConnection(player1),
        buildMountainTerrain(),
        buildVoidConnection(),
      },
      {
        buildNoneStructure(),
        buildVoidConnection(),
        buildNoneStructure(),
        buildVoidConnection(),
        buildNoneStructure(),
      },
      {
        buildVoidConnection(),
        buildNoneTerrain(),
        buildVoidConnection(),
        buildMountainTerrain(),
        buildVoidConnection(),
      },
      {
        buildNoneStructure(),
        buildVoidConnection(),
        buildNoneStructure(),
        buildVoidConnection(),
        buildNoneStructure(),
      },
    };

    return new CatanEditableBoard(5, 5, elements);
  }

  private IBoardConnection buildVoidConnection() {
    return new BoardConnection(null, new ResourceManager(), ConnectionType.Void);
  }
}
