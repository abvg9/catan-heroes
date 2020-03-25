package com.ucm.dasi.catan.board;

import static org.junit.jupiter.api.Assertions.assertSame;

import com.ucm.dasi.catan.board.connection.BoardConnection;
import com.ucm.dasi.catan.board.connection.ConnectionType;
import com.ucm.dasi.catan.board.connection.IBoardConnection;
import com.ucm.dasi.catan.board.element.IBoardElement;
import com.ucm.dasi.catan.board.exception.InvalidBoardDimensionsException;
import com.ucm.dasi.catan.board.exception.InvalidBoardElementException;
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
import com.ucm.dasi.catan.resource.provider.TerrainProductionProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class CatanEditableBoardTest {

  @DisplayName("it must build a road")
  @Tag("CatanEditableBoard")
  @Test
  public void itMustBuildARoad()
      throws NegativeNumberException, InvalidBoardDimensionsException,
          InvalidBoardElementException {

    IPlayer player = new Player(0, new ResourceManager());

    CatanEditableBoard board = buildStandardBoard(player);

    int requestX = 3;
    int requestY = 2;

    board.build(
        new BoardConnection(player, new ResourceManager(), ConnectionType.Road),
        requestX,
        requestY);
    IBoardElement elementBuilt = board.get(requestX, requestY);

    assertSame(BoardElementType.Connection, elementBuilt.getElementType());
    assertSame(ConnectionType.Road, ((IBoardConnection) elementBuilt).getType());
  }

  @DisplayName("it must build a settlement")
  @Tag("CatanEditableBoard")
  @Test
  public void itMustBuildASettlement()
      throws NegativeNumberException, InvalidBoardDimensionsException,
          InvalidBoardElementException {

    IPlayer player = new Player(0, new ResourceManager());

    CatanEditableBoard board = buildStandardBoard(player);

    int requestX = 2;
    int requestY = 2;

    board.build(
        new BoardStructure(player, new ResourceManager(), StructureType.Settlement),
        requestX,
        requestY);
    IBoardElement elementBuilt = board.get(requestX, requestY);

    assertSame(BoardElementType.Structure, elementBuilt.getElementType());
    assertSame(StructureType.Settlement, ((IBoardStructure) elementBuilt).getType());
  }

  @DisplayName("it must upgrade a settlement into a city")
  @Tag("CatanEditableBoard")
  @Test
  public void itMustUpgradeASettlementIntoACity()
      throws NegativeNumberException, InvalidBoardDimensionsException,
          InvalidBoardElementException {

    IPlayer player = new Player(0, new ResourceManager());

    CatanEditableBoard board = buildStandardBoard(player);

    int requestX = 2;
    int requestY = 2;

    board.build(
        new BoardStructure(player, new ResourceManager(), StructureType.Settlement),
        requestX,
        requestY);
    board.upgrade(
        new BoardStructure(player, new ResourceManager(), StructureType.City), requestX, requestY);

    IBoardElement elementBuilt = board.get(requestX, requestY);

    assertSame(BoardElementType.Structure, elementBuilt.getElementType());
    assertSame(StructureType.City, ((IBoardStructure) elementBuilt).getType());
  }

  private IBoardTerrain buildMountainTerrain() {
    return new BoardTerrain(6, TerrainType.Mountains);
  }

  private IBoardStructure buildNoneStructure() {
    return new BoardStructure(null, new ResourceManager(), StructureType.None);
  }

  private IBoardTerrain buildNoneTerrain() {
    return new BoardTerrain(0, TerrainType.None);
  }

  private IBoardConnection buildPlayerConnection(IPlayer player) {
    return new BoardConnection(player, new ResourceManager(), ConnectionType.Road);
  }

  private CatanEditableBoard buildStandardBoard(IPlayer player1)
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
    };

    return new CatanEditableBoard(5, 5, elements, TerrainProductionProvider.buildDefaultProvider());
  }

  private IBoardConnection buildVoidConnection() {
    return new BoardConnection(null, new ResourceManager(), ConnectionType.Void);
  }
}
