package io.github.notaphplover.catan.core.game;

import io.github.notaphplover.catan.core.board.ICatanBoard;
import io.github.notaphplover.catan.core.player.IPlayer;
import java.util.Map;

public interface ICatanGame {
  IPlayer getActivePlayer();

  ICatanBoard getBoard();

  IPlayer[] getPlayers();

  Map<IPlayer, Integer> getPoints();

  int getPointsToWin();

  GameState getState();

  int getTurnNumber();

  boolean isTurnStarted();
}
