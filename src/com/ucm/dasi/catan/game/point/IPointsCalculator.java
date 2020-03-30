package com.ucm.dasi.catan.game.point;

import com.ucm.dasi.catan.board.ICatanBoard;
import com.ucm.dasi.catan.game.ICatanGame;
import com.ucm.dasi.catan.player.IPlayer;
import java.util.Map;

public interface IPointsCalculator {

  /**
   * Gets the points achieved by every player
   *
   * @param game Game instance to process.
   * @return Map of players to player points.
   */
  Map<IPlayer, Integer> getPoints(ICatanGame<? extends ICatanBoard> game);
}
