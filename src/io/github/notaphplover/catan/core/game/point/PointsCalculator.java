package io.github.notaphplover.catan.core.game.point;

import io.github.notaphplover.catan.core.board.BoardElementType;
import io.github.notaphplover.catan.core.board.ICatanBoard;
import io.github.notaphplover.catan.core.board.element.IBoardElement;
import io.github.notaphplover.catan.core.board.structure.IBoardStructure;
import io.github.notaphplover.catan.core.board.structure.StructureType;
import io.github.notaphplover.catan.core.game.ICatanGame;
import io.github.notaphplover.catan.core.player.IPlayer;
import java.util.Map;
import java.util.TreeMap;

public class PointsCalculator implements IPointsCalculator {

  private static final int POINTS_PER_CITY = 2;
  private static final int POINTS_PER_SETTLEMENT = 1;

  @Override
  public Map<IPlayer, Integer> getPoints(ICatanGame game) {
    Map<IPlayer, Integer> pointsMap = new TreeMap<IPlayer, Integer>();

    for (IPlayer player : game.getPlayers()) {
      pointsMap.put(player, 0);
    }

    ICatanBoard board = game.getBoard();

    for (int i = 0; i < board.getWidth(); ++i) {
      for (int j = 0; j < board.getWidth(); ++j) {
        computePoints(pointsMap, board.get(i, j));
      }
    }

    return pointsMap;
  }

  private void computePoints(Map<IPlayer, Integer> pointsMap, IBoardElement element) {
    if (element.getElementType() != BoardElementType.STRUCTURE) {
      return;
    }

    IBoardStructure structure = (IBoardStructure) element;

    if (structure.getType() == StructureType.SETTLEMENT) {
      pointsMap.put(
          structure.getOwner(), pointsMap.get(structure.getOwner()) + POINTS_PER_SETTLEMENT);
      return;
    }

    if (structure.getType() == StructureType.CITY) {
      pointsMap.put(structure.getOwner(), pointsMap.get(structure.getOwner()) + POINTS_PER_CITY);
      return;
    }
  }
}
