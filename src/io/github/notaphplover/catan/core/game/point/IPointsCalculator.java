package io.github.notaphplover.catan.core.game.point;

import io.github.notaphplover.catan.core.game.ICatanGame;
import io.github.notaphplover.catan.core.player.IPlayer;
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
