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

public interface ICatanGameHearthBuilder {

  ICatanBoard getBoard();

  ICommandSender getCommandSender();

  IResourceManagerProvider<ConnectionType> getConnectionCostProvider();

  Consumer<IRequest> getErrorHandler();

  IGameLog getGameLog();

  INumberGenerator getNumberGenerator();

  IPlayerManager getPlayerManager();

  IPointsCalculator getPointsCalculator();

  int getPointsToWin();

  GameState getState();

  IResourceManagerProvider<StructureType> getStructureCostProvider();

  ITradeManager getTradeManager();

  ICatanGameHearthBuilder setBoard(ICatanBoard board);

  ICatanGameHearthBuilder setCommandSender(ICommandSender commandSender);

  ICatanGameHearthBuilder setConnectionCostProvider(
      IResourceManagerProvider<ConnectionType> connectionCostProvider);

  ICatanGameHearthBuilder setErrorHandler(Consumer<IRequest> errorHandler);

  ICatanGameHearthBuilder setGameLog(IGameLog gameLog);

  ICatanGameHearthBuilder setNumberGenerator(INumberGenerator numberGenerator);

  ICatanGameHearthBuilder setPlayerManager(IPlayerManager playerManager);

  ICatanGameHearthBuilder setPointsCalculator(IPointsCalculator pointsCalculator);

  ICatanGameHearthBuilder setPointsToWin(int pointsToWin);

  ICatanGameHearthBuilder setState(GameState state);

  ICatanGameHearthBuilder setStructureCostProvider(
      IResourceManagerProvider<StructureType> structureCostProvider);

  ICatanGameHearthBuilder setTradeManager(ITradeManager tradeManager);
}
