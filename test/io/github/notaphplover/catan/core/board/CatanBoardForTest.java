package io.github.notaphplover.catan.core.board;

import io.github.notaphplover.catan.core.board.element.IBoardElement;
import io.github.notaphplover.catan.core.board.exception.InvalidBoardDimensionsException;
import io.github.notaphplover.catan.core.board.exception.InvalidBoardElementException;
import io.github.notaphplover.catan.core.board.production.IBoardProductionManager;
import io.github.notaphplover.catan.core.resource.provider.ITerrainProductionProvider;

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
