package com.catanheroes.core.board;

import com.catanheroes.core.board.element.IBoardElement;
import com.catanheroes.core.board.exception.InvalidBoardDimensionsException;
import com.catanheroes.core.board.exception.InvalidBoardElementException;
import com.catanheroes.core.board.production.IBoardProductionManager;
import com.catanheroes.core.resource.provider.ITerrainProductionProvider;

public class CatanBoardForTest extends CatanBoard {

  public CatanBoardForTest(
      int width,
      int height,
      IBoardElement[][] elements,
      ITerrainProductionProvider terrainProductionProvider)
      throws InvalidBoardDimensionsException, InvalidBoardElementException {

    super(width, height, elements, terrainProductionProvider);
  }

  public void setProductionManager(IBoardProductionManager productionManager) {
    this.productionManager = productionManager;
  }
}
