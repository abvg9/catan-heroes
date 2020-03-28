package com.ucm.dasi.catan.board;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;

import com.ucm.dasi.catan.board.connection.BoardConnection;
import com.ucm.dasi.catan.board.connection.ConnectionType;
import com.ucm.dasi.catan.board.connection.IBoardConnection;
import com.ucm.dasi.catan.board.element.IBoardElement;
import com.ucm.dasi.catan.board.exception.InvalidBoardDimensionsException;
import com.ucm.dasi.catan.board.exception.InvalidBoardElementException;
import com.ucm.dasi.catan.board.production.BoardProductionManager;
import com.ucm.dasi.catan.board.production.IBoardProductionManager;
import com.ucm.dasi.catan.board.structure.BoardStructure;
import com.ucm.dasi.catan.board.structure.IBoardStructure;
import com.ucm.dasi.catan.board.structure.StructureType;
import com.ucm.dasi.catan.board.terrain.BoardTerrain;
import com.ucm.dasi.catan.board.terrain.IBoardTerrain;
import com.ucm.dasi.catan.board.terrain.TerrainType;
import com.ucm.dasi.catan.player.IPlayer;
import com.ucm.dasi.catan.player.Player;
import com.ucm.dasi.catan.resource.ResourceManager;
import com.ucm.dasi.catan.resource.exception.NegativeNumberException;
import com.ucm.dasi.catan.resource.provider.DefaultTerrainProductionProvider;
import com.ucm.dasi.catan.resource.provider.ITerrainProductionProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class CatanEditableBoardTest {

  @DisplayName("it must build a road")
  @Tag("CatanEditableBoard")
  @Test
  public void itMustBuildARoad()
      throws NegativeNumberException, InvalidBoardDimensionsException,
          InvalidBoardElementException {

    IPlayer player = new Player(0, new ResourceManager());

    CatanEditableBoard board = buildStandardBoard(player, new DefaultTerrainProductionProvider());

    int requestX = 3;
    int requestY = 2;

    board.build(
        new BoardConnection(player, new ResourceManager(), ConnectionType.ROAD),
        requestX,
        requestY);
    IBoardElement elementBuilt = board.get(requestX, requestY);

    assertSame(BoardElementType.CONNECTION, elementBuilt.getElementType());
    assertSame(ConnectionType.ROAD, ((IBoardConnection) elementBuilt).getType());
  }

  @DisplayName("it must build a settlement")
  @Tag("CatanEditableBoard")
  @Test
  public void itMustBuildASettlement()
      throws NegativeNumberException, InvalidBoardDimensionsException,
          InvalidBoardElementException {

    IPlayer player = new Player(0, new ResourceManager());

    CatanEditableBoard board = buildStandardBoard(player, new DefaultTerrainProductionProvider());

    int requestX = 2;
    int requestY = 2;

    board.build(
        new BoardStructure(player, new ResourceManager(), StructureType.SETTLEMENT),
        requestX,
        requestY);
    IBoardElement elementBuilt = board.get(requestX, requestY);

    assertSame(BoardElementType.STRUCTURE, elementBuilt.getElementType());
    assertSame(StructureType.SETTLEMENT, ((IBoardStructure) elementBuilt).getType());
  }

  @DisplayName("it must call the production manager to sync the production after a structure build")
  @Tag("CatanEditableBoard")
  @Test
  public void itMustCallProductionManagerI()
      throws InvalidBoardDimensionsException, InvalidBoardElementException {

    IPlayer player = new Player(0, new ResourceManager());

    ITerrainProductionProvider productionProvider = new DefaultTerrainProductionProvider();
    CatanEditableBoardForTest board = buildStandardBoard(player, productionProvider);

    IBoardProductionManager productionManager =
        Mockito.spy(new BoardProductionManager(board, productionProvider));

    board.setProductionManager(productionManager);

    int productionNumber = 6;

    int requestX = 2;
    int requestY = 2;

    board.getProduction(productionNumber).getProduction(player);

    board.build(
        new BoardStructure(player, new ResourceManager(), StructureType.SETTLEMENT),
        requestX,
        requestY);

    verify(productionManager).syncProductionOnStructureBuilt(requestX, requestY);
  }

  @DisplayName(
      "it must call the production manager to sync the production after a structure upgrade")
  @Tag("CatanEditableBoard")
  @Test
  public void itMustCallProductionManagerII()
      throws InvalidBoardDimensionsException, InvalidBoardElementException {

    IPlayer player = new Player(0, new ResourceManager());

    ITerrainProductionProvider productionProvider = new DefaultTerrainProductionProvider();
    CatanEditableBoardForTest board = buildStandardBoard(player, productionProvider);

    IBoardProductionManager productionManager =
        Mockito.spy(new BoardProductionManager(board, productionProvider));

    board.setProductionManager(productionManager);

    int productionNumber = 6;

    int requestX = 2;
    int requestY = 2;

    board.getProduction(productionNumber).getProduction(player);

    IBoardStructure settlement =
        new BoardStructure(player, new ResourceManager(), StructureType.SETTLEMENT);

    board.build(settlement, requestX, requestY);

    board.upgrade(
        new BoardStructure(player, new ResourceManager(), StructureType.CITY), requestX, requestY);

    board.getProduction(productionNumber).getProduction(player);

    verify(productionManager).syncProductionOnStructureUpgrade(settlement, requestX, requestY);
  }

  @DisplayName("it must upgrade a settlement into a city")
  @Tag("CatanEditableBoard")
  @Test
  public void itMustUpgradeASettlementIntoACity()
      throws NegativeNumberException, InvalidBoardDimensionsException,
          InvalidBoardElementException {

    IPlayer player = new Player(0, new ResourceManager());

    CatanEditableBoard board = buildStandardBoard(player, new DefaultTerrainProductionProvider());

    int requestX = 2;
    int requestY = 2;

    board.build(
        new BoardStructure(player, new ResourceManager(), StructureType.SETTLEMENT),
        requestX,
        requestY);
    board.upgrade(
        new BoardStructure(player, new ResourceManager(), StructureType.CITY), requestX, requestY);

    IBoardElement elementBuilt = board.get(requestX, requestY);

    assertSame(BoardElementType.STRUCTURE, elementBuilt.getElementType());
    assertSame(StructureType.CITY, ((IBoardStructure) elementBuilt).getType());
  }

  private IBoardTerrain buildMountainTerrain() {
    return new BoardTerrain(6, TerrainType.MOUNTAINS);
  }

  private IBoardStructure buildNoneStructure() {
    return new BoardStructure(null, new ResourceManager(), StructureType.NONE);
  }

  private IBoardTerrain buildNoneTerrain() {
    return new BoardTerrain(0, TerrainType.NONE);
  }

  private IBoardConnection buildPlayerConnection(IPlayer player) {
    return new BoardConnection(player, new ResourceManager(), ConnectionType.ROAD);
  }

  private CatanEditableBoardForTest buildStandardBoard(
      IPlayer player1, ITerrainProductionProvider productionProvider)
      throws InvalidBoardDimensionsException, InvalidBoardElementException {
    IBoardElement[][] elements = {
      {
        buildNoneStructure(),
        buildVoidConnection(),
        buildNoneStructure(),
        buildVoidConnection(),
        buildNoneStructure(),
      },
      {
        buildVoidConnection(),
        buildNoneTerrain(),
        buildPlayerConnection(player1),
        buildMountainTerrain(),
        buildVoidConnection(),
      },
      {
        buildNoneStructure(),
        buildVoidConnection(),
        buildNoneStructure(),
        buildVoidConnection(),
        buildNoneStructure(),
      },
      {
        buildVoidConnection(),
        buildNoneTerrain(),
        buildVoidConnection(),
        buildNoneTerrain(),
        buildVoidConnection(),
      },
      {
        buildNoneStructure(),
        buildVoidConnection(),
        buildNoneStructure(),
        buildVoidConnection(),
        buildNoneStructure(),
      },
    };

    return new CatanEditableBoardForTest(5, 5, elements, productionProvider);
  }

  private IBoardConnection buildVoidConnection() {
    return new BoardConnection(null, new ResourceManager(), ConnectionType.VOID);
  }
}
