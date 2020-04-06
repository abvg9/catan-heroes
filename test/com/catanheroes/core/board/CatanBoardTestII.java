package com.catanheroes.core.board;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;

import com.catanheroes.core.board.connection.BoardConnection;
import com.catanheroes.core.board.connection.ConnectionType;
import com.catanheroes.core.board.connection.IBoardConnection;
import com.catanheroes.core.board.element.IBoardElement;
import com.catanheroes.core.board.exception.InvalidBoardDimensionsException;
import com.catanheroes.core.board.exception.InvalidBoardElementException;
import com.catanheroes.core.board.production.BoardProductionManager;
import com.catanheroes.core.board.production.IBoardProductionManager;
import com.catanheroes.core.board.structure.BoardStructure;
import com.catanheroes.core.board.structure.IBoardStructure;
import com.catanheroes.core.board.structure.StructureType;
import com.catanheroes.core.board.terrain.BoardTerrain;
import com.catanheroes.core.board.terrain.IBoardTerrain;
import com.catanheroes.core.board.terrain.TerrainType;
import com.catanheroes.core.player.IPlayer;
import com.catanheroes.core.player.Player;
import com.catanheroes.core.resource.ResourceManager;
import com.catanheroes.core.resource.exception.NegativeNumberException;
import com.catanheroes.core.resource.provider.DefaultTerrainProductionProvider;
import com.catanheroes.core.resource.provider.ITerrainProductionProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class CatanBoardTestII {

  @DisplayName("it must build a road")
  @Tag("CatanBoard")
  @Test
  public void itMustBuildARoad()
      throws NegativeNumberException, InvalidBoardDimensionsException,
          InvalidBoardElementException {

    IPlayer player = new Player(0, new ResourceManager());

    CatanBoard board = buildStandardBoard(player, new DefaultTerrainProductionProvider());

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
  @Tag("CatanBoard")
  @Test
  public void itMustBuildASettlement()
      throws NegativeNumberException, InvalidBoardDimensionsException,
          InvalidBoardElementException {

    IPlayer player = new Player(0, new ResourceManager());

    CatanBoard board = buildStandardBoard(player, new DefaultTerrainProductionProvider());

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
  @Tag("CatanBoard")
  @Test
  public void itMustCallProductionManagerI()
      throws InvalidBoardDimensionsException, InvalidBoardElementException {

    IPlayer player = new Player(0, new ResourceManager());

    ITerrainProductionProvider productionProvider = new DefaultTerrainProductionProvider();
    CatanBoardForTest board = buildStandardBoard(player, productionProvider);

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
  @Tag("CatanBoard")
  @Test
  public void itMustCallProductionManagerII()
      throws InvalidBoardDimensionsException, InvalidBoardElementException {

    IPlayer player = new Player(0, new ResourceManager());

    ITerrainProductionProvider productionProvider = new DefaultTerrainProductionProvider();
    CatanBoardForTest board = buildStandardBoard(player, productionProvider);

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
  @Tag("CatanBoard")
  @Test
  public void itMustUpgradeASettlementIntoACity()
      throws NegativeNumberException, InvalidBoardDimensionsException,
          InvalidBoardElementException {

    IPlayer player = new Player(0, new ResourceManager());

    CatanBoard board = buildStandardBoard(player, new DefaultTerrainProductionProvider());

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

  private CatanBoardForTest buildStandardBoard(
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

    return new CatanBoardForTest(5, 5, elements, productionProvider);
  }

  private IBoardConnection buildVoidConnection() {
    return new BoardConnection(null, new ResourceManager(), ConnectionType.VOID);
  }
}
