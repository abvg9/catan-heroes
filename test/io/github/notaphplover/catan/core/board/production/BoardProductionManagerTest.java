package io.github.notaphplover.catan.core.board.production;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.notaphplover.catan.core.board.CatanBoard;
import io.github.notaphplover.catan.core.board.connection.BoardConnection;
import io.github.notaphplover.catan.core.board.connection.ConnectionType;
import io.github.notaphplover.catan.core.board.connection.IBoardConnection;
import io.github.notaphplover.catan.core.board.element.IBoardElement;
import io.github.notaphplover.catan.core.board.exception.InvalidBoardDimensionsException;
import io.github.notaphplover.catan.core.board.exception.InvalidBoardElementException;
import io.github.notaphplover.catan.core.board.group.StructureTerrainTypesPair;
import io.github.notaphplover.catan.core.board.structure.BoardStructure;
import io.github.notaphplover.catan.core.board.structure.IBoardStructure;
import io.github.notaphplover.catan.core.board.structure.StructureType;
import io.github.notaphplover.catan.core.board.terrain.BoardTerrain;
import io.github.notaphplover.catan.core.board.terrain.IBoardTerrain;
import io.github.notaphplover.catan.core.board.terrain.TerrainType;
import io.github.notaphplover.catan.core.player.IPlayer;
import io.github.notaphplover.catan.core.player.Player;
import io.github.notaphplover.catan.core.resource.IResourceStorage;
import io.github.notaphplover.catan.core.resource.ResourceManager;
import io.github.notaphplover.catan.core.resource.ResourceStorage;
import io.github.notaphplover.catan.core.resource.production.IResourceProduction;
import io.github.notaphplover.catan.core.resource.provider.DefaultTerrainProductionProvider;
import io.github.notaphplover.catan.core.resource.provider.ITerrainProductionProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class BoardProductionManagerTest {

  @DisplayName("It must get a player's production")
  @Tag("CatanBoard")
  @Test
  public void itMustGetAPlayerProduction()
      throws InvalidBoardDimensionsException, InvalidBoardElementException {

    int targetProductionNumber = 3;
    IPlayer player = new Player(1, new ResourceManager());

    IBoardElement[][] elements = {
      {
        createNoneStructure(), createVoidConnection(), createSettlementStructure(player),
      },
      {
        createVoidConnection(),
        createMountainsTerrain(targetProductionNumber),
        createVoidConnection(),
      },
      {
        createNoneStructure(), createVoidConnection(), createNoneStructure(),
      },
    };

    ITerrainProductionProvider terrainProductionProvider = new DefaultTerrainProductionProvider();

    CatanBoard board = new CatanBoard(3, 3, elements, terrainProductionProvider);

    BoardProductionManager boardProductionManager =
        new BoardProductionManager(board, terrainProductionProvider);

    IResourceProduction production = boardProductionManager.getProduction(targetProductionNumber);

    assertEquals(
        terrainProductionProvider.getResourceManager(
            new StructureTerrainTypesPair(StructureType.SETTLEMENT, TerrainType.MOUNTAINS)),
        production.getProduction(player));
  }

  @DisplayName("it must sync the production after a structure build")
  @Tag("CatanEditableBoard")
  @Test
  public void itMustSyncTheProductionAfterAStructureBuild()
      throws InvalidBoardDimensionsException, InvalidBoardElementException {

    IPlayer player = new Player(0, new ResourceManager());

    ITerrainProductionProvider productionProvider = new DefaultTerrainProductionProvider();
    CatanBoard board = buildStandardBoard(player, productionProvider);

    BoardProductionManager boardProductionManager =
        new BoardProductionManager(board, productionProvider);

    int productionNumber = 6;

    int requestX = 2;
    int requestY = 2;

    IResourceStorage productionBefore =
        boardProductionManager.getProduction(productionNumber).getProduction(player);

    board.build(
        new BoardStructure(player, new ResourceManager(), StructureType.SETTLEMENT),
        requestX,
        requestY);

    boardProductionManager.syncProductionOnStructureBuilt(requestX, requestY);

    IResourceStorage standardSettlementAtMountainProduction =
        productionProvider.getResourceManager(
            new StructureTerrainTypesPair(StructureType.SETTLEMENT, TerrainType.MOUNTAINS));
    IResourceStorage productionAfter =
        boardProductionManager.getProduction(productionNumber).getProduction(player);

    assertEquals(new ResourceStorage(), productionBefore);
    assertEquals(standardSettlementAtMountainProduction, productionAfter);
  }

  @DisplayName("it must sync the production after a structure upgrade")
  @Tag("CatanEditableBoard")
  @Test
  public void itMustSyncTheProductionAfterAStructureUpdate()
      throws InvalidBoardDimensionsException, InvalidBoardElementException {

    IPlayer player = new Player(0, new ResourceManager());

    ITerrainProductionProvider productionProvider = new DefaultTerrainProductionProvider();
    CatanBoard board = buildStandardBoard(player, productionProvider);

    BoardProductionManager boardProductionManager =
        new BoardProductionManager(board, productionProvider);

    int productionNumber = 6;

    int requestX = 2;
    int requestY = 2;

    IResourceStorage productionBefore =
        boardProductionManager.getProduction(productionNumber).getProduction(player);

    board.build(
        new BoardStructure(player, new ResourceManager(), StructureType.SETTLEMENT),
        requestX,
        requestY);

    boardProductionManager.syncProductionOnStructureBuilt(requestX, requestY);

    board.upgrade(
        new BoardStructure(player, new ResourceManager(), StructureType.CITY), requestX, requestY);

    boardProductionManager.syncProductionOnStructureUpgrade(
        new BoardStructure(player, new ResourceManager(), StructureType.SETTLEMENT),
        requestX,
        requestY);

    IResourceStorage standardCityAtMountainProduction =
        productionProvider.getResourceManager(
            new StructureTerrainTypesPair(StructureType.CITY, TerrainType.MOUNTAINS));
    IResourceStorage productionAfter =
        boardProductionManager.getProduction(productionNumber).getProduction(player);

    assertEquals(new ResourceStorage(), productionBefore);
    assertEquals(standardCityAtMountainProduction, productionAfter);
  }

  private CatanBoard buildStandardBoard(
      IPlayer player1, ITerrainProductionProvider productionProvider)
      throws InvalidBoardDimensionsException, InvalidBoardElementException {
    IBoardElement[][] elements = {
      {
        createNoneStructure(),
        createVoidConnection(),
        createNoneStructure(),
        createVoidConnection(),
        createNoneStructure(),
      },
      {
        createVoidConnection(),
        createNoneTerrain(),
        createPlayerConnection(player1),
        createMountainsTerrain(6),
        createVoidConnection(),
      },
      {
        createNoneStructure(),
        createVoidConnection(),
        createNoneStructure(),
        createVoidConnection(),
        createNoneStructure(),
      },
      {
        createVoidConnection(),
        createNoneTerrain(),
        createVoidConnection(),
        createNoneTerrain(),
        createVoidConnection(),
      },
      {
        createNoneStructure(),
        createVoidConnection(),
        createNoneStructure(),
        createVoidConnection(),
        createNoneStructure(),
      },
    };

    return new CatanBoard(5, 5, elements, productionProvider);
  }

  private IBoardTerrain createMountainsTerrain(int productionNumber) {
    return new BoardTerrain(productionNumber, TerrainType.MOUNTAINS);
  }

  private IBoardStructure createNoneStructure() {
    return new BoardStructure(null, new ResourceManager(), StructureType.NONE);
  }

  private IBoardTerrain createNoneTerrain() {
    return new BoardTerrain(0, TerrainType.NONE);
  }

  private IBoardConnection createPlayerConnection(IPlayer player) {
    return new BoardConnection(player, new ResourceManager(), ConnectionType.ROAD);
  }

  private IBoardStructure createSettlementStructure(IPlayer owner) {
    return new BoardStructure(owner, new ResourceManager(), StructureType.SETTLEMENT);
  }

  private IBoardConnection createVoidConnection() {
    return new BoardConnection(null, new ResourceManager(), ConnectionType.VOID);
  }
}
