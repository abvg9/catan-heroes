package com.ucm.dasi.catan.board;

import com.ucm.dasi.catan.board.element.IBoardElement;
import com.ucm.dasi.catan.board.exception.InvalidBoardDimensionsException;
import com.ucm.dasi.catan.board.exception.InvalidBoardElementException;
import com.ucm.dasi.catan.board.production.IBoardProductionManager;
import com.ucm.dasi.catan.resource.provider.ITerrainProductionProvider;

public class CatanEditableBoardForTest extends CatanEditableBoard {

  public CatanEditableBoardForTest(
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
