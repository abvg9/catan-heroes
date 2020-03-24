package com.ucm.dasi.catan.game;

import com.ucm.dasi.catan.board.ICatanBoard;
import com.ucm.dasi.catan.player.IPlayer;

public interface ICatanGame<TBoard extends ICatanBoard> {
  IPlayer getActivePlayer();

  TBoard getBoard();

  IPlayer[] getPlayers();
}
