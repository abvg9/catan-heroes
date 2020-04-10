package io.github.notaphplover.catan.core.game;

import io.github.notaphplover.catan.core.board.ICatanBoard;
import io.github.notaphplover.catan.core.command.ICommandSender;
import io.github.notaphplover.catan.core.game.generator.INumberGenerator;
import io.github.notaphplover.catan.core.game.log.IGameLog;
import io.github.notaphplover.catan.core.game.player.IPlayerManager;
import io.github.notaphplover.catan.core.request.IRequest;
import java.util.function.Consumer;

public interface ICatanGameBuilder {

  ICatanBoard getBoard();

  ICommandSender getCommandSender();

  Consumer<IRequest> getErrorHandler();

  IGameLog getGameLog();

  INumberGenerator getNumberGenerator();

  IPlayerManager getPlayerManager();

  int getPointsToWin();

  GameState getState();

  ICatanGameBuilder setBoard(ICatanBoard board);

  ICatanGameBuilder setCommandSender(ICommandSender commandSender);

  ICatanGameBuilder setErrorHandler(Consumer<IRequest> errorHandler);

  ICatanGameBuilder setGameLog(IGameLog gameLog);

  ICatanGameBuilder setNumberGenerator(INumberGenerator numberGenerator);

  ICatanGameBuilder setPlayerManager(IPlayerManager playerManager);

  ICatanGameBuilder setPointsToWin(int pointsToWin);

  ICatanGameBuilder setState(GameState state);
}
