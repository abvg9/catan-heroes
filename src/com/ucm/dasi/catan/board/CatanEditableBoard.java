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

public class CatanEditableBoard extends CatanBoard implements ICatanEditableBoard {

  public CatanEditableBoard(int width, int height, IBoardElement[][] elements)
      throws InvalidBoardDimensionsException, InvalidBoardElementException {

    super(width, height, elements);
  }

  @Override
  public void build(IBoardElement element, int x, int y) throws InvalidBoardElementException {

    if (null == element || !isValidBuild(element, x, y)) {
      throw new InvalidBoardElementException(element == null ? null : element.getElementType());
    }
    this.elements[x][y] = element;
  }

  private boolean isVoidElement(IBoardElement element) {
    switch (element.getElementType()) {
      case Connection:
        return ((IBoardConnection) element).getType() == ConnectionType.Void;
      case Structure:
        return ((IBoardStructure) element).getType() == StructureType.None;
      case Terrain:
        return ((IBoardTerrain) element).getType() == TerrainType.None;
      default:
        return false;
    }
  }

  private boolean isValidBuild(IBoardElement element, int x, int y) {

    if (!this.checkElementType(element.getElementType(), x, y)) {
      return false;
    }

    if (isVoidElement(elements[x][y])) {
      return this.isValidBuildNew(element, x, y);
    } else {
      return this.isValidBuildUpgrade(element, elements[x][y]);
    }
  }

  private boolean isValidBuildNew(IBoardElement element, int x, int y) {

    return element.getElementType() != BoardElementType.Structure
        || ((IBoardStructure) element).getType() != StructureType.City
            && this.isNonVoidTerrainCloseTo(x, y);
  }

  private boolean isNonVoidTerrainCloseTo(int x, int y) {

    return get(x, y).getElementType() == BoardElementType.Structure
        && ((x - 1 >= 0
                && y - 1 >= 0
                && ((IBoardTerrain) get(x - 1, y - 1)).getType() != TerrainType.None)
            || (x - 1 >= 0
                && y + 1 < getHeight()
                && ((IBoardTerrain) get(x - 1, y + 1)).getType() != TerrainType.None)
            || (x + 1 < getWidth()
                && y - 1 >= 0
                && ((IBoardTerrain) get(x + 1, y - 1)).getType() != TerrainType.None)
            || (x + 1 < getWidth()
                && y + 1 < getHeight()
                && ((IBoardTerrain) get(x + 1, y + 1)).getType() != TerrainType.None));
  }

  private boolean isValidBuildUpgrade(IBoardElement element, IBoardElement oldElement) {

    return oldElement.getElementType() == BoardElementType.Structure
        && ((IBoardStructure) oldElement).getType() == StructureType.Settlement
        && element.getElementType() == BoardElementType.Structure
        && ((IBoardStructure) element).getType() == StructureType.City
        && ((OwnedElement) oldElement).getOwner() == ((OwnedElement) element).getOwner();
  }
}
