package com.ucm.dasi.catan.board;

import com.ucm.dasi.catan.board.connection.ConnectionType;
import com.ucm.dasi.catan.board.connection.IBoardConnection;
import com.ucm.dasi.catan.board.element.IBoardElement;
import com.ucm.dasi.catan.board.element.OwnedElement;
import com.ucm.dasi.catan.board.exception.InvalidBoardDimensionsException;
import com.ucm.dasi.catan.board.exception.InvalidBoardElementException;
import com.ucm.dasi.catan.board.structure.IBoardStructure;
import com.ucm.dasi.catan.board.structure.StructureType;
import com.ucm.dasi.catan.board.terrain.IBoardTerrain;
import com.ucm.dasi.catan.board.terrain.TerrainType;
import com.ucm.dasi.catan.resource.provider.ITerrainProductionProvider;

public class CatanEditableBoard extends CatanBoard implements ICatanEditableBoard {

  public CatanEditableBoard(
      int width,
      int height,
      IBoardElement[][] elements,
      ITerrainProductionProvider terrainProductionProvider)
      throws InvalidBoardDimensionsException, InvalidBoardElementException {

    super(width, height, elements, terrainProductionProvider);
  }

  @Override
  public void build(IBoardElement element, int x, int y) throws InvalidBoardElementException {

    if (null == element) {
      throw new InvalidBoardElementException(null);
    }

    if (!this.checkElementType(element.getElementType(), x, y)) {
      throw new InvalidBoardElementException(element.getElementType());
    }

    if (!isValidBuildNew(element, x, y)) {
      throw new InvalidBoardElementException(element.getElementType());
    }

    this.elements[x][y] = element;

    productionManager.syncProductionOnStructureBuilt(this, x, y);
  }

  @Override
  public void upgrade(IBoardElement element, int x, int y) throws InvalidBoardElementException {

    if (null == element) {
      throw new InvalidBoardElementException(null);
    }

    if (!checkElementType(element.getElementType(), x, y)) {
      throw new InvalidBoardElementException(element.getElementType());
    }

    if (!isValidBuildUpgrade(element, elements[x][y])) {
      throw new InvalidBoardElementException(element.getElementType());
    }

    IBoardStructure oldElement = (IBoardStructure) elements[x][y];

    elements[x][y] = element;

    productionManager.syncProductionOnStructureUpgrade(this, oldElement, x, y);
  }

  private boolean isVoidElement(IBoardElement element) {
    switch (element.getElementType()) {
      case CONNECTION:
        return ((IBoardConnection) element).getType() == ConnectionType.VOID;
      case STRUCTURE:
        return ((IBoardStructure) element).getType() == StructureType.NONE;
      case TERRAIN:
        return ((IBoardTerrain) element).getType() == TerrainType.NONE;
      default:
        return false;
    }
  }

  private boolean isValidBuildNew(IBoardElement element, int x, int y) {

    return isVoidElement(elements[x][y])
        && (element.getElementType() == BoardElementType.STRUCTURE
                && ((IBoardStructure) element).getType() == StructureType.SETTLEMENT
                && this.isNonVoidTerrainCloseTo(x, y)
            || element.getElementType() == BoardElementType.CONNECTION
                && ((IBoardConnection) element).getType() == ConnectionType.ROAD);
  }

  private boolean isNonVoidTerrainCloseTo(int x, int y) {

    return get(x, y).getElementType() == BoardElementType.STRUCTURE
        && ((x - 1 >= 0
                && y - 1 >= 0
                && ((IBoardTerrain) get(x - 1, y - 1)).getType() != TerrainType.NONE)
            || (x - 1 >= 0
                && y + 1 < getHeight()
                && ((IBoardTerrain) get(x - 1, y + 1)).getType() != TerrainType.NONE)
            || (x + 1 < getWidth()
                && y - 1 >= 0
                && ((IBoardTerrain) get(x + 1, y - 1)).getType() != TerrainType.NONE)
            || (x + 1 < getWidth()
                && y + 1 < getHeight()
                && ((IBoardTerrain) get(x + 1, y + 1)).getType() != TerrainType.NONE));
  }

  private boolean isValidBuildUpgrade(IBoardElement element, IBoardElement oldElement) {

    return !isVoidElement(oldElement)
        && oldElement.getElementType() == BoardElementType.STRUCTURE
        && ((IBoardStructure) oldElement).getType() == StructureType.SETTLEMENT
        && element.getElementType() == BoardElementType.STRUCTURE
        && ((IBoardStructure) element).getType() == StructureType.CITY
        && ((OwnedElement) oldElement).getOwner() == ((OwnedElement) element).getOwner();
  }
}
