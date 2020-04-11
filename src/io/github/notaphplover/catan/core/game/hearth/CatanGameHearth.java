package io.github.notaphplover.catan.core.game.hearth;

import io.github.notaphplover.catan.core.board.ICatanBoard;
import io.github.notaphplover.catan.core.board.connection.ConnectionType;
import io.github.notaphplover.catan.core.board.structure.StructureType;
import io.github.notaphplover.catan.core.command.ICommandSender;
import io.github.notaphplover.catan.core.game.GameState;
import io.github.notaphplover.catan.core.game.generator.INumberGenerator;
import io.github.notaphplover.catan.core.game.log.IGameLog;
import io.github.notaphplover.catan.core.game.player.IPlayerManager;
import io.github.notaphplover.catan.core.game.point.IPointsCalculator;
import io.github.notaphplover.catan.core.game.trade.ITradeManager;
import io.github.notaphplover.catan.core.request.IRequest;
import io.github.notaphplover.catan.core.resource.provider.IResourceManagerProvider;
import java.util.function.Consumer;

public class CatanGameHearth implements ICatanGameHearth {

  private ICatanBoard board;

  private ICommandSender commandSender;

  private IResourceManagerProvider<ConnectionType> connectionCostProvider;

  private Consumer<IRequest> errorHandler;

  private IGameLog gameLog;

  private INumberGenerator numberGenerator;

  private IPlayerManager playerManager;

  private IPointsCalculator pointsCalculator;

  private int pointsToWin;

  private GameState state;

  private IResourceManagerProvider<StructureType> structureCostProvider;

  private ITradeManager tradeManager;

  public CatanGameHearth(ICatanGameHearthBuilder builder) {

    this.board = builder.getBoard();
    this.commandSender = builder.getCommandSender();
    this.connectionCostProvider = builder.getConnectionCostProvider();
    this.errorHandler = builder.getErrorHandler();
    this.gameLog = builder.getGameLog();
    this.numberGenerator = builder.getNumberGenerator();
    this.playerManager = builder.getPlayerManager();
    this.pointsCalculator = builder.getPointsCalculator();
    this.pointsToWin = builder.getPointsToWin();
    this.state = builder.getState();
    this.structureCostProvider = builder.getStructureCostProvider();
    this.tradeManager = builder.getTradeManager();
  }

  @Override
  public ICatanBoard getBoard() {
    return board;
  }

  @Override
  public ICommandSender getCommandSender() {
    return commandSender;
  }

  @Override
  public IResourceManagerProvider<ConnectionType> getConnectionCostProvider() {
    return connectionCostProvider;
  }

  @Override
  public Consumer<IRequest> getErrorHandler() {
    return errorHandler;
  }

  @Override
  public IGameLog getGameLog() {
    return gameLog;
  }

  @Override
  public INumberGenerator getNumberGenerator() {
    return numberGenerator;
  }

  @Override
  public IPlayerManager getPlayerManager() {
    return playerManager;
  }

  @Override
  public IPointsCalculator getPointsCalculator() {
    return pointsCalculator;
  }

  @Override
  public int getPointsToWin() {
    return pointsToWin;
  }

  @Override
  public GameState getState() {
    return state;
  }

  @Override
  public IResourceManagerProvider<StructureType> getStructureCostProvider() {
    return structureCostProvider;
  }

  @Override
  public ITradeManager getTradeManager() {
    return tradeManager;
  }

  @Override
  public void setState(GameState state) {
    this.state = state;
  }
}
