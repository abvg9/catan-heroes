package io.github.notaphplover.catan.core.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

import io.github.notaphplover.catan.core.board.BoardElementType;
import io.github.notaphplover.catan.core.board.CatanBoard;
import io.github.notaphplover.catan.core.board.ICatanBoard;
import io.github.notaphplover.catan.core.board.connection.BoardConnection;
import io.github.notaphplover.catan.core.board.connection.ConnectionType;
import io.github.notaphplover.catan.core.board.connection.IBoardConnection;
import io.github.notaphplover.catan.core.board.element.IBoardElement;
import io.github.notaphplover.catan.core.board.element.IOwnedElement;
import io.github.notaphplover.catan.core.board.exception.InvalidBoardDimensionsException;
import io.github.notaphplover.catan.core.board.exception.InvalidBoardElementException;
import io.github.notaphplover.catan.core.board.group.StructureTerrainTypesPair;
import io.github.notaphplover.catan.core.board.structure.BoardStructure;
import io.github.notaphplover.catan.core.board.structure.IBoardStructure;
import io.github.notaphplover.catan.core.board.structure.StructureType;
import io.github.notaphplover.catan.core.board.terrain.BoardTerrain;
import io.github.notaphplover.catan.core.board.terrain.IBoardTerrain;
import io.github.notaphplover.catan.core.board.terrain.TerrainType;
import io.github.notaphplover.catan.core.exception.NonNullInputException;
import io.github.notaphplover.catan.core.exception.NonVoidCollectionException;
import io.github.notaphplover.catan.core.game.exception.InvalidLogException;
import io.github.notaphplover.catan.core.game.generator.CatanRandomGenerator;
import io.github.notaphplover.catan.core.game.generator.ConstantNumberGenerator;
import io.github.notaphplover.catan.core.game.log.IGameLog;
import io.github.notaphplover.catan.core.game.log.ILogEntry;
import io.github.notaphplover.catan.core.game.log.LinearGameLog;
import io.github.notaphplover.catan.core.game.log.LogEntry;
import io.github.notaphplover.catan.core.game.trade.ITrade;
import io.github.notaphplover.catan.core.game.trade.ITradeAgreement;
import io.github.notaphplover.catan.core.game.trade.Trade;
import io.github.notaphplover.catan.core.game.trade.TradeAgreement;
import io.github.notaphplover.catan.core.game.trade.TradeConfirmation;
import io.github.notaphplover.catan.core.game.trade.TradeDiscard;
import io.github.notaphplover.catan.core.player.IPlayer;
import io.github.notaphplover.catan.core.player.Player;
import io.github.notaphplover.catan.core.request.BuildConnectionRequest;
import io.github.notaphplover.catan.core.request.BuildInitialConnectionRequest;
import io.github.notaphplover.catan.core.request.BuildInitialStructureRequest;
import io.github.notaphplover.catan.core.request.BuildStructureRequest;
import io.github.notaphplover.catan.core.request.EndTurnRequest;
import io.github.notaphplover.catan.core.request.IRequest;
import io.github.notaphplover.catan.core.request.StartTurnRequest;
import io.github.notaphplover.catan.core.request.UpgradeStructureRequest;
import io.github.notaphplover.catan.core.request.trade.TradeAgreementRequest;
import io.github.notaphplover.catan.core.request.trade.TradeConfirmationRequest;
import io.github.notaphplover.catan.core.request.trade.TradeDiscardRequest;
import io.github.notaphplover.catan.core.request.trade.TradeRequest;
import io.github.notaphplover.catan.core.resource.IResourceStorage;
import io.github.notaphplover.catan.core.resource.ResourceManager;
import io.github.notaphplover.catan.core.resource.ResourceStorage;
import io.github.notaphplover.catan.core.resource.ResourceType;
import io.github.notaphplover.catan.core.resource.provider.DefaultTerrainProductionProvider;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class CatanGameEngineTest {

  @DisplayName("it must end the foundation phase")
  @Tag("CatanBoardEngine")
  @Test
  public void itMustEndTheFoundationPhase()
      throws InvalidBoardDimensionsException, InvalidBoardElementException, NonNullInputException,
          NonVoidCollectionException, InvalidLogException {

    IPlayer player1 = new Player(0, new ResourceManager());
    IPlayer[] players = {player1};

    ICatanBoard board = buildStandardBoard(player1);

    Consumer<IRequest> errorHandler =
        (request) -> {
          fail();
        };

    Collection<ILogEntry> entries = new ArrayList<ILogEntry>();

    ArrayList<IRequest> entryFirstTurnsRequests = new ArrayList<IRequest>();
    entryFirstTurnsRequests.add(new StartTurnRequest(player1));
    entryFirstTurnsRequests.add(new EndTurnRequest(player1));

    entries.add(new LogEntry(6, entryFirstTurnsRequests));
    entries.add(new LogEntry(6, entryFirstTurnsRequests));
    entries.add(new LogEntry(6, entryFirstTurnsRequests));

    ArrayList<IRequest> entryLastTurnRequests = new ArrayList<IRequest>();
    entryLastTurnRequests.add(new StartTurnRequest(player1));

    entries.add(new LogEntry(6, entryLastTurnRequests));

    CatanGameEngine engine =
        new CatanGameEngine(
            board,
            players,
            10,
            GameState.NORMAL,
            3,
            true,
            errorHandler,
            new LinearGameLog(entries),
            new CatanRandomGenerator());

    engine.processRequest(new EndTurnRequest(player1));

    assertSame(GameState.NORMAL, engine.getState());
  }

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

    ICatanBoard board = buildStandardBoard(player1);

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

    engine.processRequest(
        new BuildStructureRequest(player1, StructureType.SETTLEMENT, requestX, requestY));
    engine.processRequest(new EndTurnRequest(player1));

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
    ICatanBoard board = buildStandardBoard(player);

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

    engine.processRequest(request);

    verify(log.get(turn)).add(request);
  }

  @DisplayName(
      "It must append a request to the log while processing a build initial connection request")
  @Tag(value = "CatanBoardEngine")
  @Test
  public void itMustLogABuildInitialConnectionRequest()
      throws NonNullInputException, NonVoidCollectionException, InvalidLogException,
          InvalidBoardElementException, InvalidBoardDimensionsException {

    IPlayer player = new Player(0, new ResourceManager());
    IPlayer[] players = {player};
    ICatanBoard board = buildStandardBoard(player);

    int requestX = 3;
    int requestY = 4;

    Consumer<IRequest> errorHandler =
        (request) -> {
          fail();
        };

    Collection<ILogEntry> entries = new ArrayList<ILogEntry>();

    ArrayList<IRequest> turn1Requests = new ArrayList<IRequest>();
    turn1Requests.add(new StartTurnRequest(player));
    turn1Requests.add(new EndTurnRequest(player));

    ArrayList<IRequest> turn2Requests = new ArrayList<IRequest>();
    turn1Requests.add(new StartTurnRequest(player));

    entries.add(new LogEntry(6, turn1Requests));

    entries.add(Mockito.spy(new LogEntry(6, turn2Requests)));

    IGameLog originalLog = new LinearGameLog(entries);
    IGameLog log = Mockito.spy(originalLog);

    int turn = 1;

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
        new BuildInitialConnectionRequest(player, ConnectionType.ROAD, requestX, requestY);

    engine.processRequest(request);

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
    ICatanBoard board = buildStandardBoard(player);

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

    engine.processRequest(request);

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
    ICatanBoard board = buildStandardBoard(player);

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

    engine.processRequest(request);

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
    ICatanBoard board = buildStandardBoard(player);

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

    engine.processRequest(request);

    verify(log).set(eq(turn), any());
  }

  @DisplayName("it must log a valid trade agreement request")
  @Tag("CatanBoardEngine")
  @Test
  public void itMustLogATradeAgreementRequest()
      throws InvalidBoardDimensionsException, InvalidBoardElementException, NonNullInputException,
          NonVoidCollectionException, InvalidLogException {

    Map<ResourceType, Integer> playerResources = new TreeMap<ResourceType, Integer>();

    playerResources.put(ResourceType.BRICK, 1);
    playerResources.put(ResourceType.GRAIN, 1);
    playerResources.put(ResourceType.LUMBER, 1);
    playerResources.put(ResourceType.ORE, 1);
    playerResources.put(ResourceType.WOOL, 1);

    IPlayer player1 = new Player(0, new ResourceManager(playerResources));
    IPlayer player2 = new Player(1, new ResourceManager(playerResources));
    IPlayer[] players = {player1, player2};
    ICatanBoard board = buildStandardBoard(player1);

    Consumer<IRequest> errorHandler =
        (request) -> {
          fail();
        };

    Collection<ILogEntry> entries = new ArrayList<ILogEntry>();
    ArrayList<IRequest> entryRequests = new ArrayList<IRequest>();

    entryRequests.add(new StartTurnRequest(player1));
    entries.add(Mockito.spy(new LogEntry(6, entryRequests)));

    IGameLog log = new LinearGameLog(entries);
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
            new LinearGameLog(entries),
            new CatanRandomGenerator());

    Map<ResourceType, Integer> requestedResourcesMap = new TreeMap<ResourceType, Integer>();
    requestedResourcesMap.put(ResourceType.ORE, 1);

    IResourceStorage requestedResources = new ResourceStorage(requestedResourcesMap);

    Map<ResourceType, Integer> resourcesMap = new TreeMap<ResourceType, Integer>();
    resourcesMap.put(ResourceType.GRAIN, 1);

    Collection<IResourceStorage> acceptableExchanges = new ArrayList<IResourceStorage>();
    acceptableExchanges.add(new ResourceStorage(resourcesMap));

    ITrade trade = new Trade(UUID.randomUUID(), acceptableExchanges, requestedResources);

    engine.processRequest(new TradeRequest(player1, trade));

    ITradeAgreement tradeAgreement =
        new TradeAgreement(UUID.randomUUID(), acceptableExchanges.iterator().next(), trade);

    IRequest request = new TradeAgreementRequest(player2, tradeAgreement);

    engine.processRequest(request);

    verify(log.get(turn)).add(request);
  }

  @DisplayName("it must log a valid trade confirmation request")
  @Tag("CatanBoardEngine")
  @Test
  public void itMustLogATradeConfirmationRequest()
      throws InvalidBoardDimensionsException, InvalidBoardElementException, NonNullInputException,
          NonVoidCollectionException, InvalidLogException {

    Map<ResourceType, Integer> playerResources = new TreeMap<ResourceType, Integer>();

    playerResources.put(ResourceType.BRICK, 1);
    playerResources.put(ResourceType.GRAIN, 1);
    playerResources.put(ResourceType.LUMBER, 1);
    playerResources.put(ResourceType.ORE, 1);
    playerResources.put(ResourceType.WOOL, 1);

    IPlayer player1 = new Player(0, new ResourceManager(playerResources));
    IPlayer player2 = new Player(1, new ResourceManager(playerResources));
    IPlayer[] players = {player1, player2};
    ICatanBoard board = buildStandardBoard(player1);

    Consumer<IRequest> errorHandler =
        (request) -> {
          fail();
        };

    Collection<ILogEntry> entries = new ArrayList<ILogEntry>();
    ArrayList<IRequest> entryRequests = new ArrayList<IRequest>();

    entryRequests.add(new StartTurnRequest(player1));
    entries.add(Mockito.spy(new LogEntry(6, entryRequests)));

    IGameLog log = new LinearGameLog(entries);
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
            new LinearGameLog(entries),
            new CatanRandomGenerator());

    Map<ResourceType, Integer> requestedResourcesMap = new TreeMap<ResourceType, Integer>();
    requestedResourcesMap.put(ResourceType.ORE, 1);

    IResourceStorage requestedResources = new ResourceStorage(requestedResourcesMap);

    Map<ResourceType, Integer> resourcesMap = new TreeMap<ResourceType, Integer>();
    resourcesMap.put(ResourceType.GRAIN, 1);

    Collection<IResourceStorage> acceptableExchanges = new ArrayList<IResourceStorage>();
    acceptableExchanges.add(new ResourceStorage(resourcesMap));

    ITrade trade = new Trade(UUID.randomUUID(), acceptableExchanges, requestedResources);

    engine.processRequest(new TradeRequest(player1, trade));

    ITradeAgreement tradeAgreement =
        new TradeAgreement(UUID.randomUUID(), acceptableExchanges.iterator().next(), trade);

    engine.processRequest(new TradeAgreementRequest(player2, tradeAgreement));

    IRequest request =
        new TradeConfirmationRequest(
            player1, new TradeConfirmation(UUID.randomUUID(), tradeAgreement));

    engine.processRequest(request);

    verify(log.get(turn)).add(request);
  }

  @DisplayName("it must log a valid trade discard request")
  @Tag("CatanBoardEngine")
  @Test
  public void itMustLogATradeDiscardRequest()
      throws InvalidBoardDimensionsException, InvalidBoardElementException, NonNullInputException,
          NonVoidCollectionException, InvalidLogException {

    Map<ResourceType, Integer> playerResources = new TreeMap<ResourceType, Integer>();

    playerResources.put(ResourceType.BRICK, 1);
    playerResources.put(ResourceType.GRAIN, 1);
    playerResources.put(ResourceType.LUMBER, 1);
    playerResources.put(ResourceType.ORE, 1);
    playerResources.put(ResourceType.WOOL, 1);

    IPlayer player1 = new Player(0, new ResourceManager(playerResources));
    IPlayer player2 = new Player(1, new ResourceManager(playerResources));
    IPlayer[] players = {player1, player2};
    ICatanBoard board = buildStandardBoard(player1);

    Consumer<IRequest> errorHandler =
        (request) -> {
          fail();
        };

    Collection<ILogEntry> entries = new ArrayList<ILogEntry>();
    ArrayList<IRequest> entryRequests = new ArrayList<IRequest>();

    entryRequests.add(new StartTurnRequest(player1));
    entries.add(Mockito.spy(new LogEntry(6, entryRequests)));

    IGameLog log = new LinearGameLog(entries);
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
            new LinearGameLog(entries),
            new CatanRandomGenerator());

    Map<ResourceType, Integer> requestedResourcesMap = new TreeMap<ResourceType, Integer>();
    requestedResourcesMap.put(ResourceType.ORE, 1);

    IResourceStorage requestedResources = new ResourceStorage(requestedResourcesMap);

    Map<ResourceType, Integer> resourcesMap = new TreeMap<ResourceType, Integer>();
    resourcesMap.put(ResourceType.GRAIN, 1);

    Collection<IResourceStorage> acceptableExchanges = new ArrayList<IResourceStorage>();
    acceptableExchanges.add(new ResourceStorage(resourcesMap));

    ITrade trade = new Trade(UUID.randomUUID(), acceptableExchanges, requestedResources);

    engine.processRequest(new TradeRequest(player1, trade));

    ITradeAgreement tradeAgreement =
        new TradeAgreement(UUID.randomUUID(), acceptableExchanges.iterator().next(), trade);

    engine.processRequest(new TradeAgreementRequest(player2, tradeAgreement));

    IRequest request = new TradeDiscardRequest(player1, new TradeDiscard(UUID.randomUUID(), trade));

    engine.processRequest(request);

    verify(log.get(turn)).add(request);
  }

  @DisplayName("it must log a valid trade request")
  @Tag("CatanBoardEngine")
  @Test
  public void itMustLogATradeRequest()
      throws InvalidBoardDimensionsException, InvalidBoardElementException, NonNullInputException,
          NonVoidCollectionException, InvalidLogException {

    Map<ResourceType, Integer> playerResources = new TreeMap<ResourceType, Integer>();

    playerResources.put(ResourceType.BRICK, 1);
    playerResources.put(ResourceType.GRAIN, 1);
    playerResources.put(ResourceType.LUMBER, 1);
    playerResources.put(ResourceType.WOOL, 1);

    IPlayer player = new Player(0, new ResourceManager(playerResources));
    IPlayer[] players = {player};
    ICatanBoard board = buildStandardBoard(player);

    Consumer<IRequest> errorHandler =
        (request) -> {
          fail();
        };

    Collection<ILogEntry> entries = new ArrayList<ILogEntry>();
    ArrayList<IRequest> entryRequests = new ArrayList<IRequest>();

    entryRequests.add(new StartTurnRequest(player));
    entries.add(Mockito.spy(new LogEntry(6, entryRequests)));

    IGameLog log = new LinearGameLog(entries);
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
            new CatanRandomGenerator());

    Map<ResourceType, Integer> requestedResourcesMap = new TreeMap<ResourceType, Integer>();
    requestedResourcesMap.put(ResourceType.ORE, 1);

    IResourceStorage requestedResources = new ResourceStorage(requestedResourcesMap);

    Map<ResourceType, Integer> resourcesMap = new TreeMap<ResourceType, Integer>();
    resourcesMap.put(ResourceType.GRAIN, 1);

    Collection<IResourceStorage> acceptableExchanges = new ArrayList<IResourceStorage>();
    acceptableExchanges.add(new ResourceStorage(resourcesMap));

    Trade trade = new Trade(UUID.randomUUID(), acceptableExchanges, requestedResources);

    IRequest request = new TradeRequest(player, trade);
    engine.processRequest(request);

    verify(log.get(turn)).add(request);
  }

  @DisplayName("It must append a request to the log while processing an end turn request")
  @Tag(value = "CatanBoardEngine")
  @Test
  public void itMustLogAnEndTurnRequest()
      throws NonNullInputException, NonVoidCollectionException, InvalidLogException,
          InvalidBoardElementException, InvalidBoardDimensionsException {
    IPlayer player = new Player(0, new ResourceManager());
    IPlayer[] players = {player};
    ICatanBoard board = buildStandardBoard(player);

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

    engine.processRequest(request);

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
    ICatanBoard board = buildStandardBoard(player);

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

    engine.processRequest(request);

    verify(log.get(turn)).add(request);
  }

  @DisplayName(
      "It must not generate resources processing a start turn request at the foundation phase")
  @Tag(value = "CatanGameEngine")
  @Test
  public void itMustNotGenerateResourcesProcessingAStartTurnRequestI()
      throws NonNullInputException, NonVoidCollectionException, InvalidLogException,
          InvalidBoardDimensionsException, InvalidBoardElementException {

    IPlayer player = new Player(0, new ResourceManager());
    IPlayer[] players = {player};
    ICatanBoard board = buildStandardBoard(player);

    board.build(new BoardStructure(player, new ResourceManager(), StructureType.SETTLEMENT), 2, 2);

    Consumer<IRequest> errorHandler =
        (request) -> {
          fail();
        };

    CatanGameEngine engine =
        new CatanGameEngine(
            board,
            players,
            10,
            GameState.FOUNDATION,
            0,
            false,
            errorHandler,
            new LinearGameLog(),
            new ConstantNumberGenerator(6));

    engine.processRequest(new StartTurnRequest(player));

    assertEquals(new ResourceStorage(), player.getResourceManager());
  }

  @DisplayName(
      "It must not process a valid build connection request if the player has not enought resources")
  @Tag(value = "CatanGameEngine")
  @Test
  public void itMustNotProcessAValidBuildConnectionRequestI()
      throws InvalidBoardDimensionsException, InvalidBoardElementException, NonNullInputException,
          NonVoidCollectionException, InvalidLogException {

    IPlayer player1 = new Player(0, new ResourceManager());
    IPlayer[] players = {player1};
    ICatanBoard board = buildStandardBoard(player1);

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

    engine.processRequest(
        new BuildConnectionRequest(player1, ConnectionType.ROAD, requestX, requestY));

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
    ICatanBoard board = buildStandardBoard(player2);

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

    engine.processRequest(
        new BuildConnectionRequest(player2, ConnectionType.ROAD, requestX, requestY));

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
    ICatanBoard board = buildStandardBoard(player);

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

    engine.processRequest(
        new BuildConnectionRequest(player, ConnectionType.ROAD, requestX, requestY));

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
    ICatanBoard board = buildStandardBoard(player);

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

    engine.processRequest(
        new BuildConnectionRequest(player, ConnectionType.ROAD, requestX, requestY));

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
    ICatanBoard board = buildStandardBoard(player1);

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

    engine.processRequest(
        new BuildStructureRequest(player1, StructureType.SETTLEMENT, requestX, requestY));

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
    ICatanBoard board = buildStandardBoard(player2);

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

    engine.processRequest(
        new BuildStructureRequest(player2, StructureType.SETTLEMENT, requestX, requestY));

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
    ICatanBoard board = buildStandardBoard(player);

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

    engine.processRequest(
        new BuildStructureRequest(player, StructureType.SETTLEMENT, requestX, requestY));

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
    ICatanBoard board = buildStandardBoard(player1);

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

    engine.processRequest(
        new BuildStructureRequest(player1, StructureType.SETTLEMENT, requestX, requestY));

    assertSame(true, requestFailed.get());
  }

  @DisplayName("It must not process a valid end turn request if the game is ended")
  @Tag(value = "CatanBoardEngine")
  @Test
  public void itMustNotProcessAValidEndTurnRequestI()
      throws NonNullInputException, NonVoidCollectionException, InvalidBoardDimensionsException,
          InvalidBoardElementException, InvalidLogException {

    IPlayer player = new Player(0, new ResourceManager());
    IPlayer[] players = {player};
    ICatanBoard board = buildStandardBoard(player);
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

    engine.processRequest(new EndTurnRequest(player));

    assertSame(true, requestFailed.get());
  }

  @DisplayName("it must log a valid trade request")
  @Tag("CatanBoardEngine")
  @Test
  public void itMustNotProcessAValidEndTurnRequestII()
      throws InvalidBoardDimensionsException, InvalidBoardElementException, NonNullInputException,
          NonVoidCollectionException, InvalidLogException {

    Map<ResourceType, Integer> playerResources = new TreeMap<ResourceType, Integer>();

    playerResources.put(ResourceType.BRICK, 1);
    playerResources.put(ResourceType.GRAIN, 1);
    playerResources.put(ResourceType.LUMBER, 1);
    playerResources.put(ResourceType.WOOL, 1);

    IPlayer player = new Player(0, new ResourceManager(playerResources));
    IPlayer[] players = {player};
    ICatanBoard board = buildStandardBoard(player);

    AtomicBoolean requestFailed = new AtomicBoolean(false);

    Consumer<IRequest> errorHandler =
        (request) -> {
          requestFailed.set(true);
        };

    Collection<ILogEntry> entries = new ArrayList<ILogEntry>();
    ArrayList<IRequest> entryRequests = new ArrayList<IRequest>();

    entryRequests.add(new StartTurnRequest(player));
    entries.add(new LogEntry(6, entryRequests));

    IGameLog log = new LinearGameLog(entries);
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
            new CatanRandomGenerator());

    Map<ResourceType, Integer> requestedResourcesMap = new TreeMap<ResourceType, Integer>();
    requestedResourcesMap.put(ResourceType.ORE, 1);

    IResourceStorage requestedResources = new ResourceStorage(requestedResourcesMap);

    Map<ResourceType, Integer> resourcesMap = new TreeMap<ResourceType, Integer>();
    resourcesMap.put(ResourceType.GRAIN, 1);

    Collection<IResourceStorage> acceptableExchanges = new ArrayList<IResourceStorage>();
    acceptableExchanges.add(new ResourceStorage(resourcesMap));

    Trade trade = new Trade(UUID.randomUUID(), acceptableExchanges, requestedResources);

    engine.processRequest(new TradeRequest(player, trade));
    engine.processRequest(new EndTurnRequest(player));

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
    ICatanBoard board = buildStandardBoard(player);
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

    engine.processRequest(new StartTurnRequest(player));

    assertSame(true, requestFailed.get());
  }

  @DisplayName("it must process a valid trade request if the turn is not started")
  @Tag("CatanBoardEngine")
  @Test
  public void itMustNotProcessAValidTradeRequestI()
      throws InvalidBoardDimensionsException, InvalidBoardElementException, NonNullInputException,
          NonVoidCollectionException, InvalidLogException {

    Map<ResourceType, Integer> playerResources = new TreeMap<ResourceType, Integer>();

    playerResources.put(ResourceType.BRICK, 1);
    playerResources.put(ResourceType.GRAIN, 1);
    playerResources.put(ResourceType.LUMBER, 1);
    playerResources.put(ResourceType.WOOL, 1);

    IPlayer player = new Player(0, new ResourceManager(playerResources));
    IPlayer[] players = {player};
    ICatanBoard board = buildStandardBoard(player);

    AtomicBoolean requestFailed = new AtomicBoolean(false);

    Consumer<IRequest> errorHandler =
        (request) -> {
          requestFailed.set(true);
        };

    Collection<ILogEntry> entries = new ArrayList<ILogEntry>();

    CatanGameEngine engine =
        new CatanGameEngine(
            board,
            players,
            10,
            GameState.NORMAL,
            0,
            false,
            errorHandler,
            new LinearGameLog(entries),
            new CatanRandomGenerator());

    Map<ResourceType, Integer> requestedResourcesMap = new TreeMap<ResourceType, Integer>();
    requestedResourcesMap.put(ResourceType.ORE, 1);

    IResourceStorage requestedResources = new ResourceStorage(requestedResourcesMap);

    Map<ResourceType, Integer> resourcesMap = new TreeMap<ResourceType, Integer>();
    resourcesMap.put(ResourceType.GRAIN, 1);

    Collection<IResourceStorage> acceptableExchanges = new ArrayList<IResourceStorage>();
    acceptableExchanges.add(new ResourceStorage(resourcesMap));

    Trade trade = new Trade(UUID.randomUUID(), acceptableExchanges, requestedResources);

    engine.processRequest(new TradeRequest(player, trade));

    assertTrue(requestFailed.get());
  }

  @DisplayName("it must process a valid trade request if the phase is not the normal one")
  @Tag("CatanBoardEngine")
  @Test
  public void itMustNotProcessAValidTradeRequestII()
      throws InvalidBoardDimensionsException, InvalidBoardElementException, NonNullInputException,
          NonVoidCollectionException, InvalidLogException {

    Map<ResourceType, Integer> playerResources = new TreeMap<ResourceType, Integer>();

    playerResources.put(ResourceType.BRICK, 1);
    playerResources.put(ResourceType.GRAIN, 1);
    playerResources.put(ResourceType.LUMBER, 1);
    playerResources.put(ResourceType.WOOL, 1);

    IPlayer player = new Player(0, new ResourceManager(playerResources));
    IPlayer[] players = {player};
    ICatanBoard board = buildStandardBoard(player);

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

    Map<ResourceType, Integer> requestedResourcesMap = new TreeMap<ResourceType, Integer>();
    requestedResourcesMap.put(ResourceType.ORE, 1);

    IResourceStorage requestedResources = new ResourceStorage(requestedResourcesMap);

    Map<ResourceType, Integer> resourcesMap = new TreeMap<ResourceType, Integer>();
    resourcesMap.put(ResourceType.GRAIN, 1);

    Collection<IResourceStorage> acceptableExchanges = new ArrayList<IResourceStorage>();
    acceptableExchanges.add(new ResourceStorage(resourcesMap));

    Trade trade = new Trade(UUID.randomUUID(), acceptableExchanges, requestedResources);

    engine.processRequest(new TradeRequest(player, trade));

    assertTrue(requestFailed.get());
  }

  @DisplayName("it must not process a valid trade request if the player is not the active one")
  @Tag("CatanBoardEngine")
  @Test
  public void itMustNotProcessAValidTradeRequestIII()
      throws InvalidBoardDimensionsException, InvalidBoardElementException, NonNullInputException,
          NonVoidCollectionException, InvalidLogException {

    Map<ResourceType, Integer> playerResources = new TreeMap<ResourceType, Integer>();

    playerResources.put(ResourceType.BRICK, 1);
    playerResources.put(ResourceType.GRAIN, 1);
    playerResources.put(ResourceType.LUMBER, 1);
    playerResources.put(ResourceType.WOOL, 1);

    IPlayer player = new Player(0, new ResourceManager(playerResources));
    IPlayer player2 = new Player(1, new ResourceManager(playerResources));
    IPlayer[] players = {player, player2};
    ICatanBoard board = buildStandardBoard(player);

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
            GameState.NORMAL,
            0,
            true,
            errorHandler,
            new LinearGameLog(entries),
            new CatanRandomGenerator());

    Map<ResourceType, Integer> requestedResourcesMap = new TreeMap<ResourceType, Integer>();
    requestedResourcesMap.put(ResourceType.ORE, 1);

    IResourceStorage requestedResources = new ResourceStorage(requestedResourcesMap);

    Map<ResourceType, Integer> resourcesMap = new TreeMap<ResourceType, Integer>();
    resourcesMap.put(ResourceType.GRAIN, 1);

    Collection<IResourceStorage> acceptableExchanges = new ArrayList<IResourceStorage>();
    acceptableExchanges.add(new ResourceStorage(resourcesMap));

    Trade trade = new Trade(UUID.randomUUID(), acceptableExchanges, requestedResources);

    engine.processRequest(new TradeRequest(player2, trade));

    assertTrue(requestFailed.get());
  }

  @DisplayName("It not process two build initial connections at the same turns")
  @Tag(value = "CatanBoardEngine")
  @Test
  public void itMustNotProcessTwoBuildInitialConnectionRequestsAtTheSameTurn()
      throws InvalidBoardDimensionsException, InvalidBoardElementException, NonNullInputException,
          NonVoidCollectionException, InvalidLogException {

    IPlayer player = new Player(0, new ResourceManager());
    IPlayer[] players = {player};
    ICatanBoard board = buildStandardBoard(player);

    AtomicBoolean requestFailed = new AtomicBoolean(false);

    Consumer<IRequest> errorHandler =
        (request) -> {
          requestFailed.set(true);
        };

    Collection<ILogEntry> entries = new ArrayList<ILogEntry>();

    ArrayList<IRequest> turn1Requests = new ArrayList<IRequest>();
    turn1Requests.add(new StartTurnRequest(player));
    turn1Requests.add(new EndTurnRequest(player));

    ArrayList<IRequest> turn2Requests = new ArrayList<IRequest>();
    turn1Requests.add(new StartTurnRequest(player));

    entries.add(new LogEntry(6, turn1Requests));

    entries.add(new LogEntry(6, turn2Requests));

    CatanGameEngine engine =
        new CatanGameEngine(
            board,
            players,
            10,
            GameState.FOUNDATION,
            1,
            true,
            errorHandler,
            new LinearGameLog(entries),
            new CatanRandomGenerator());

    int requestX1 = 3;
    int requestY1 = 4;

    int requestX2 = 2;
    int requestY2 = 3;

    engine.processRequest(
        new BuildInitialConnectionRequest(player, ConnectionType.ROAD, requestX1, requestY1));
    engine.processRequest(
        new BuildInitialConnectionRequest(player, ConnectionType.ROAD, requestX2, requestY2));

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
    ICatanBoard board = buildStandardBoard(player);

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

    engine.processRequest(
        new BuildInitialStructureRequest(player, StructureType.SETTLEMENT, requestX, requestY));
    engine.processRequest(
        new BuildInitialStructureRequest(player, StructureType.SETTLEMENT, requestX, requestY));

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
    ICatanBoard board = buildStandardBoard(player);
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

    engine.processRequest(
        new BuildConnectionRequest(player, ConnectionType.ROAD, requestX, requestY));

    IBoardElement elementBuilt = board.get(requestX, requestY);

    assertSame(BoardElementType.CONNECTION, elementBuilt.getElementType());
    assertSame(ConnectionType.ROAD, ((IBoardConnection) elementBuilt).getType());
    assertSame(player.getId(), ((IOwnedElement) elementBuilt).getOwner().getId());
    assertSame(0, player.getResourceManager().getResource(ResourceType.BRICK));
    assertSame(1, player.getResourceManager().getResource(ResourceType.GRAIN));
    assertSame(0, player.getResourceManager().getResource(ResourceType.LUMBER));
    assertSame(1, player.getResourceManager().getResource(ResourceType.WOOL));
  }

  @DisplayName("It must process a valid build inital connection request")
  @Tag(value = "CatanBoardEngine")
  @Test
  public void itMustProcessAValidBuildInitialConnectionRequest()
      throws InvalidBoardDimensionsException, InvalidBoardElementException, NonNullInputException,
          NonVoidCollectionException, InvalidLogException {

    IPlayer player = new Player(0, new ResourceManager());
    IPlayer[] players = {player};
    ICatanBoard board = buildStandardBoard(player);
    Consumer<IRequest> errorHandler =
        (request) -> {
          fail();
        };

    Collection<ILogEntry> entries = new ArrayList<ILogEntry>();

    ArrayList<IRequest> turn1Requests = new ArrayList<IRequest>();
    turn1Requests.add(new StartTurnRequest(player));
    turn1Requests.add(new EndTurnRequest(player));

    ArrayList<IRequest> turn2Requests = new ArrayList<IRequest>();
    turn1Requests.add(new StartTurnRequest(player));

    entries.add(new LogEntry(6, turn1Requests));

    entries.add(new LogEntry(6, turn2Requests));

    CatanGameEngine engine =
        new CatanGameEngine(
            board,
            players,
            10,
            GameState.FOUNDATION,
            1,
            true,
            errorHandler,
            new LinearGameLog(entries),
            new CatanRandomGenerator());

    int requestX = 3;
    int requestY = 4;

    engine.processRequest(
        new BuildInitialConnectionRequest(player, ConnectionType.ROAD, requestX, requestY));

    IBoardElement elementBuilt = board.get(requestX, requestY);

    assertSame(BoardElementType.CONNECTION, elementBuilt.getElementType());
    assertSame(ConnectionType.ROAD, ((IBoardConnection) elementBuilt).getType());
    assertSame(player, ((IOwnedElement) elementBuilt).getOwner());
  }

  @DisplayName("It must process a valid build inital structure request")
  @Tag(value = "CatanBoardEngine")
  @Test
  public void itMustProcessAValidBuildInitialStructureRequest()
      throws InvalidBoardDimensionsException, InvalidBoardElementException, NonNullInputException,
          NonVoidCollectionException, InvalidLogException {

    IPlayer player = new Player(0, new ResourceManager());
    IPlayer[] players = {player};
    ICatanBoard board = buildStandardBoard(player);
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

    engine.processRequest(
        new BuildInitialStructureRequest(player, StructureType.SETTLEMENT, requestX, requestY));

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
    ICatanBoard board = buildStandardBoard(player);
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

    engine.processRequest(
        new BuildStructureRequest(player, StructureType.SETTLEMENT, requestX, requestY));

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
    ICatanBoard board = buildStandardBoard(player);
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

    engine.processRequest(new EndTurnRequest(player));

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
    ICatanBoard board = buildStandardBoard(player);
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

    engine.processRequest(
        new BuildStructureRequest(player, StructureType.SETTLEMENT, requestX, requestY));
    engine.processRequest(
        new UpgradeStructureRequest(player, StructureType.CITY, requestX, requestY));

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
    ICatanBoard board = buildStandardBoard(player);

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

    engine.processRequest(new StartTurnRequest(player));

    assertSame(true, engine.isTurnStarted());
  }

  @DisplayName("it must process a valid trade agreement request")
  @Tag("CatanBoardEngine")
  @Test
  public void itMustProcessAValidTradeAgreementRequest()
      throws InvalidBoardDimensionsException, InvalidBoardElementException, NonNullInputException,
          NonVoidCollectionException, InvalidLogException {

    Map<ResourceType, Integer> playerResources = new TreeMap<ResourceType, Integer>();

    playerResources.put(ResourceType.BRICK, 1);
    playerResources.put(ResourceType.GRAIN, 1);
    playerResources.put(ResourceType.LUMBER, 1);
    playerResources.put(ResourceType.ORE, 1);
    playerResources.put(ResourceType.WOOL, 1);

    IPlayer player1 = new Player(0, new ResourceManager(playerResources));
    IPlayer player2 = new Player(1, new ResourceManager(playerResources));
    IPlayer[] players = {player1, player2};
    ICatanBoard board = buildStandardBoard(player1);

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

    Map<ResourceType, Integer> requestedResourcesMap = new TreeMap<ResourceType, Integer>();
    requestedResourcesMap.put(ResourceType.ORE, 1);

    IResourceStorage requestedResources = new ResourceStorage(requestedResourcesMap);

    Map<ResourceType, Integer> resourcesMap = new TreeMap<ResourceType, Integer>();
    resourcesMap.put(ResourceType.GRAIN, 1);

    Collection<IResourceStorage> acceptableExchanges = new ArrayList<IResourceStorage>();
    acceptableExchanges.add(new ResourceStorage(resourcesMap));

    ITrade trade = new Trade(UUID.randomUUID(), acceptableExchanges, requestedResources);

    engine.processRequest(new TradeRequest(player1, trade));

    ITradeAgreement tradeAgreement =
        new TradeAgreement(UUID.randomUUID(), acceptableExchanges.iterator().next(), trade);

    engine.processRequest(new TradeAgreementRequest(player2, tradeAgreement));

    assertFalse(requestFailed.get());
  }

  @DisplayName("it must process a valid trade confirmation request")
  @Tag("CatanBoardEngine")
  @Test
  public void itMustProcessAValidTradeConfirmationRequest()
      throws InvalidBoardDimensionsException, InvalidBoardElementException, NonNullInputException,
          NonVoidCollectionException, InvalidLogException {

    Map<ResourceType, Integer> playerResources = new TreeMap<ResourceType, Integer>();

    playerResources.put(ResourceType.BRICK, 1);
    playerResources.put(ResourceType.GRAIN, 1);
    playerResources.put(ResourceType.LUMBER, 1);
    playerResources.put(ResourceType.ORE, 1);
    playerResources.put(ResourceType.WOOL, 1);

    IPlayer player1 = new Player(0, new ResourceManager(playerResources));
    IPlayer player2 = new Player(1, new ResourceManager(playerResources));
    IPlayer[] players = {player1, player2};
    ICatanBoard board = buildStandardBoard(player1);

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

    Map<ResourceType, Integer> requestedResourcesMap = new TreeMap<ResourceType, Integer>();
    requestedResourcesMap.put(ResourceType.ORE, 1);

    IResourceStorage requestedResources = new ResourceStorage(requestedResourcesMap);

    Map<ResourceType, Integer> resourcesMap = new TreeMap<ResourceType, Integer>();
    resourcesMap.put(ResourceType.GRAIN, 1);

    Collection<IResourceStorage> acceptableExchanges = new ArrayList<IResourceStorage>();
    acceptableExchanges.add(new ResourceStorage(resourcesMap));

    ITrade trade = new Trade(UUID.randomUUID(), acceptableExchanges, requestedResources);

    engine.processRequest(new TradeRequest(player1, trade));

    ITradeAgreement tradeAgreement =
        new TradeAgreement(UUID.randomUUID(), acceptableExchanges.iterator().next(), trade);

    engine.processRequest(new TradeAgreementRequest(player2, tradeAgreement));

    engine.processRequest(
        new TradeConfirmationRequest(
            player1, new TradeConfirmation(UUID.randomUUID(), tradeAgreement)));

    assertFalse(requestFailed.get());
  }

  public void itMustProcessAValidTradeDiscardRequest()
      throws InvalidBoardDimensionsException, InvalidBoardElementException, NonNullInputException,
          NonVoidCollectionException, InvalidLogException {
    Map<ResourceType, Integer> playerResources = new TreeMap<ResourceType, Integer>();

    playerResources.put(ResourceType.BRICK, 1);
    playerResources.put(ResourceType.GRAIN, 1);
    playerResources.put(ResourceType.LUMBER, 1);
    playerResources.put(ResourceType.ORE, 1);
    playerResources.put(ResourceType.WOOL, 1);

    IPlayer player1 = new Player(0, new ResourceManager(playerResources));
    IPlayer player2 = new Player(1, new ResourceManager(playerResources));
    IPlayer[] players = {player1, player2};
    ICatanBoard board = buildStandardBoard(player1);

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

    Map<ResourceType, Integer> requestedResourcesMap = new TreeMap<ResourceType, Integer>();
    requestedResourcesMap.put(ResourceType.ORE, 1);

    IResourceStorage requestedResources = new ResourceStorage(requestedResourcesMap);

    Map<ResourceType, Integer> resourcesMap = new TreeMap<ResourceType, Integer>();
    resourcesMap.put(ResourceType.GRAIN, 1);

    Collection<IResourceStorage> acceptableExchanges = new ArrayList<IResourceStorage>();
    acceptableExchanges.add(new ResourceStorage(resourcesMap));

    ITrade trade = new Trade(UUID.randomUUID(), acceptableExchanges, requestedResources);

    engine.processRequest(new TradeRequest(player1, trade));

    ITradeAgreement tradeAgreement =
        new TradeAgreement(UUID.randomUUID(), acceptableExchanges.iterator().next(), trade);

    engine.processRequest(new TradeAgreementRequest(player2, tradeAgreement));
    engine.processRequest(
        new TradeDiscardRequest(player1, new TradeDiscard(UUID.randomUUID(), trade)));

    assertFalse(requestFailed.get());
  }

  @DisplayName("it must process a valid trade request")
  @Tag("CatanBoardEngine")
  @Test
  public void itMustProcessAValidTradeRequest()
      throws InvalidBoardDimensionsException, InvalidBoardElementException, NonNullInputException,
          NonVoidCollectionException, InvalidLogException {

    Map<ResourceType, Integer> playerResources = new TreeMap<ResourceType, Integer>();

    playerResources.put(ResourceType.BRICK, 1);
    playerResources.put(ResourceType.GRAIN, 1);
    playerResources.put(ResourceType.LUMBER, 1);
    playerResources.put(ResourceType.WOOL, 1);

    IPlayer player = new Player(0, new ResourceManager(playerResources));
    IPlayer[] players = {player};
    ICatanBoard board = buildStandardBoard(player);

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
            GameState.NORMAL,
            0,
            true,
            errorHandler,
            new LinearGameLog(entries),
            new CatanRandomGenerator());

    Map<ResourceType, Integer> requestedResourcesMap = new TreeMap<ResourceType, Integer>();
    requestedResourcesMap.put(ResourceType.ORE, 1);

    IResourceStorage requestedResources = new ResourceStorage(requestedResourcesMap);

    Map<ResourceType, Integer> resourcesMap = new TreeMap<ResourceType, Integer>();
    resourcesMap.put(ResourceType.GRAIN, 1);

    Collection<IResourceStorage> acceptableExchanges = new ArrayList<IResourceStorage>();
    acceptableExchanges.add(new ResourceStorage(resourcesMap));

    Trade trade = new Trade(UUID.randomUUID(), acceptableExchanges, requestedResources);

    engine.processRequest(new TradeRequest(player, trade));

    assertFalse(requestFailed.get());
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
    ICatanBoard board = buildStandardBoard(player);

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

    engine.processRequest(new StartTurnRequest(player));

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

  private ICatanBoard buildStandardBoard(IPlayer player1)
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

    return new CatanBoard(5, 5, elements, new DefaultTerrainProductionProvider());
  }

  private IBoardConnection buildVoidConnection() {
    return new BoardConnection(null, new ResourceManager(), ConnectionType.VOID);
  }
}
