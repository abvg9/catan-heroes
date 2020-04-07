package io.github.notaphplover.catan.core.board;

import io.github.notaphplover.catan.core.board.connection.ConnectionDirection;
import io.github.notaphplover.catan.core.board.connection.ConnectionType;
import io.github.notaphplover.catan.core.board.connection.IBoardConnection;
import io.github.notaphplover.catan.core.board.element.IBoardElement;
import io.github.notaphplover.catan.core.board.element.OwnedElement;
import io.github.notaphplover.catan.core.board.exception.InvalidBoardDimensionsException;
import io.github.notaphplover.catan.core.board.exception.InvalidBoardElementException;
import io.github.notaphplover.catan.core.board.production.BoardProductionManager;
import io.github.notaphplover.catan.core.board.production.IBoardProductionManager;
import io.github.notaphplover.catan.core.board.structure.IBoardStructure;
import io.github.notaphplover.catan.core.board.structure.StructureType;
import io.github.notaphplover.catan.core.board.terrain.IBoardTerrain;
import io.github.notaphplover.catan.core.board.terrain.TerrainType;
import io.github.notaphplover.catan.core.resource.production.IResourceProduction;
import io.github.notaphplover.catan.core.resource.provider.ITerrainProductionProvider;

public class CatanBoard implements ICatanBoard {

  protected int width;

  protected int height;

  protected IBoardElement[][] elements;

  protected IBoardProductionManager productionManager;

  public CatanBoard(
      int width,
      int height,
      IBoardElement[][] elements,
      ITerrainProductionProvider terrainProductionProvider)
      throws InvalidBoardDimensionsException, InvalidBoardElementException {

    setDimensions(width, height);
    setElements(elements);

    productionManager = new BoardProductionManager(this, terrainProductionProvider);
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

    productionManager.syncProductionOnStructureBuilt(x, y);
  }

  @Override
  public IBoardElement get(int x, int y) {
    return this.elements[x][y];
  }

  @Override
  public ConnectionDirection getConnectionDirection(int x, int y) {
    if (this.elements[x][y].getElementType() != BoardElementType.CONNECTION) {
      return null;
    }

    return y % 2 == 0 ? ConnectionDirection.HORIZONTAL : ConnectionDirection.VERTICAL;
  }

  @Override
  public int getHeight() {
    return height;
  }

  @Override
  public IResourceProduction getProduction(int productionNumber) {

    return productionManager.getProduction(productionNumber);
  }

  @Override
  public int getWidth() {
    return width;
  }

  @Override
  public IBoardStructure getStructure(int x, int y) {
    return (IBoardStructure) this.elements[2 * x][2 * y];
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

    productionManager.syncProductionOnStructureUpgrade(oldElement, x, y);
  }

  private boolean checkElementType(BoardElementType type, int x, int y) {
    if (1 == (x + y) % 2) {
      return type == BoardElementType.CONNECTION;
    }

    if (x % 2 == 0) {
      return type == BoardElementType.STRUCTURE;
    }

    return type == BoardElementType.TERRAIN;
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

  private boolean isValidBuildNew(IBoardElement element, int x, int y) {

    return isVoidElement(elements[x][y])
        && (element.getElementType() == BoardElementType.STRUCTURE
                && ((IBoardStructure) element).getType() == StructureType.SETTLEMENT
                && this.isNonVoidTerrainCloseTo(x, y)
            || element.getElementType() == BoardElementType.CONNECTION
                && ((IBoardConnection) element).getType() == ConnectionType.ROAD);
  }

  private boolean isValidBuildUpgrade(IBoardElement element, IBoardElement oldElement) {

    return !isVoidElement(oldElement)
        && oldElement.getElementType() == BoardElementType.STRUCTURE
        && ((IBoardStructure) oldElement).getType() == StructureType.SETTLEMENT
        && element.getElementType() == BoardElementType.STRUCTURE
        && ((IBoardStructure) element).getType() == StructureType.CITY
        && ((OwnedElement) oldElement).getOwner() == ((OwnedElement) element).getOwner();
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

  private void setDimensions(int width, int height) throws InvalidBoardDimensionsException {
    if (width % 2 == 0 || height % 2 == 0) {
      throw new InvalidBoardDimensionsException();
    }

    this.width = width;
    this.height = height;
  }

  private void setElements(IBoardElement[][] elements)
      throws InvalidBoardDimensionsException, InvalidBoardElementException {
    if (elements.length != width) {
      throw new InvalidBoardDimensionsException();
    }

    this.elements = new IBoardElement[width][height];

    for (int i = 0; i < width; ++i) {
      if (elements[i].length != height) {
        throw new InvalidBoardDimensionsException();
      }

      for (int j = 0; j < height; ++j) {
        if (null == elements[i][j] || !checkElementType(elements[i][j].getElementType(), i, j)) {
          throw new InvalidBoardElementException(
              null == elements[i][j] ? null : elements[i][j].getElementType());
        }
        this.elements[i][j] = elements[i][j];
      }
    }
  }
}
