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

    if (isProductionDictionaryInitialized()) {
      buildProductionDictionary();
    }
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

    elements[x][y] = element;

    if (isProductionDictionaryInitialized()) {
      buildProductionDictionary();
    }
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

  private boolean isValidBuildNew(IBoardElement element, int x, int y) {

    return isVoidElement(elements[x][y])
        && (element.getElementType() == BoardElementType.Structure
                && ((IBoardStructure) element).getType() == StructureType.Settlement
                && this.isNonVoidTerrainCloseTo(x, y)
            || element.getElementType() == BoardElementType.Connection
                && ((IBoardConnection) element).getType() == ConnectionType.Road);
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

    return !isVoidElement(oldElement)
        && oldElement.getElementType() == BoardElementType.Structure
        && ((IBoardStructure) oldElement).getType() == StructureType.Settlement
        && element.getElementType() == BoardElementType.Structure
        && ((IBoardStructure) element).getType() == StructureType.City
        && ((OwnedElement) oldElement).getOwner() == ((OwnedElement) element).getOwner();
  }
}
