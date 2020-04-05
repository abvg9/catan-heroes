package com.ucm.dasi.catan.game;

import com.ucm.dasi.catan.board.ICatanBoard;
import com.ucm.dasi.catan.player.IPlayer;
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
