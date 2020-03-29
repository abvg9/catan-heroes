package com.ucm.dasi.catan.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import com.ucm.dasi.catan.board.group.StructureTerrainTypesPair;
import com.ucm.dasi.catan.board.structure.BoardStructure;
import com.ucm.dasi.catan.board.structure.IBoardStructure;
import com.ucm.dasi.catan.board.structure.StructureType;
import com.ucm.dasi.catan.board.terrain.BoardTerrain;
import com.ucm.dasi.catan.board.terrain.IBoardTerrain;
import com.ucm.dasi.catan.board.terrain.TerrainType;
import com.ucm.dasi.catan.exception.NonNullInputException;
import com.ucm.dasi.catan.exception.NonVoidCollectionException;
import com.ucm.dasi.catan.game.generator.CatanRandomGenerator;
import com.ucm.dasi.catan.game.generator.ConstantNumberGenerator;
import com.ucm.dasi.catan.player.IPlayer;
import com.ucm.dasi.catan.player.Player;
import com.ucm.dasi.catan.request.BuildConnectionRequest;
import com.ucm.dasi.catan.request.BuildStructureRequest;
import com.ucm.dasi.catan.request.EndTurnRequest;
import com.ucm.dasi.catan.request.IRequest;
import com.ucm.dasi.catan.request.StartTurnRequest;
import com.ucm.dasi.catan.request.UpgradeStructureRequest;
import com.ucm.dasi.catan.resource.IResourceStorage;
import com.ucm.dasi.catan.resource.ResourceManager;
import com.ucm.dasi.catan.resource.ResourceType;
import com.ucm.dasi.catan.resource.provider.DefaultTerrainProductionProvider;
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
      throws InvalidBoardDimensionsException, InvalidBoardElementException, NonNullInputException,
          NonVoidCollectionException {

    Map<ResourceType, Integer> playerResources = new TreeMap<ResourceType, Integer>();

    IPlayer player1 = new Player(0, new ResourceManager(playerResources));
    IPlayer[] players = {player1};
    ICatanEditableBoard board = buildStandardBoard(player1);

    AtomicBoolean requestFailed = new AtomicBoolean(false);

    Consumer<IRequest> errorHandler =
        (request) -> {
          requestFailed.set(true);
        };

    CatanGameEngine engine =
        new CatanGameEngine(
            board, players, GameState.NORMAL, 0, true, errorHandler, new CatanRandomGenerator());

    int requestX = 3;
    int requestY = 2;
    IRequest[] requests = {
      new BuildConnectionRequest(player1, ConnectionType.ROAD, requestX, requestY)
    };

    engine.processRequests(requests);

    assertSame(true, requestFailed.get());
  }

  @DisplayName(
      "It must not process a valid build connection request if the player is not the active one")
  @Tag(value = "CatanBoardEngine")
  @Test
  public void itMustNotProcessAValidBuildConnectionRequestII()
      throws InvalidBoardDimensionsException, InvalidBoardElementException, NonNullInputException,
          NonVoidCollectionException {

    Map<ResourceType, Integer> playerResources = new TreeMap<ResourceType, Integer>();

    playerResources.put(ResourceType.BRICK, 1);
    playerResources.put(ResourceType.GRAIN, 1);
    playerResources.put(ResourceType.LUMBER, 1);
    playerResources.put(ResourceType.WOOL, 1);

    IPlayer player1 = new Player(0, new ResourceManager(playerResources));
    IPlayer player2 = new Player(1, new ResourceManager(playerResources));
    IPlayer[] players = {player1, player2};
    ICatanEditableBoard board = buildStandardBoard(player2);

    AtomicBoolean requestFailed = new AtomicBoolean(false);

    Consumer<IRequest> errorHandler =
        (request) -> {
          requestFailed.set(true);
        };

    CatanGameEngine engine =
        new CatanGameEngine(
            board, players, GameState.NORMAL, 0, true, errorHandler, new CatanRandomGenerator());

    int requestX = 3;
    int requestY = 2;
    IRequest[] requests = {
      new BuildConnectionRequest(player2, ConnectionType.ROAD, requestX, requestY)
    };

    engine.processRequests(requests);

    assertSame(true, requestFailed.get());
  }

  @DisplayName("It must not process a valid build connection request if the turn is not started")
  @Tag(value = "CatanBoardEngine")
  @Test
  public void itMustNotProcessAValidBuildConnectionRequestIII()
      throws InvalidBoardDimensionsException, InvalidBoardElementException, NonNullInputException,
          NonVoidCollectionException {

    Map<ResourceType, Integer> playerResources = new TreeMap<ResourceType, Integer>();

    playerResources.put(ResourceType.BRICK, 1);
    playerResources.put(ResourceType.GRAIN, 1);
    playerResources.put(ResourceType.LUMBER, 1);
    playerResources.put(ResourceType.WOOL, 1);

    IPlayer player = new Player(0, new ResourceManager(playerResources));
    IPlayer[] players = {player};
    ICatanEditableBoard board = buildStandardBoard(player);

    AtomicBoolean requestFailed = new AtomicBoolean(false);

    Consumer<IRequest> errorHandler =
        (request) -> {
          requestFailed.set(true);
        };

    CatanGameEngine engine =
        new CatanGameEngine(
            board, players, GameState.NORMAL, 0, false, errorHandler, new CatanRandomGenerator());

    int requestX = 3;
    int requestY = 2;
    IRequest[] requests = {
      new BuildConnectionRequest(player, ConnectionType.ROAD, requestX, requestY)
    };

    engine.processRequests(requests);

    assertSame(true, requestFailed.get());
  }

  @DisplayName(
      "It must not process a valid build connection request if the game state is not normal")
  @Tag(value = "CatanBoardEngine")
  @Test
  public void itMustNotProcessAValidBuildConnectionRequestIV()
      throws InvalidBoardDimensionsException, InvalidBoardElementException, NonNullInputException,
          NonVoidCollectionException {

    Map<ResourceType, Integer> playerResources = new TreeMap<ResourceType, Integer>();

    playerResources.put(ResourceType.BRICK, 1);
    playerResources.put(ResourceType.GRAIN, 1);
    playerResources.put(ResourceType.LUMBER, 1);
    playerResources.put(ResourceType.WOOL, 1);

    IPlayer player = new Player(0, new ResourceManager(playerResources));
    IPlayer[] players = {player};
    ICatanEditableBoard board = buildStandardBoard(player);

    AtomicBoolean requestFailed = new AtomicBoolean(false);

    Consumer<IRequest> errorHandler =
        (request) -> {
          requestFailed.set(true);
        };

    CatanGameEngine engine =
        new CatanGameEngine(
            board,
            players,
            GameState.FOUNDATION,
            0,
            false,
            errorHandler,
            new CatanRandomGenerator());

    int requestX = 3;
    int requestY = 2;
    IRequest[] requests = {
      new BuildConnectionRequest(player, ConnectionType.ROAD, requestX, requestY)
    };

    engine.processRequests(requests);

    assertSame(true, requestFailed.get());
  }

  @DisplayName(
      "It must not process a valid build structure request if the player has not enought resources")
  @Tag(value = "CatanBoardEngine")
  @Test
  public void itMustNotProcessAValidBuildStructureRequestI()
      throws InvalidBoardDimensionsException, InvalidBoardElementException, NonNullInputException,
          NonVoidCollectionException {

    Map<ResourceType, Integer> playerResources = new TreeMap<ResourceType, Integer>();

    IPlayer player1 = new Player(0, new ResourceManager(playerResources));
    IPlayer[] players = {player1};
    ICatanEditableBoard board = buildStandardBoard(player1);

    AtomicBoolean requestFailed = new AtomicBoolean(false);

    Consumer<IRequest> errorHandler =
        (request) -> {
          requestFailed.set(true);
        };

    CatanGameEngine engine =
        new CatanGameEngine(
            board, players, GameState.NORMAL, 0, true, errorHandler, new CatanRandomGenerator());

    int requestX = 2;
    int requestY = 2;

    IRequest[] requests = {
      new BuildStructureRequest(player1, StructureType.SETTLEMENT, requestX, requestY)
    };

    engine.processRequests(requests);

    assertSame(true, requestFailed.get());
  }

  @DisplayName(
      "It must not process a valid build structure request if the player is not the active one")
  @Tag(value = "CatanBoardEngine")
  @Test
  public void itMustNotProcessAValidBuildStructureRequestII()
      throws InvalidBoardDimensionsException, InvalidBoardElementException, NonNullInputException,
          NonVoidCollectionException {

    Map<ResourceType, Integer> playerResources = new TreeMap<ResourceType, Integer>();

    playerResources.put(ResourceType.BRICK, 1);
    playerResources.put(ResourceType.GRAIN, 1);
    playerResources.put(ResourceType.LUMBER, 1);
    playerResources.put(ResourceType.WOOL, 1);

    IPlayer player1 = new Player(0, new ResourceManager(playerResources));
    IPlayer player2 = new Player(1, new ResourceManager(playerResources));
    IPlayer[] players = {player1, player2};
    ICatanEditableBoard board = buildStandardBoard(player2);

    AtomicBoolean requestFailed = new AtomicBoolean(false);

    Consumer<IRequest> errorHandler =
        (request) -> {
          requestFailed.set(true);
        };

    CatanGameEngine engine =
        new CatanGameEngine(
            board, players, GameState.NORMAL, 0, true, errorHandler, new CatanRandomGenerator());

    int requestX = 2;
    int requestY = 2;

    IRequest[] requests = {
      new BuildStructureRequest(player2, StructureType.SETTLEMENT, requestX, requestY)
    };

    engine.processRequests(requests);

    assertSame(true, requestFailed.get());
  }

  @DisplayName("It must not process a valid build structure request if the turn is not started")
  @Tag(value = "CatanBoardEngine")
  @Test
  public void itMustNotProcessAValidBuildStructureRequestIII()
      throws InvalidBoardDimensionsException, InvalidBoardElementException, NonNullInputException,
          NonVoidCollectionException {

    Map<ResourceType, Integer> playerResources = new TreeMap<ResourceType, Integer>();

    playerResources.put(ResourceType.BRICK, 1);
    playerResources.put(ResourceType.GRAIN, 1);
    playerResources.put(ResourceType.LUMBER, 1);
    playerResources.put(ResourceType.WOOL, 1);

    IPlayer player = new Player(0, new ResourceManager(playerResources));
    IPlayer[] players = {player};
    ICatanEditableBoard board = buildStandardBoard(player);

    AtomicBoolean requestFailed = new AtomicBoolean(false);

    Consumer<IRequest> errorHandler =
        (request) -> {
          requestFailed.set(true);
        };

    CatanGameEngine engine =
        new CatanGameEngine(
            board, players, GameState.NORMAL, 0, false, errorHandler, new CatanRandomGenerator());

    int requestX = 2;
    int requestY = 2;

    IRequest[] requests = {
      new BuildStructureRequest(player, StructureType.SETTLEMENT, requestX, requestY)
    };

    engine.processRequests(requests);

    assertSame(true, requestFailed.get());
  }

  @DisplayName(
      "It must not process a valid build structure request if the game state is not normal")
  @Tag(value = "CatanBoardEngine")
  @Test
  public void itMustNotProcessAValidBuildStructureRequestIV()
      throws InvalidBoardDimensionsException, InvalidBoardElementException, NonNullInputException,
          NonVoidCollectionException {

    Map<ResourceType, Integer> playerResources = new TreeMap<ResourceType, Integer>();

    playerResources.put(ResourceType.BRICK, 1);
    playerResources.put(ResourceType.GRAIN, 1);
    playerResources.put(ResourceType.LUMBER, 1);
    playerResources.put(ResourceType.WOOL, 1);

    IPlayer player1 = new Player(0, new ResourceManager(playerResources));
    IPlayer[] players = {player1};
    ICatanEditableBoard board = buildStandardBoard(player1);

    AtomicBoolean requestFailed = new AtomicBoolean(false);

    Consumer<IRequest> errorHandler =
        (request) -> {
          requestFailed.set(true);
        };

    CatanGameEngine engine =
        new CatanGameEngine(
            board,
            players,
            GameState.FOUNDATION,
            0,
            true,
            errorHandler,
            new CatanRandomGenerator());

    int requestX = 2;
    int requestY = 2;

    IRequest[] requests = {
      new BuildStructureRequest(player1, StructureType.SETTLEMENT, requestX, requestY)
    };

    engine.processRequests(requests);

    assertSame(true, requestFailed.get());
  }

  @DisplayName("It must not process a valid end turn request if the game is ended")
  @Tag(value = "CatanBoardEngine")
  @Test
  public void itMustNotProcessAValidEndTurnRequestIfTheGameIsEnded()
      throws NonNullInputException, NonVoidCollectionException, InvalidBoardDimensionsException,
          InvalidBoardElementException {

    IPlayer player = new Player(0, new ResourceManager());
    IPlayer[] players = {player};
    ICatanEditableBoard board = buildStandardBoard(player);
    AtomicBoolean requestFailed = new AtomicBoolean(false);

    Consumer<IRequest> errorHandler =
        (request) -> {
          requestFailed.set(true);
        };

    CatanGameEngine engine =
        new CatanGameEngine(
            board, players, GameState.ENDED, 0, true, errorHandler, new CatanRandomGenerator());

    IRequest[] requests = {new EndTurnRequest(player)};

    engine.processRequests(requests);

    assertSame(true, requestFailed.get());
  }

  @DisplayName("It must not process a valid start turn request if the game is ended")
  @Tag(value = "CatanBoardEngine")
  @Test
  public void itMustNotProcessAValidStartTurnRequestIfTheGameIsEnded()
      throws NonNullInputException, NonVoidCollectionException, InvalidBoardDimensionsException,
          InvalidBoardElementException {

    IPlayer player = new Player(0, new ResourceManager());
    IPlayer[] players = {player};
    ICatanEditableBoard board = buildStandardBoard(player);
    AtomicBoolean requestFailed = new AtomicBoolean(false);

    Consumer<IRequest> errorHandler =
        (request) -> {
          requestFailed.set(true);
        };

    CatanGameEngine engine =
        new CatanGameEngine(
            board, players, GameState.ENDED, 0, false, errorHandler, new CatanRandomGenerator());

    IRequest[] requests = {new StartTurnRequest(player)};

    engine.processRequests(requests);

    assertSame(true, requestFailed.get());
  }

  @DisplayName("It must process a valid build connection request")
  @Tag(value = "CatanBoardEngine")
  @Test
  public void itMustProcessAValidBuildConnectionRequest()
      throws InvalidBoardDimensionsException, InvalidBoardElementException, NonNullInputException,
          NonVoidCollectionException {

    Map<ResourceType, Integer> playerResources = new TreeMap<ResourceType, Integer>();

    playerResources.put(ResourceType.BRICK, 1);
    playerResources.put(ResourceType.GRAIN, 1);
    playerResources.put(ResourceType.LUMBER, 1);
    playerResources.put(ResourceType.WOOL, 1);

    IPlayer player = new Player(0, new ResourceManager(playerResources));
    IPlayer[] players = {player};
    ICatanEditableBoard board = buildStandardBoard(player);
    Consumer<IRequest> errorHandler =
        (request) -> {
          fail();
        };

    CatanGameEngine engine =
        new CatanGameEngine(
            board, players, GameState.NORMAL, 0, true, errorHandler, new CatanRandomGenerator());

    int requestX = 3;
    int requestY = 2;
    IRequest[] requests = {
      new BuildConnectionRequest(player, ConnectionType.ROAD, requestX, requestY)
    };

    engine.processRequests(requests);

    IBoardElement elementBuilt = board.get(requestX, requestY);

    assertSame(BoardElementType.CONNECTION, elementBuilt.getElementType());
    assertSame(ConnectionType.ROAD, ((IBoardConnection) elementBuilt).getType());
    assertSame(player.getId(), ((IOwnedElement) elementBuilt).getOwner().getId());
    assertSame(0, player.getResourceManager().getResource(ResourceType.BRICK));
    assertSame(1, player.getResourceManager().getResource(ResourceType.GRAIN));
    assertSame(0, player.getResourceManager().getResource(ResourceType.LUMBER));
    assertSame(1, player.getResourceManager().getResource(ResourceType.WOOL));
  }

  @DisplayName("It must process a valid build structure request")
  @Tag(value = "CatanBoardEngine")
  @Test
  public void itMustProcessAValidBuildStructureRequest()
      throws InvalidBoardDimensionsException, InvalidBoardElementException, NonNullInputException,
          NonVoidCollectionException {

    Map<ResourceType, Integer> playerResources = new TreeMap<ResourceType, Integer>();

    playerResources.put(ResourceType.BRICK, 1);
    playerResources.put(ResourceType.GRAIN, 1);
    playerResources.put(ResourceType.LUMBER, 1);
    playerResources.put(ResourceType.WOOL, 1);

    IPlayer player = new Player(0, new ResourceManager(playerResources));
    IPlayer[] players = {player};
    ICatanEditableBoard board = buildStandardBoard(player);
    Consumer<IRequest> errorHandler =
        (request) -> {
          fail();
        };

    CatanGameEngine engine =
        new CatanGameEngine(
            board, players, GameState.NORMAL, 0, true, errorHandler, new CatanRandomGenerator());

    int requestX = 2;
    int requestY = 2;

    IRequest[] requests = {
      new BuildStructureRequest(player, StructureType.SETTLEMENT, requestX, requestY)
    };

    engine.processRequests(requests);

    IBoardElement elementBuilt = board.get(requestX, requestY);

    assertSame(BoardElementType.STRUCTURE, elementBuilt.getElementType());
    assertSame(StructureType.SETTLEMENT, ((IBoardStructure) elementBuilt).getType());
    assertSame(player.getId(), ((IOwnedElement) elementBuilt).getOwner().getId());
    assertSame(0, player.getResourceManager().getResource(ResourceType.BRICK));
    assertSame(0, player.getResourceManager().getResource(ResourceType.GRAIN));
    assertSame(0, player.getResourceManager().getResource(ResourceType.LUMBER));
    assertSame(0, player.getResourceManager().getResource(ResourceType.WOOL));
  }

  @DisplayName("It must process a valid ent turn request")
  @Tag(value = "CatanBoardEngine")
  @Test
  public void itMustProcessAValidEndTurnRequest()
      throws NonNullInputException, NonVoidCollectionException, InvalidBoardDimensionsException,
          InvalidBoardElementException {

    IPlayer player = new Player(0, new ResourceManager());
    IPlayer[] players = {player};
    ICatanEditableBoard board = buildStandardBoard(player);
    Consumer<IRequest> errorHandler =
        (request) -> {
          fail();
        };

    CatanGameEngine engine =
        new CatanGameEngine(
            board, players, GameState.NORMAL, 0, true, errorHandler, new CatanRandomGenerator());

    IRequest[] requests = {new EndTurnRequest(player)};

    engine.processRequests(requests);

    assertSame(false, engine.isTurnStarted());
  }

  @DisplayName("It must process a valid upgrade structure request")
  @Tag(value = "CatanBoardEngine")
  @Test
  public void itMustProcessAValidUpgradeStructureRequest()
      throws InvalidBoardDimensionsException, InvalidBoardElementException, NonNullInputException,
          NonVoidCollectionException {

    Map<ResourceType, Integer> playerResources = new TreeMap<ResourceType, Integer>();

    playerResources.put(ResourceType.BRICK, 1);
    playerResources.put(ResourceType.GRAIN, 3);
    playerResources.put(ResourceType.LUMBER, 1);
    playerResources.put(ResourceType.WOOL, 1);
    playerResources.put(ResourceType.ORE, 3);

    IPlayer player = new Player(0, new ResourceManager(playerResources));
    IPlayer[] players = {player};
    ICatanEditableBoard board = buildStandardBoard(player);
    Consumer<IRequest> errorHandler =
        (request) -> {
          fail();
        };

    CatanGameEngine engine =
        new CatanGameEngine(
            board, players, GameState.NORMAL, 0, true, errorHandler, new CatanRandomGenerator());

    int requestX = 2;
    int requestY = 2;

    IRequest[] requests = {
      new BuildStructureRequest(player, StructureType.SETTLEMENT, requestX, requestY),
      new UpgradeStructureRequest(player, StructureType.CITY, requestX, requestY)
    };

    engine.processRequests(requests);

    IBoardElement elementBuilt = board.get(requestX, requestY);

    assertSame(BoardElementType.STRUCTURE, elementBuilt.getElementType());
    assertSame(StructureType.CITY, ((IBoardStructure) elementBuilt).getType());
    assertSame(player.getId(), ((IOwnedElement) elementBuilt).getOwner().getId());
    assertSame(0, player.getResourceManager().getResource(ResourceType.BRICK));
    assertSame(0, player.getResourceManager().getResource(ResourceType.GRAIN));
    assertSame(0, player.getResourceManager().getResource(ResourceType.LUMBER));
    assertSame(0, player.getResourceManager().getResource(ResourceType.WOOL));
  }

  @DisplayName("It must process a valid start turn request")
  @Tag(value = "CatanBoardEngine")
  @Test
  public void itMustProcessAValidStartTurnRequest()
      throws NonNullInputException, NonVoidCollectionException, InvalidBoardDimensionsException,
          InvalidBoardElementException {

    IPlayer player = new Player(0, new ResourceManager());
    IPlayer[] players = {player};
    ICatanEditableBoard board = buildStandardBoard(player);

    Consumer<IRequest> errorHandler =
        (request) -> {
          fail();
        };

    CatanGameEngine engine =
        new CatanGameEngine(
            board,
            players,
            GameState.NORMAL,
            0,
            false,
            errorHandler,
            new ConstantNumberGenerator(6));

    IRequest[] requests = {new StartTurnRequest(player)};

    engine.processRequests(requests);

    assertSame(true, engine.isTurnStarted());
  }

  @DisplayName("It must produce resources at the start of a turn")
  @Tag(value = "CatanBoardEngine")
  @Test
  public void itMustProduceResourcesAtTheStartOfATurn()
      throws InvalidBoardDimensionsException, InvalidBoardElementException, NonNullInputException,
          NonVoidCollectionException {

    Map<ResourceType, Integer> playerResources = new TreeMap<ResourceType, Integer>();

    IPlayer player = new Player(0, new ResourceManager(playerResources));
    IPlayer[] players = {player};
    ICatanEditableBoard board = buildStandardBoard(player);

    int requestX = 2;
    int requestY = 2;

    board.build(
        new BoardStructure(player, new ResourceManager(), StructureType.SETTLEMENT),
        requestX,
        requestY);

    Consumer<IRequest> errorHandler =
        (request) -> {
          fail();
        };

    CatanGameEngine engine =
        new CatanGameEngine(
            board,
            players,
            GameState.NORMAL,
            0,
            false,
            errorHandler,
            new ConstantNumberGenerator(6));

    IRequest[] requests = {new StartTurnRequest(player)};

    engine.processRequests(requests);

    IResourceStorage expectedResources =
        new DefaultTerrainProductionProvider()
            .getResourceManager(
                new StructureTerrainTypesPair(StructureType.SETTLEMENT, TerrainType.MOUNTAINS));

    assertEquals(expectedResources, player.getResourceManager());
  }

  private IBoardTerrain buildMountainTerrain() {
    return new BoardTerrain(6, TerrainType.MOUNTAINS);
  }

  private IBoardStructure buildNoneStructure() {
    return new BoardStructure(null, new ResourceManager(), StructureType.NONE);
  }

  private IBoardTerrain buildNoneTerrain() {
    return new BoardTerrain(0, TerrainType.NONE);
  }

  private IBoardConnection buildPlayerConnection(IPlayer player) {
    return new BoardConnection(player, new ResourceManager(), ConnectionType.ROAD);
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
        buildNoneTerrain(),
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

    return new CatanEditableBoard(5, 5, elements, new DefaultTerrainProductionProvider());
  }

  private IBoardConnection buildVoidConnection() {
    return new BoardConnection(null, new ResourceManager(), ConnectionType.VOID);
  }
}
