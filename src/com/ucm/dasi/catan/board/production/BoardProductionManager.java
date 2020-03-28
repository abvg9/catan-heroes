package com.ucm.dasi.catan.board.production;

import com.ucm.dasi.catan.board.BoardElementType;
import com.ucm.dasi.catan.board.ICatanBoard;
import com.ucm.dasi.catan.board.element.IBoardElement;
import com.ucm.dasi.catan.board.group.StructureTerrainTypesPair;
import com.ucm.dasi.catan.board.structure.IBoardStructure;
import com.ucm.dasi.catan.board.terrain.IBoardTerrain;
import com.ucm.dasi.catan.board.terrain.TerrainType;
import com.ucm.dasi.catan.player.IPlayer;
import com.ucm.dasi.catan.resource.IResourceManager;
import com.ucm.dasi.catan.resource.IResourceStorage;
import com.ucm.dasi.catan.resource.ResourceManager;
import com.ucm.dasi.catan.resource.exception.NotEnoughtResourcesException;
import com.ucm.dasi.catan.resource.production.IResourceProduction;
import com.ucm.dasi.catan.resource.production.ResourceProduction;
import com.ucm.dasi.catan.resource.provider.ITerrainProductionProvider;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.function.BiConsumer;

public class BoardProductionManager implements IBoardProductionManager {

  private TreeMap<Integer, TreeMap<IPlayer, IResourceManager>> productionDictionary;

  protected ITerrainProductionProvider terrainProductionProvider;

  public BoardProductionManager(ITerrainProductionProvider terrainProductionProvider) {

    this.terrainProductionProvider = terrainProductionProvider;
  }

  public IResourceProduction getProduction(ICatanBoard board, int productionNumber) {

    if (!isInitialized()) {
      buildProductionDictionary(board);
    }

    TreeMap<IPlayer, IResourceManager> numberProduction =
        productionDictionary.get(productionNumber);

    if (numberProduction == null) {
      numberProduction = new TreeMap<IPlayer, IResourceManager>();
    }

    return new ResourceProduction(productionNumber, numberProduction);
  }

  public void syncProductionOnStructureBuilt(ICatanBoard board, int x, int y) {

    if (!isInitialized()) {
      return;
    }

    IBoardElement element = board.get(x, y);
    if (element.getElementType() != BoardElementType.STRUCTURE) {
      return;
    }
    IBoardStructure structure = (IBoardStructure) element;
    TreeMap<Integer, IResourceManager> production =
        getProductionOfStructure(board, structure, x, y);

    addProductionOfPlayer(structure.getOwner(), production);
  }

  public void syncProductionOnStructureUpgrade(
      ICatanBoard board, IBoardStructure oldStructure, int x, int y) {

    if (!isInitialized()) {
      return;
    }

    syncProductionOnStructureBuilt(board, x, y);

    TreeMap<Integer, IResourceManager> production =
        getProductionOfStructure(board, oldStructure, x, y);

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
      ICatanBoard board,
      TreeMap<Integer, TreeMap<IPlayer, IResourceManager>> innerMap,
      int x,
      int y) {

    IBoardElement element = board.get(x, y);

    if (element.getElementType() != BoardElementType.TERRAIN) {
      return;
    }

    IBoardTerrain terrain = (IBoardTerrain) element;
    if (terrain.getType() == TerrainType.NONE) {
      return;
    }

    analyzeStructureProduction(innerMap, terrain, getNWStructureOfTerrain(board, x, y));
    analyzeStructureProduction(innerMap, terrain, getNEStructureOfTerrain(board, x, y));
    analyzeStructureProduction(innerMap, terrain, getSWStructureOfTerrain(board, x, y));
    analyzeStructureProduction(innerMap, terrain, getSEStructureOfTerrain(board, x, y));
  }

  private void buildProductionDictionary(ICatanBoard board) {

    TreeMap<Integer, TreeMap<IPlayer, IResourceManager>> innerMap =
        new TreeMap<Integer, TreeMap<IPlayer, IResourceManager>>();

    for (int i = 0; i < board.getWidth(); ++i) {
      for (int j = 0; j < board.getHeight(); ++j) {
        analyzeTerrainProduction(board, innerMap, i, j);
      }
    }

    productionDictionary = innerMap;
  }

  private IBoardStructure getNWStructureOfTerrain(ICatanBoard board, int x, int y) {
    return (IBoardStructure) board.get(x - 1, y - 1);
  }

  private IBoardTerrain getNWTerrainOfStructure(ICatanBoard board, int x, int y) {
    return (x > 0 && y > 0) ? (IBoardTerrain) board.get(x - 1, y - 1) : null;
  }

  private IBoardStructure getNEStructureOfTerrain(ICatanBoard board, int x, int y) {
    return (IBoardStructure) board.get(x + 1, y - 1);
  }

  private IBoardTerrain getNETerrainOfStructure(ICatanBoard board, int x, int y) {
    return (x + 1 < board.getWidth() && y > 0) ? (IBoardTerrain) board.get(x + 1, y - 1) : null;
  }

  private TreeMap<Integer, IResourceManager> getProductionOfStructure(
      ICatanBoard board, IBoardStructure structure, int x, int y) {

    TreeMap<Integer, IResourceManager> productionMap = new TreeMap<Integer, IResourceManager>();

    addProductionOfStructureOverTerrain(
        productionMap, structure, getNWTerrainOfStructure(board, x, y));
    addProductionOfStructureOverTerrain(
        productionMap, structure, getNETerrainOfStructure(board, x, y));
    addProductionOfStructureOverTerrain(
        productionMap, structure, getSWTerrainOfStructure(board, x, y));
    addProductionOfStructureOverTerrain(
        productionMap, structure, getSETerrainOfStructure(board, x, y));

    return productionMap;
  }

  private IBoardStructure getSEStructureOfTerrain(ICatanBoard board, int x, int y) {
    return (IBoardStructure) board.get(x + 1, y + 1);
  }

  private IBoardTerrain getSETerrainOfStructure(ICatanBoard board, int x, int y) {
    return (x + 1 < board.getWidth() && y + 1 < board.getHeight())
        ? (IBoardTerrain) board.get(x + 1, y + 1)
        : null;
  }

  private IBoardStructure getSWStructureOfTerrain(ICatanBoard board, int x, int y) {
    return (IBoardStructure) board.get(x - 1, y + 1);
  }

  private IBoardTerrain getSWTerrainOfStructure(ICatanBoard board, int x, int y) {
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
