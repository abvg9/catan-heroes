package com.ucm.dasi.catan.game;

import com.ucm.dasi.catan.board.ICatanBoard;
import com.ucm.dasi.catan.player.IPlayer;

public interface ICatanGame {
    IPlayer getActivePlayer();
    
    ICatanBoard getBoard();

    IPlayer[] getPlayers();
}
