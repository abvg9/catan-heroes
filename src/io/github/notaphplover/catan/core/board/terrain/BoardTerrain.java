package io.github.notaphplover.catan.core.board.terrain;

import io.github.notaphplover.catan.core.board.BoardElementType;
import io.github.notaphplover.catan.core.board.element.BoardElement;

public class BoardTerrain extends BoardElement implements IBoardTerrain {

  protected int productionNumber;

  protected TerrainType type;

  public BoardTerrain(int productionNumber, TerrainType type) {
    super(BoardElementType.TERRAIN);

    this.productionNumber = productionNumber;
    this.type = type;
  }

  public int getProductionNumber() {
    return productionNumber;
  }

  public TerrainType getType() {
    return type;
  }
}
