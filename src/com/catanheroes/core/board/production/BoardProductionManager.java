package com.catanheroes.core.board.production;

import com.catanheroes.core.board.BoardElementType;
import com.catanheroes.core.board.ICatanBoard;
import com.catanheroes.core.board.element.IBoardElement;
import com.catanheroes.core.board.exception.InvalidBoardElementException;
import com.catanheroes.core.board.group.StructureTerrainTypesPair;
import com.catanheroes.core.board.structure.IBoardStructure;
import com.catanheroes.core.board.terrain.IBoardTerrain;
import com.catanheroes.core.board.terrain.TerrainType;
import com.catanheroes.core.player.IPlayer;
import com.catanheroes.core.resource.IResourceManager;
import com.catanheroes.core.resource.IResourceStorage;
import com.catanheroes.core.resource.ResourceManager;
import com.catanheroes.core.resource.exception.NotEnoughtResourcesException;
import com.catanheroes.core.resource.production.IResourceProduction;
import com.catanheroes.core.resource.production.ResourceProduction;
import com.catanheroes.core.resource.provider.ITerrainProductionProvider;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.function.BiConsumer;

public class BoardProductionManager implements IBoardProductionManager {

  private ICatanBoard board;

  private TreeMap<Integer, TreeMap<IPlayer, IResourceManager>> productionDictionary;

  private ITerrainProductionProvider terrainProductionProvider;

  public BoardProductionManager(
      ICatanBoard board, ITerrainProductionProvider terrainProductionProvider) {

    this.board = board;
    this.terrainProductionProvider = terrainProductionProvider;
  }

  public IResourceProduction getProduction(int productionNumber) {

    if (!isInitialized()) {
      buildProductionDictionary();
    }

    TreeMap<IPlayer, IResourceManager> numberProduction =
        productionDictionary.get(productionNumber);

    if (numberProduction == null) {
      numberProduction = new TreeMap<IPlayer, IResourceManager>();
    }

    return new ResourceProduction(productionNumber, numberProduction);
  }

  public void syncProductionOnStructureBuilt(int x, int y) throws InvalidBoardElementException {

    if (!isInitialized()) {
      return;
    }

    IBoardElement element = board.get(x, y);
    if (element.getElementType() != BoardElementType.STRUCTURE) {
      throw new InvalidBoardElementException(element.getElementType());
    }
    IBoardStructure structure = (IBoardStructure) element;
    TreeMap<Integer, IResourceManager> production = getProductionOfStructure(structure, x, y);

    addProductionOfPlayer(structure.getOwner(), production);
  }

  public void syncProductionOnStructureUpgrade(IBoardStructure oldStructure, int x, int y)
      throws InvalidBoardElementException {

    if (!isInitialized()) {
      return;
    }

    syncProductionOnStructureBuilt(x, y);

    TreeMap<Integer, IResourceManager> production = getProductionOfStructure(oldStructure, x, y);

    removeProductionOfPlayer(oldStructure.getOwner(), production);
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

    IResourceStorage structureProductionOverTerrain =
        terrainProductionProvider.getResourceManager(
            new StructureTerrainTypesPair(structure.getType(), terrain.getType()));
    numberProduction.add(structureProductionOverTerrain);
  }

  private void addProductionOfPlayer(
      IPlayer player, TreeMap<Integer, IResourceManager> production) {

    transformProductionOfPlayer(player, production, (total, diff) -> total.add(diff));
  }

  private void analyzeStructureProduction(
      TreeMap<Integer, TreeMap<IPlayer, IResourceManager>> innerMap,
      IBoardTerrain terrain,
      IBoardStructure structure) {

    if (structure.getOwner() == null) {
      return;
    }

    IResourceStorage production =
        terrainProductionProvider.getResourceManager(
            new StructureTerrainTypesPair(structure.getType(), terrain.getType()));
    insertProduction(innerMap, terrain.getProductionNumber(), production, structure.getOwner());
  }

  private void analyzeTerrainProduction(
      TreeMap<Integer, TreeMap<IPlayer, IResourceManager>> innerMap, int x, int y) {

    IBoardElement element = board.get(x, y);

    if (element.getElementType() != BoardElementType.TERRAIN) {
      return;
    }

    IBoardTerrain terrain = (IBoardTerrain) element;
    if (terrain.getType() == TerrainType.NONE) {
      return;
    }

    analyzeStructureProduction(innerMap, terrain, getNWStructureOfTerrain(x, y));
    analyzeStructureProduction(innerMap, terrain, getNEStructureOfTerrain(x, y));
    analyzeStructureProduction(innerMap, terrain, getSWStructureOfTerrain(x, y));
    analyzeStructureProduction(innerMap, terrain, getSEStructureOfTerrain(x, y));
  }

  private void buildProductionDictionary() {

    TreeMap<Integer, TreeMap<IPlayer, IResourceManager>> innerMap =
        new TreeMap<Integer, TreeMap<IPlayer, IResourceManager>>();

    for (int i = 0; i < board.getWidth(); ++i) {
      for (int j = 0; j < board.getHeight(); ++j) {
        analyzeTerrainProduction(innerMap, i, j);
      }
    }

    productionDictionary = innerMap;
  }

  private IBoardStructure getNWStructureOfTerrain(int x, int y) {
    return (IBoardStructure) board.get(x - 1, y - 1);
  }

  private IBoardTerrain getNWTerrainOfStructure(int x, int y) {
    return (x > 0 && y > 0) ? (IBoardTerrain) board.get(x - 1, y - 1) : null;
  }

  private IBoardStructure getNEStructureOfTerrain(int x, int y) {
    return (IBoardStructure) board.get(x + 1, y - 1);
  }

  private IBoardTerrain getNETerrainOfStructure(int x, int y) {
    return (x + 1 < board.getWidth() && y > 0) ? (IBoardTerrain) board.get(x + 1, y - 1) : null;
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

  private IBoardStructure getSEStructureOfTerrain(int x, int y) {
    return (IBoardStructure) board.get(x + 1, y + 1);
  }

  private IBoardTerrain getSETerrainOfStructure(int x, int y) {
    return (x + 1 < board.getWidth() && y + 1 < board.getHeight())
        ? (IBoardTerrain) board.get(x + 1, y + 1)
        : null;
  }

  private IBoardStructure getSWStructureOfTerrain(int x, int y) {
    return (IBoardStructure) board.get(x - 1, y + 1);
  }

  private IBoardTerrain getSWTerrainOfStructure(int x, int y) {
    return (x > 0 && y + 1 < board.getHeight()) ? (IBoardTerrain) board.get(x - 1, y + 1) : null;
  }

  private void insertProduction(
      TreeMap<Integer, TreeMap<IPlayer, IResourceManager>> innerMap,
      int productionNumber,
      IResourceStorage production,
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

  private boolean isInitialized() {
    return productionDictionary != null;
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
