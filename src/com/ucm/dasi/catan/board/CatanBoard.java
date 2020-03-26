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
import com.ucm.dasi.catan.resource.exception.NotEnoughtResourcesException;
import com.ucm.dasi.catan.resource.production.IResourceProduction;
import com.ucm.dasi.catan.resource.production.ResourceProduction;
import com.ucm.dasi.catan.resource.provider.ITerrainProductionProvider;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.function.BiConsumer;

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

  protected boolean isProductionDictionaryInitialized() {
    return productionDictionary != null;
  }

  protected void syncProductionOnStructureBuilt(int x, int y) {
    IBoardElement element = get(x, y);
    if (element.getElementType() != BoardElementType.Structure) {
      return;
    }
    IBoardStructure structure = (IBoardStructure) element;
    TreeMap<Integer, IResourceManager> production = getProductionOfStructure(structure, x, y);

    addProductionOfPlayer(structure.getOwner(), production);
  }

  protected void syncProductionOnStructureUpgrade(IBoardStructure oldStructure, int x, int y) {
    syncProductionOnStructureBuilt(x, y);

    TreeMap<Integer, IResourceManager> production = getProductionOfStructure(oldStructure, x, y);

    removeProductionOfPlayer(oldStructure.getOwner(), production);
  }

  private void addProductionOfPlayer(
      IPlayer player, TreeMap<Integer, IResourceManager> production) {

    transformProductionOfPlayer(player, production, (total, diff) -> total.add(diff));
  }

  private void buildProductionDictionary() {

    TreeMap<Integer, TreeMap<IPlayer, IResourceManager>> innerMap =
        new TreeMap<Integer, TreeMap<IPlayer, IResourceManager>>();

    for (int i = 0; i < getWidth(); ++i) {
      for (int j = 0; j < getHeight(); ++j) {
        analyzeTerrainProduction(innerMap, i, j);
      }
    }

    productionDictionary = innerMap;
  }

  private IBoardStructure getNWStructureOfTerrain(int x, int y) {
    return (IBoardStructure) get(x - 1, y - 1);
  }

  private IBoardTerrain getNWTerrainOfStructure(int x, int y) {
    return (x > 0 && y > 0) ? (IBoardTerrain) get(x - 1, y - 1) : null;
  }

  private IBoardStructure getNEStructureOfTerrain(int x, int y) {
    return (IBoardStructure) get(x + 1, y - 1);
  }

  private IBoardTerrain getNETerrainOfStructure(int x, int y) {
    return (x + 1 < getWidth() && y > 0) ? (IBoardTerrain) get(x + 1, y - 1) : null;
  }

  private TreeMap<Integer, IResourceManager> getProductionOfStructure(
      IBoardStructure structure, int x, int y) {

    TreeMap<Integer, IResourceManager> productionMap = new TreeMap<Integer, IResourceManager>();

    addProductionOfStructureOverTerrain(productionMap, structure, getNWTerrainOfStructure(x, y));
    addProductionOfStructureOverTerrain(productionMap, structure, getNETerrainOfStructure(x, y));
    addProductionOfStructureOverTerrain(productionMap, structure, getSWTerrainOfStructure(x, y));
    addProductionOfStructureOverTerrain(productionMap, structure, getSETerrainOfStructure(x, y));

    return productionMap;
  }

  private void addProductionOfStructureOverTerrain(
      TreeMap<Integer, IResourceManager> productionMap,
      IBoardStructure structure,
      IBoardTerrain terrain) {
    if (terrain == null) {
      return;
    }

    int productionNumber = terrain.getProductionNumber();

    IResourceManager numberProduction = productionMap.get(productionNumber);

    if (numberProduction == null) {
      numberProduction = new ResourceManager();
      productionMap.put(productionNumber, numberProduction);
    }

    IResourceManager structureProductionOverTerrain =
        terrainProductionProvider.getResourceManager(
            new StructureTerrainTypesPair(structure.getType(), terrain.getType()));
    numberProduction.add(structureProductionOverTerrain);
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

  private IBoardStructure getSWStructureOfTerrain(int x, int y) {
    return (IBoardStructure) get(x - 1, y + 1);
  }

  private IBoardTerrain getSWTerrainOfStructure(int x, int y) {
    return (x > 0 && y + 1 < getHeight()) ? (IBoardTerrain) get(x - 1, y + 1) : null;
  }

  private IBoardStructure getSEStructureOfTerrain(int x, int y) {
    return (IBoardStructure) get(x + 1, y + 1);
  }

  private IBoardTerrain getSETerrainOfStructure(int x, int y) {
    return (x + 1 < getWidth() && y + 1 < getHeight()) ? (IBoardTerrain) get(x + 1, y + 1) : null;
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

  private void removeProductionOfPlayer(
      IPlayer player, TreeMap<Integer, IResourceManager> production) {

    transformProductionOfPlayer(
        player,
        production,
        (total, diff) -> {
          try {
            total.substract(diff);
          } catch (NotEnoughtResourcesException e) {
            e.printStackTrace();
          }
        });
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

  private void transformProductionOfPlayer(
      IPlayer player,
      TreeMap<Integer, IResourceManager> production,
      BiConsumer<IResourceManager, IResourceManager> operation) {

    for (Entry<Integer, IResourceManager> productionEntry : production.entrySet()) {
      int productionNumber = productionEntry.getKey();

      TreeMap<IPlayer, IResourceManager> numberProduction =
          productionDictionary.get(productionNumber);
      if (numberProduction == null) {
        numberProduction = new TreeMap<IPlayer, IResourceManager>();
        productionDictionary.put(productionNumber, numberProduction);
      }

      IResourceManager playerProduction = numberProduction.get(player);
      if (playerProduction == null) {
        playerProduction = new ResourceManager();
        numberProduction.put(player, playerProduction);
      }

      IResourceManager productionAtNumber = productionEntry.getValue();
      operation.accept(playerProduction, productionAtNumber);
    }
  }
}
