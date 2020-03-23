package com.ucm.dasi.catan.board.terrain;

import com.ucm.dasi.catan.board.BoardElementType;
import com.ucm.dasi.catan.board.element.BoardElement;

public class BoardTerrain extends BoardElement implements IBoardTerrain {

  protected int productionNumber;

  protected TerrainType type;

  public BoardTerrain(int productionNumber, TerrainType type) {
    super(BoardElementType.Terrain);

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
