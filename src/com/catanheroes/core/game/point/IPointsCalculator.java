package com.catanheroes.core.game.point;

import com.catanheroes.core.game.ICatanGame;
import com.catanheroes.core.player.IPlayer;
import java.util.Map;

public interface IPointsCalculator {

  /**
   * Gets the points achieved by every player
   *
   * @param game Game instance to process.
   * @return Map of players to player points.
   */
  Map<IPlayer, Integer> getPoints(ICatanGame game);
}
