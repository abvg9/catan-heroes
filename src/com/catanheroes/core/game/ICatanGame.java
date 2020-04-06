package com.catanheroes.core.game;

import com.catanheroes.core.board.ICatanBoard;
import com.catanheroes.core.player.IPlayer;
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
