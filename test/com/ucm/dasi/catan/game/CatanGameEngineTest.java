package com.ucm.dasi.catan.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

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
import com.ucm.dasi.catan.game.exception.InvalidLogException;
import com.ucm.dasi.catan.game.generator.CatanRandomGenerator;
import com.ucm.dasi.catan.game.generator.ConstantNumberGenerator;
import com.ucm.dasi.catan.game.log.IGameLog;
import com.ucm.dasi.catan.game.log.ILogEntry;
import com.ucm.dasi.catan.game.log.LinearGameLog;
import com.ucm.dasi.catan.game.log.LogEntry;
import com.ucm.dasi.catan.player.IPlayer;
import com.ucm.dasi.catan.player.Player;
import com.ucm.dasi.catan.request.BuildConnectionRequest;
import com.ucm.dasi.catan.request.BuildInitialStructureRequest;
import com.ucm.dasi.catan.request.BuildStructureRequest;
import com.ucm.dasi.catan.request.EndTurnRequest;
import com.ucm.dasi.catan.request.IRequest;
import com.ucm.dasi.catan.request.StartTurnRequest;
import com.ucm.dasi.catan.request.UpgradeStructureRequest;
import com.ucm.dasi.catan.resource.IResourceStorage;
import com.ucm.dasi.catan.resource.ResourceManager;
import com.ucm.dasi.catan.resource.ResourceStorage;
import com.ucm.dasi.catan.resource.ResourceType;
import com.ucm.dasi.catan.resource.provider.DefaultTerrainProductionProvider;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class CatanGameEngineTest {

  @DisplayName("it must end the game if the active player won")
  @Tag("CatanBoardEngine")
  @Test
  public void itMustEndTheGameIfTheActivePlayerWon()
      throws InvalidBoardDimensionsException, InvalidBoardElementException, NonNullInputException,
          NonVoidCollectionException, InvalidLogException {

    Map<ResourceType, Integer> playerResources = new TreeMap<ResourceType, Integer>();

    playerResources.put(ResourceType.BRICK, 1);
    playerResources.put(ResourceType.GRAIN, 1);
    playerResources.put(ResourceType.LUMBER, 1);
    playerResources.put(ResourceType.WOOL, 1);

    IPlayer player1 = new Player(0, new ResourceManager(playerResources));
    IPlayer[] players = {player1};

    ICatanEditableBoard board = buildStandardBoard(player1);

    Consumer<IRequest> errorHandler =
        (request) -> {
          fail();
        };

    Collection<ILogEntry> entries = new ArrayList<ILogEntry>();
    ArrayList<IRequest> entryRequests = new ArrayList<IRequest>();

    entryRequests.add(new StartTurnRequest(player1));
    entries.add(new LogEntry(6, entryRequests));

    CatanGameEngine engine =
        new CatanGameEngine(
            board,
            players,
            1,
            GameState.NORMAL,
            0,
            true,
            errorHandler,
            new LinearGameLog(entries),
            new CatanRandomGenerator());

    int requestX = 2;
    int requestY = 2;

    IRequest[] requests = {
      new BuildStructureRequest(player1, StructureType.SETTLEMENT, requestX, requestY),
      new EndTurnRequest(player1)
    };

    engine.processRequests(requests);

    assertSame(GameState.ENDED, engine.getState());
    assertSame(player1, engine.getActivePlayer());
  }

  @DisplayName("It must append a request to the log while processing a build connection request")
  @Tag(value = "CatanBoardEngine")
  @Test
  public void itMustLogABuildConnectionRequest()
      throws NonNullInputException, NonVoidCollectionException, InvalidLogException,
          InvalidBoardElementException, InvalidBoardDimensionsException {

    Map<ResourceType, Integer> playerResources = new TreeMap<ResourceType, Integer>();

    playerResources.put(ResourceType.BRICK, 1);
    playerResources.put(ResourceType.GRAIN, 1);
    playerResources.put(ResourceType.LUMBER, 1);
    playerResources.put(ResourceType.WOOL, 1);

    IPlayer player = new Player(0, new ResourceManager(playerResources));
    IPlayer[] players = {player};
    ICatanEditableBoard board = buildStandardBoard(player);

    int requestX = 3;
    int requestY = 2;

    Consumer<IRequest> errorHandler =
        (request) -> {
          fail();
        };

    Collection<ILogEntry> entries = new ArrayList<ILogEntry>();
    ArrayList<IRequest> entryRequests = new ArrayList<IRequest>();

    entryRequests.add(new StartTurnRequest(player));
    entries.add(Mockito.spy(new LogEntry(6, entryRequests)));

    IGameLog originalLog = new LinearGameLog(entries);
    IGameLog log = Mockito.spy(originalLog);

    int turn = 0;

    CatanGameEngine engine =
        new CatanGameEngine(
            board,
            players,
            10,
            GameState.NORMAL,
            turn,
            true,
            errorHandler,
            log,
            new ConstantNumberGenerator(6));

    IRequest request = new BuildConnectionRequest(player, ConnectionType.ROAD, requestX, requestY);
    IRequest[] requests = {request};

    engine.processRequests(requests);

    verify(log.get(turn)).add(request);
  }

  @DisplayName(
      "It must append a request to the log while processing a build initial structure request")
  @Tag(value = "CatanBoardEngine")
  @Test
  public void itMustLogABuildInitialStructureRequest()
      throws NonNullInputException, NonVoidCollectionException, InvalidLogException,
          InvalidBoardElementException, InvalidBoardDimensionsException {

    IPlayer player = new Player(0, new ResourceManager());
    IPlayer[] players = {player};
    ICatanEditableBoard board = buildStandardBoard(player);

    int requestX = 2;
    int requestY = 4;

    Consumer<IRequest> errorHandler =
        (request) -> {
          fail();
        };

    Collection<ILogEntry> entries = new ArrayList<ILogEntry>();
    ArrayList<IRequest> entryRequests = new ArrayList<IRequest>();

    entryRequests.add(new StartTurnRequest(player));
    entries.add(Mockito.spy(new LogEntry(6, entryRequests)));

    IGameLog originalLog = new LinearGameLog(entries);
    IGameLog log = Mockito.spy(originalLog);

    int turn = 0;

    CatanGameEngine engine =
        new CatanGameEngine(
            board,
            players,
            10,
            GameState.FOUNDATION,
            turn,
            true,
            errorHandler,
            log,
            new ConstantNumberGenerator(6));

    IRequest request =
        new BuildInitialStructureRequest(player, StructureType.SETTLEMENT, requestX, requestY);
    IRequest[] requests = {request};

    engine.processRequests(requests);

    verify(log.get(turn)).add(request);
  }

  @DisplayName("It must append a request to the log while processing a build structure request")
  @Tag(value = "CatanBoardEngine")
  @Test
  public void itMustLogABuildStructureRequest()
      throws NonNullInputException, NonVoidCollectionException, InvalidLogException,
          InvalidBoardElementException, InvalidBoardDimensionsException {

    Map<ResourceType, Integer> playerResources = new TreeMap<ResourceType, Integer>();

    playerResources.put(ResourceType.BRICK, 1);
    playerResources.put(ResourceType.GRAIN, 1);
    playerResources.put(ResourceType.LUMBER, 1);
    playerResources.put(ResourceType.WOOL, 1);

    IPlayer player = new Player(0, new ResourceManager(playerResources));
    IPlayer[] players = {player};
    ICatanEditableBoard board = buildStandardBoard(player);

    int requestX = 2;
    int requestY = 2;

    Consumer<IRequest> errorHandler =
        (request) -> {
          fail();
        };

    Collection<ILogEntry> entries = new ArrayList<ILogEntry>();
    ArrayList<IRequest> entryRequests = new ArrayList<IRequest>();

    entryRequests.add(new StartTurnRequest(player));
    entries.add(Mockito.spy(new LogEntry(6, entryRequests)));

    IGameLog originalLog = new LinearGameLog(entries);
    IGameLog log = Mockito.spy(originalLog);

    int turn = 0;

    CatanGameEngine engine =
        new CatanGameEngine(
            board,
            players,
            10,
            GameState.NORMAL,
            turn,
            true,
            errorHandler,
            log,
            new ConstantNumberGenerator(6));

    IRequest request =
        new BuildStructureRequest(player, StructureType.SETTLEMENT, requestX, requestY);
    IRequest[] requests = {request};

    engine.processRequests(requests);

    verify(log.get(turn)).add(request);
  }

  @DisplayName("It must log a new entry while processing a start turn request")
  @Tag(value = "CatanBoardEngine")
  @Test
  public void itMustLogAStartTurnRequest()
      throws NonNullInputException, NonVoidCollectionException, InvalidLogException,
          InvalidBoardElementException, InvalidBoardDimensionsException {
    IPlayer player = new Player(0, new ResourceManager());
    IPlayer[] players = {player};
    ICatanEditableBoard board = buildStandardBoard(player);

    Consumer<IRequest> errorHandler =
        (request) -> {
          fail();
        };

    IGameLog log = Mockito.spy(new LinearGameLog());

    int turn = 0;

    CatanGameEngine engine =
        new CatanGameEngine(
            board,
            players,
            10,
            GameState.NORMAL,
            turn,
            false,
            errorHandler,
            log,
            new ConstantNumberGenerator(6));

    IRequest request = new StartTurnRequest(player);
    IRequest[] requests = {request};

    engine.processRequests(requests);

    verify(log).set(eq(turn), any());
  }

  @DisplayName("It must append a request to the log while processing an end turn request")
  @Tag(value = "CatanBoardEngine")
  @Test
  public void itMustLogAnEndTurnRequest()
      throws NonNullInputException, NonVoidCollectionException, InvalidLogException,
          InvalidBoardElementException, InvalidBoardDimensionsException {
    IPlayer player = new Player(0, new ResourceManager());
    IPlayer[] players = {player};
    ICatanEditableBoard board = buildStandardBoard(player);

    Consumer<IRequest> errorHandler =
        (request) -> {
          fail();
        };

    Collection<ILogEntry> entries = new ArrayList<ILogEntry>();
    ArrayList<IRequest> entryRequests = new ArrayList<IRequest>();

    entryRequests.add(new StartTurnRequest(player));
    entries.add(Mockito.spy(new LogEntry(6, entryRequests)));

    IGameLog originalLog = new LinearGameLog(entries);
    IGameLog log = Mockito.spy(originalLog);

    int turn = 0;

    CatanGameEngine engine =
        new CatanGameEngine(
            board,
            players,
            10,
            GameState.NORMAL,
            turn,
            true,
            errorHandler,
            log,
            new ConstantNumberGenerator(6));

    IRequest request = new EndTurnRequest(player);
    IRequest[] requests = {request};

    engine.processRequests(requests);

    verify(log.get(turn)).add(request);
  }

  @DisplayName("It must append a request to the log while processing an update structure request")
  @Tag(value = "CatanBoardEngine")
  @Test
  public void itMustLogAnUpdateStructureRequest()
      throws NonNullInputException, NonVoidCollectionException, InvalidLogException,
          InvalidBoardElementException, InvalidBoardDimensionsException {

    Map<ResourceType, Integer> playerResources = new TreeMap<ResourceType, Integer>();

    playerResources.put(ResourceType.GRAIN, 2);
    playerResources.put(ResourceType.ORE, 3);

    IPlayer player = new Player(0, new ResourceManager(playerResources));
    IPlayer[] players = {player};
    ICatanEditableBoard board = buildStandardBoard(player);

    int requestX = 2;
    int requestY = 2;

    board.build(
        new BoardStructure(player, new ResourceStorage(), StructureType.SETTLEMENT),
        requestX,
        requestY);

    Consumer<IRequest> errorHandler =
        (request) -> {
          fail();
        };

    Collection<ILogEntry> entries = new ArrayList<ILogEntry>();
    ArrayList<IRequest> entryRequests = new ArrayList<IRequest>();

    entryRequests.add(new StartTurnRequest(player));
    entries.add(Mockito.spy(new LogEntry(6, entryRequests)));

    IGameLog originalLog = new LinearGameLog(entries);
    IGameLog log = Mockito.spy(originalLog);

    int turn = 0;

    CatanGameEngine engine =
        new CatanGameEngine(
            board,
            players,
            10,
            GameState.NORMAL,
            turn,
            true,
            errorHandler,
            log,
            new ConstantNumberGenerator(6));

    IRequest request = new UpgradeStructureRequest(player, StructureType.CITY, requestX, requestY);
    IRequest[] requests = {request};

    engine.processRequests(requests);

    verify(log.get(turn)).add(request);
  }

  @DisplayName(
      "It must not process a valid build connection request if the player has not enought resources")
  @Tag(value = "CatanBoardEngine")
  @Test
  public void itMustNotProcessAValidBuildConnectionRequestI()
      throws InvalidBoardDimensionsException, InvalidBoardElementException, NonNullInputException,
          NonVoidCollectionException, InvalidLogException {

    IPlayer player1 = new Player(0, new ResourceManager());
    IPlayer[] players = {player1};
    ICatanEditableBoard board = buildStandardBoard(player1);

    AtomicBoolean requestFailed = new AtomicBoolean(false);

    Consumer<IRequest> errorHandler =
        (request) -> {
          requestFailed.set(true);
        };

    Collection<ILogEntry> entries = new ArrayList<ILogEntry>();
    ArrayList<IRequest> entryRequests = new ArrayList<IRequest>();

    entryRequests.add(new StartTurnRequest(player1));
    entries.add(new LogEntry(6, entryRequests));

    CatanGameEngine engine =
        new CatanGameEngine(
            board,
            players,
            10,
            GameState.NORMAL,
            0,
            true,
            errorHandler,
            new LinearGameLog(entries),
            new CatanRandomGenerator());

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
          NonVoidCollectionException, InvalidLogException {

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

    Collection<ILogEntry> entries = new ArrayList<ILogEntry>();
    ArrayList<IRequest> entryRequests = new ArrayList<IRequest>();

    entryRequests.add(new StartTurnRequest(player1));
    entries.add(new LogEntry(6, entryRequests));

    CatanGameEngine engine =
        new CatanGameEngine(
            board,
            players,
            10,
            GameState.NORMAL,
            0,
            true,
            errorHandler,
            new LinearGameLog(entries),
            new CatanRandomGenerator());

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
          NonVoidCollectionException, InvalidLogException {

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
            10,
            GameState.NORMAL,
            0,
            false,
            errorHandler,
            new LinearGameLog(),
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
      "It must not process a valid build connection request if the game state is not normal")
  @Tag(value = "CatanBoardEngine")
  @Test
  public void itMustNotProcessAValidBuildConnectionRequestIV()
      throws InvalidBoardDimensionsException, InvalidBoardElementException, NonNullInputException,
          NonVoidCollectionException, InvalidLogException {

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

    Collection<ILogEntry> entries = new ArrayList<ILogEntry>();
    ArrayList<IRequest> entryRequests = new ArrayList<IRequest>();

    entryRequests.add(new StartTurnRequest(player));
    entries.add(new LogEntry(6, entryRequests));

    CatanGameEngine engine =
        new CatanGameEngine(
            board,
            players,
            10,
            GameState.FOUNDATION,
            0,
            true,
            errorHandler,
            new LinearGameLog(entries),
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
          NonVoidCollectionException, InvalidLogException {

    Map<ResourceType, Integer> playerResources = new TreeMap<ResourceType, Integer>();

    IPlayer player1 = new Player(0, new ResourceManager(playerResources));
    IPlayer[] players = {player1};
    ICatanEditableBoard board = buildStandardBoard(player1);

    AtomicBoolean requestFailed = new AtomicBoolean(false);

    Consumer<IRequest> errorHandler =
        (request) -> {
          requestFailed.set(true);
        };

    Collection<ILogEntry> entries = new ArrayList<ILogEntry>();
    ArrayList<IRequest> entryRequests = new ArrayList<IRequest>();

    entryRequests.add(new StartTurnRequest(player1));
    entries.add(new LogEntry(6, entryRequests));

    CatanGameEngine engine =
        new CatanGameEngine(
            board,
            players,
            10,
            GameState.NORMAL,
            0,
            true,
            errorHandler,
            new LinearGameLog(entries),
            new CatanRandomGenerator());

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
          NonVoidCollectionException, InvalidLogException {

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

    Collection<ILogEntry> entries = new ArrayList<ILogEntry>();
    ArrayList<IRequest> entryRequests = new ArrayList<IRequest>();

    entryRequests.add(new StartTurnRequest(player1));
    entries.add(new LogEntry(6, entryRequests));

    CatanGameEngine engine =
        new CatanGameEngine(
            board,
            players,
            10,
            GameState.NORMAL,
            0,
            true,
            errorHandler,
            new LinearGameLog(entries),
            new CatanRandomGenerator());

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
          NonVoidCollectionException, InvalidLogException {

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
            10,
            GameState.NORMAL,
            0,
            false,
            errorHandler,
            new LinearGameLog(),
            new CatanRandomGenerator());

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
          NonVoidCollectionException, InvalidLogException {

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

    Collection<ILogEntry> entries = new ArrayList<ILogEntry>();
    ArrayList<IRequest> entryRequests = new ArrayList<IRequest>();

    entryRequests.add(new StartTurnRequest(player1));
    entries.add(new LogEntry(6, entryRequests));

    CatanGameEngine engine =
        new CatanGameEngine(
            board,
            players,
            10,
            GameState.FOUNDATION,
            0,
            true,
            errorHandler,
            new LinearGameLog(entries),
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
          InvalidBoardElementException, InvalidLogException {

    IPlayer player = new Player(0, new ResourceManager());
    IPlayer[] players = {player};
    ICatanEditableBoard board = buildStandardBoard(player);
    AtomicBoolean requestFailed = new AtomicBoolean(false);

    Consumer<IRequest> errorHandler =
        (request) -> {
          requestFailed.set(true);
        };

    Collection<ILogEntry> entries = new ArrayList<ILogEntry>();
    ArrayList<IRequest> entryRequests = new ArrayList<IRequest>();

    entryRequests.add(new StartTurnRequest(player));
    entries.add(new LogEntry(6, entryRequests));

    CatanGameEngine engine =
        new CatanGameEngine(
            board,
            players,
            10,
            GameState.ENDED,
            0,
            true,
            errorHandler,
            new LinearGameLog(entries),
            new CatanRandomGenerator());

    IRequest[] requests = {new EndTurnRequest(player)};

    engine.processRequests(requests);

    assertSame(true, requestFailed.get());
  }

  @DisplayName("It must not process a valid start turn request if the game is ended")
  @Tag(value = "CatanBoardEngine")
  @Test
  public void itMustNotProcessAValidStartTurnRequestIfTheGameIsEnded()
      throws NonNullInputException, NonVoidCollectionException, InvalidBoardDimensionsException,
          InvalidBoardElementException, InvalidLogException {

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
            board,
            players,
            10,
            GameState.ENDED,
            0,
            false,
            errorHandler,
            new LinearGameLog(),
            new CatanRandomGenerator());

    IRequest[] requests = {new StartTurnRequest(player)};

    engine.processRequests(requests);

    assertSame(true, requestFailed.get());
  }

  @DisplayName("It must not process two build initial structure requests at the same turn")
  @Tag(value = "CatanBoardEngine")
  @Test
  public void itMustNotProcessTwoBuildInitialStructureRequestsAtTheSameTurn()
      throws InvalidBoardDimensionsException, InvalidBoardElementException, NonNullInputException,
          NonVoidCollectionException, InvalidLogException {
    IPlayer player = new Player(0, new ResourceManager());
    IPlayer[] players = {player};
    ICatanEditableBoard board = buildStandardBoard(player);

    AtomicBoolean requestFailed = new AtomicBoolean(false);

    Consumer<IRequest> errorHandler =
        (request) -> {
          requestFailed.set(true);
        };

    Collection<ILogEntry> entries = new ArrayList<ILogEntry>();
    ArrayList<IRequest> entryRequests = new ArrayList<IRequest>();

    entryRequests.add(new StartTurnRequest(player));
    entries.add(new LogEntry(6, entryRequests));

    CatanGameEngine engine =
        new CatanGameEngine(
            board,
            players,
            10,
            GameState.FOUNDATION,
            0,
            true,
            errorHandler,
            new LinearGameLog(entries),
            new CatanRandomGenerator());

    int requestX = 2;
    int requestY = 4;

    IRequest[] requests = {
      new BuildInitialStructureRequest(player, StructureType.SETTLEMENT, requestX, requestY),
      new BuildInitialStructureRequest(player, StructureType.SETTLEMENT, requestX, requestY),
    };

    engine.processRequests(requests);

    assertSame(true, requestFailed.get());
  }

  @DisplayName("It must process a valid build connection request")
  @Tag(value = "CatanBoardEngine")
  @Test
  public void itMustProcessAValidBuildConnectionRequest()
      throws InvalidBoardDimensionsException, InvalidBoardElementException, NonNullInputException,
          NonVoidCollectionException, InvalidLogException {

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

    Collection<ILogEntry> entries = new ArrayList<ILogEntry>();
    ArrayList<IRequest> entryRequests = new ArrayList<IRequest>();

    entryRequests.add(new StartTurnRequest(player));
    entries.add(new LogEntry(6, entryRequests));

    CatanGameEngine engine =
        new CatanGameEngine(
            board,
            players,
            10,
            GameState.NORMAL,
            0,
            true,
            errorHandler,
            new LinearGameLog(entries),
            new CatanRandomGenerator());

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

  @DisplayName("It must process a valid build inital structure request")
  @Tag(value = "CatanBoardEngine")
  @Test
  public void itMustProcessAValidBuildInitialStructureRequest()
      throws InvalidBoardDimensionsException, InvalidBoardElementException, NonNullInputException,
          NonVoidCollectionException, InvalidLogException {

    IPlayer player = new Player(0, new ResourceManager());
    IPlayer[] players = {player};
    ICatanEditableBoard board = buildStandardBoard(player);
    Consumer<IRequest> errorHandler =
        (request) -> {
          fail();
        };

    Collection<ILogEntry> entries = new ArrayList<ILogEntry>();
    ArrayList<IRequest> entryRequests = new ArrayList<IRequest>();

    entryRequests.add(new StartTurnRequest(player));
    entries.add(new LogEntry(6, entryRequests));

    CatanGameEngine engine =
        new CatanGameEngine(
            board,
            players,
            10,
            GameState.FOUNDATION,
            0,
            true,
            errorHandler,
            new LinearGameLog(entries),
            new CatanRandomGenerator());

    int requestX = 2;
    int requestY = 4;

    IRequest[] requests = {
      new BuildInitialStructureRequest(player, StructureType.SETTLEMENT, requestX, requestY)
    };

    engine.processRequests(requests);

    IBoardElement elementBuilt = board.get(requestX, requestY);

    assertSame(BoardElementType.STRUCTURE, elementBuilt.getElementType());
    assertSame(StructureType.SETTLEMENT, ((IBoardStructure) elementBuilt).getType());
    assertSame(player.getId(), ((IOwnedElement) elementBuilt).getOwner().getId());
  }

  @DisplayName("It must process a valid build structure request")
  @Tag(value = "CatanBoardEngine")
  @Test
  public void itMustProcessAValidBuildStructureRequest()
      throws InvalidBoardDimensionsException, InvalidBoardElementException, NonNullInputException,
          NonVoidCollectionException, InvalidLogException {

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

    Collection<ILogEntry> entries = new ArrayList<ILogEntry>();
    ArrayList<IRequest> entryRequests = new ArrayList<IRequest>();

    entryRequests.add(new StartTurnRequest(player));
    entries.add(new LogEntry(6, entryRequests));

    CatanGameEngine engine =
        new CatanGameEngine(
            board,
            players,
            10,
            GameState.NORMAL,
            0,
            true,
            errorHandler,
            new LinearGameLog(entries),
            new CatanRandomGenerator());

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

  @DisplayName("It must process a valid end turn request")
  @Tag(value = "CatanBoardEngine")
  @Test
  public void itMustProcessAValidEndTurnRequest()
      throws NonNullInputException, NonVoidCollectionException, InvalidBoardDimensionsException,
          InvalidBoardElementException, InvalidLogException {

    IPlayer player = new Player(0, new ResourceManager());
    IPlayer[] players = {player};
    ICatanEditableBoard board = buildStandardBoard(player);
    Consumer<IRequest> errorHandler =
        (request) -> {
          fail();
        };

    Collection<ILogEntry> entries = new ArrayList<ILogEntry>();
    ArrayList<IRequest> entryRequests = new ArrayList<IRequest>();

    entryRequests.add(new StartTurnRequest(player));
    entries.add(new LogEntry(6, entryRequests));

    CatanGameEngine engine =
        new CatanGameEngine(
            board,
            players,
            10,
            GameState.NORMAL,
            0,
            true,
            errorHandler,
            new LinearGameLog(entries),
            new CatanRandomGenerator());

    IRequest[] requests = {new EndTurnRequest(player)};

    engine.processRequests(requests);

    assertSame(false, engine.isTurnStarted());
  }

  @DisplayName("It must process a valid upgrade structure request")
  @Tag(value = "CatanBoardEngine")
  @Test
  public void itMustProcessAValidUpgradeStructureRequest()
      throws InvalidBoardDimensionsException, InvalidBoardElementException, NonNullInputException,
          NonVoidCollectionException, InvalidLogException {

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

    Collection<ILogEntry> entries = new ArrayList<ILogEntry>();
    ArrayList<IRequest> entryRequests = new ArrayList<IRequest>();

    entryRequests.add(new StartTurnRequest(player));
    entries.add(new LogEntry(6, entryRequests));

    CatanGameEngine engine =
        new CatanGameEngine(
            board,
            players,
            10,
            GameState.NORMAL,
            0,
            true,
            errorHandler,
            new LinearGameLog(entries),
            new CatanRandomGenerator());

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
          InvalidBoardElementException, InvalidLogException {

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
            10,
            GameState.NORMAL,
            0,
            false,
            errorHandler,
            new LinearGameLog(),
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
          NonVoidCollectionException, InvalidLogException {

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
            10,
            GameState.NORMAL,
            0,
            false,
            errorHandler,
            new LinearGameLog(),
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
