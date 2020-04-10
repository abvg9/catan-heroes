package io.github.notaphplover.catan.core.game;

import io.github.notaphplover.catan.core.board.ICatanBoard;
import io.github.notaphplover.catan.core.command.ICommandSender;
import io.github.notaphplover.catan.core.game.generator.INumberGenerator;
import io.github.notaphplover.catan.core.game.log.IGameLog;
import io.github.notaphplover.catan.core.game.player.IPlayerManager;
import io.github.notaphplover.catan.core.request.IRequest;
import java.util.function.Consumer;

public class CatanGameBuilder implements ICatanGameBuilder {

  private ICatanBoard board;
  private ICommandSender commandSender;
  private Consumer<IRequest> errorHandler;
  private IGameLog gameLog;
  private INumberGenerator numberGenerator;
  private IPlayerManager playerManager;
  private int pointsToWin;
  private GameState state;

  @Override
  public ICatanBoard getBoard() {
    return board;
  }

  @Override
  public ICatanGameBuilder setBoard(ICatanBoard board) {
    this.board = board;
    return this;
  }

  @Override
  public ICommandSender getCommandSender() {
    return commandSender;
  }

  @Override
  public ICatanGameBuilder setCommandSender(ICommandSender commandSender) {
    this.commandSender = commandSender;
    return this;
  }

  @Override
  public Consumer<IRequest> getErrorHandler() {
    return errorHandler;
  }

  @Override
  public ICatanGameBuilder setErrorHandler(Consumer<IRequest> errorHandler) {
    this.errorHandler = errorHandler;
    return this;
  }

  @Override
  public IGameLog getGameLog() {
    return gameLog;
  }

  @Override
  public ICatanGameBuilder setGameLog(IGameLog gameLog) {
    this.gameLog = gameLog;
    return this;
  }

  @Override
  public INumberGenerator getNumberGenerator() {
    return numberGenerator;
  }

  @Override
  public ICatanGameBuilder setNumberGenerator(INumberGenerator numberGenerator) {
    this.numberGenerator = numberGenerator;
    return this;
  }

  @Override
  public IPlayerManager getPlayerManager() {
    return playerManager;
  }

  @Override
  public ICatanGameBuilder setPlayerManager(IPlayerManager playerManager) {
    this.playerManager = playerManager;
    return this;
  }

  @Override
  public int getPointsToWin() {
    return pointsToWin;
  }

  @Override
  public ICatanGameBuilder setPointsToWin(int pointsToWin) {
    this.pointsToWin = pointsToWin;
    return this;
  }

  @Override
  public GameState getState() {
    return state;
  }

  @Override
  public ICatanGameBuilder setState(GameState state) {
    this.state = state;
    return this;
  }
}
