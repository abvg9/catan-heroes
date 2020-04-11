package io.github.notaphplover.catan.core.game;

import io.github.notaphplover.catan.core.board.ICatanBoard;
import io.github.notaphplover.catan.core.game.log.ILogEntry;
import io.github.notaphplover.catan.core.player.IPlayer;
import io.github.notaphplover.catan.core.request.IRequest;
import java.util.Map;

public interface ICatanGameEngine {

  IPlayer getActivePlayer();

  ICatanBoard getBoard();

  ILogEntry getLog(int turn);

  IPlayer[] getPlayers();

  Map<IPlayer, Integer> getPoints();

  int getPointsToWin();

  GameState getState();

  int getTurnNumber();

  boolean isTurnStarted();

  void processRequest(IRequest requests);
}
