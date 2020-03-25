package com.ucm.dasi.catan.board;

import com.ucm.dasi.catan.board.connection.ConnectionDirection;
import com.ucm.dasi.catan.board.element.IBoardElement;
import com.ucm.dasi.catan.board.exception.InvalidBoardDimensionsException;
import com.ucm.dasi.catan.board.exception.InvalidBoardElementException;
import com.ucm.dasi.catan.board.group.StructureTerrainTypesPair;
import com.ucm.dasi.catan.board.structure.IBoardStructure;
import com.ucm.dasi.catan.board.terrain.IBoardTerrain;
import com.ucm.dasi.catan.board.terrain.TerrainType;
import com.ucm.dasi.catan.player.IPlayer;
import com.ucm.dasi.catan.resource.IResourceManager;
import com.ucm.dasi.catan.resource.ResourceManager;
import com.ucm.dasi.catan.resource.production.IResourceProduction;
import com.ucm.dasi.catan.resource.production.ResourceProduction;
import com.ucm.dasi.catan.resource.provider.ITerrainProductionProvider;
import java.util.TreeMap;

public class CatanBoard implements ICatanBoard {

  protected int width;

  protected int height;

  protected IBoardElement[][] elements;

  protected TreeMap<Integer, TreeMap<IPlayer, IResourceManager>> productionDictionary;

  protected ITerrainProductionProvider terrainProductionProvider;

  public CatanBoard(
      int width,
      int height,
      IBoardElement[][] elements,
      ITerrainProductionProvider terrainProductionProvider)
      throws InvalidBoardDimensionsException, InvalidBoardElementException {

    setDimensions(width, height);
    setElements(elements);

    this.terrainProductionProvider = terrainProductionProvider;
  }

  @Override
  public IBoardElement get(int x, int y) {
    return this.elements[x][y];
  }

  @Override
  public ConnectionDirection getConnectionDirection(int x, int y) {
    if (this.elements[x][y].getElementType() != BoardElementType.Connection) {
      return null;
    }

    return y % 2 == 0 ? ConnectionDirection.Horizontal : ConnectionDirection.Vertical;
  }

  @Override
  public int getHeight() {
    return height;
  }

  @Override
  public IResourceProduction getProduction(int productionNumber) {

    if (!isProductionDictionaryInitialized()) {
      buildProductionDictionary();
    }

    TreeMap<IPlayer, IResourceManager> numberProduction =
        productionDictionary.get(productionNumber);

    if (numberProduction == null) {
      numberProduction = new TreeMap<IPlayer, IResourceManager>();
    }

    return new ResourceProduction(productionNumber, numberProduction);
  }

  @Override
  public int getWidth() {
    return width;
  }

  @Override
  public IBoardStructure getStructure(int x, int y) {
    return (IBoardStructure) this.elements[2 * x][2 * y];
  }

  protected boolean checkElementType(BoardElementType type, int x, int y) {
    if (1 == (x + y) % 2) {
      return type == BoardElementType.Connection;
    }

    if (x % 2 == 0) {
      return type == BoardElementType.Structure;
    }

    return type == BoardElementType.Terrain;
  }

  protected void buildProductionDictionary() {

    TreeMap<Integer, TreeMap<IPlayer, IResourceManager>> innerMap =
        new TreeMap<Integer, TreeMap<IPlayer, IResourceManager>>();

    for (int i = 0; i < getWidth(); ++i) {
      for (int j = 0; j < getHeight(); ++j) {
        analyzeTerrainProduction(innerMap, i, j);
      }
    }

    productionDictionary = innerMap;
  }

  protected boolean isProductionDictionaryInitialized() {
    return productionDictionary != null;
  }

  private IBoardStructure getNWStructureOfTerrain(int x, int y) {
    return (IBoardStructure) get(x - 1, y - 1);
  }

  private IBoardStructure getNEStructureOfTerrain(int x, int y) {
    return (IBoardStructure) get(x + 1, y - 1);
  }

  private IBoardStructure getSWStructureOfTerrain(int x, int y) {
    return (IBoardStructure) get(x - 1, y + 1);
  }

  private IBoardStructure getSEStructureOfTerrain(int x, int y) {
    return (IBoardStructure) get(x + 1, y + 1);
  }

  private void analyzeTerrainProduction(
      TreeMap<Integer, TreeMap<IPlayer, IResourceManager>> innerMap, int x, int y) {

    IBoardElement element = get(x, y);

    if (element.getElementType() != BoardElementType.Terrain) {
      return;
    }

    IBoardTerrain terrain = (IBoardTerrain) element;
    if (terrain.getType() == TerrainType.None) {
      return;
    }

    analyzeStructureProduction(innerMap, terrain, getNWStructureOfTerrain(x, y));
    analyzeStructureProduction(innerMap, terrain, getNEStructureOfTerrain(x, y));
    analyzeStructureProduction(innerMap, terrain, getSWStructureOfTerrain(x, y));
    analyzeStructureProduction(innerMap, terrain, getSEStructureOfTerrain(x, y));
  }

  private void analyzeStructureProduction(
      TreeMap<Integer, TreeMap<IPlayer, IResourceManager>> innerMap,
      IBoardTerrain terrain,
      IBoardStructure structure) {

    if (structure.getOwner() == null) {
      return;
    }

    IResourceManager production =
        terrainProductionProvider.getResourceManager(
            new StructureTerrainTypesPair(structure.getType(), terrain.getType()));
    insertProduction(innerMap, terrain.getProductionNumber(), production, structure.getOwner());
  }

  private void insertProduction(
      TreeMap<Integer, TreeMap<IPlayer, IResourceManager>> innerMap,
      int productionNumber,
      IResourceManager production,
      IPlayer player) {

    TreeMap<IPlayer, IResourceManager> productionMap = innerMap.get(productionNumber);

    if (productionMap == null) {
      productionMap = new TreeMap<IPlayer, IResourceManager>();
      innerMap.put(productionNumber, productionMap);
    }

    IResourceManager playerProduction = productionMap.get(player);

    if (playerProduction == null) {
      playerProduction = new ResourceManager();
      productionMap.put(player, playerProduction);
    }

    playerProduction.add(production);
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
