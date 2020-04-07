package io.github.notaphplover.catan.core.board.terrain;

import io.github.notaphplover.catan.core.board.element.IBoardElement;

public interface IBoardTerrain extends IBoardElement {
  /**
   * Gets the terrain's type
   *
   * @return terrain's type.
   */
  TerrainType getType();

  /**
   * Gets the production number. The production number is the number to be obtained in order to
   * produce resources.
   *
   * @return Production number
   */
  int getProductionNumber();
}
