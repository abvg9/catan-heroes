package com.catanheroes.core.board.terrain;

import com.catanheroes.core.board.BoardElementType;
import com.catanheroes.core.board.element.BoardElement;

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
