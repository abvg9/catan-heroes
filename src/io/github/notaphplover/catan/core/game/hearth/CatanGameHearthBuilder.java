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

public class CatanGameHearthBuilder implements ICatanGameHearthBuilder {

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
  public ICatanGameHearthBuilder setBoard(ICatanBoard board) {
    this.board = board;

    return this;
  }

  @Override
  public ICatanGameHearthBuilder setCommandSender(ICommandSender commandSender) {
    this.commandSender = commandSender;

    return this;
  }

  @Override
  public ICatanGameHearthBuilder setConnectionCostProvider(
      IResourceManagerProvider<ConnectionType> connectionCostProvider) {
    this.connectionCostProvider = connectionCostProvider;

    return this;
  }

  @Override
  public ICatanGameHearthBuilder setErrorHandler(Consumer<IRequest> errorHandler) {
    this.errorHandler = errorHandler;

    return this;
  }

  @Override
  public ICatanGameHearthBuilder setGameLog(IGameLog gameLog) {
    this.gameLog = gameLog;

    return this;
  }

  @Override
  public ICatanGameHearthBuilder setNumberGenerator(INumberGenerator numberGenerator) {
    this.numberGenerator = numberGenerator;

    return this;
  }

  @Override
  public ICatanGameHearthBuilder setPlayerManager(IPlayerManager playerManager) {
    this.playerManager = playerManager;

    return this;
  }

  @Override
  public ICatanGameHearthBuilder setPointsCalculator(IPointsCalculator pointsCalculator) {
    this.pointsCalculator = pointsCalculator;

    return this;
  }

  @Override
  public ICatanGameHearthBuilder setPointsToWin(int pointsToWin) {
    this.pointsToWin = pointsToWin;

    return this;
  }

  @Override
  public ICatanGameHearthBuilder setState(GameState state) {
    this.state = state;

    return this;
  }

  @Override
  public ICatanGameHearthBuilder setStructureCostProvider(
      IResourceManagerProvider<StructureType> structureCostProvider) {
    this.structureCostProvider = structureCostProvider;

    return this;
  }

  @Override
  public ICatanGameHearthBuilder setTradeManager(ITradeManager tradeManager) {
    this.tradeManager = tradeManager;

    return this;
  }
}
