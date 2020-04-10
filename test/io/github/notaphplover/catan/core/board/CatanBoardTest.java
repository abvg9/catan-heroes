package io.github.notaphplover.catan.core.board;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

import io.github.notaphplover.catan.core.board.connection.BoardConnection;
import io.github.notaphplover.catan.core.board.connection.ConnectionType;
import io.github.notaphplover.catan.core.board.connection.IBoardConnection;
import io.github.notaphplover.catan.core.board.element.IBoardElement;
import io.github.notaphplover.catan.core.board.exception.InvalidBoardDimensionsException;
import io.github.notaphplover.catan.core.board.exception.InvalidBoardElementException;
import io.github.notaphplover.catan.core.board.production.BoardProductionManager;
import io.github.notaphplover.catan.core.board.production.IBoardProductionManager;
import io.github.notaphplover.catan.core.board.structure.BoardStructure;
import io.github.notaphplover.catan.core.board.structure.IBoardStructure;
import io.github.notaphplover.catan.core.board.structure.StructureType;
import io.github.notaphplover.catan.core.board.terrain.BoardTerrain;
import io.github.notaphplover.catan.core.board.terrain.IBoardTerrain;
import io.github.notaphplover.catan.core.board.terrain.TerrainType;
import io.github.notaphplover.catan.core.player.IPlayer;
import io.github.notaphplover.catan.core.player.Player;
import io.github.notaphplover.catan.core.resource.ResourceManager;
import io.github.notaphplover.catan.core.resource.provider.DefaultTerrainProductionProvider;
import io.github.notaphplover.catan.core.resource.provider.ITerrainProductionProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class CatanBoardTest {

  @DisplayName("It must be initializable")
  @Tag(value = "CatanBoard")
  @Test
  public void itMustBeInitializable()
      throws InvalidBoardDimensionsException, InvalidBoardElementException {

    IBoardElement[][] elements = {
      {
        createNoneStructure(), createVoidConnection(), createNoneStructure(),
      },
      {
        createVoidConnection(), createNoneTerrain(), createVoidConnection(),
      },
      {
        createNoneStructure(), createVoidConnection(), createNoneStructure(),
      },
    };

    CatanBoard board = new CatanBoard(3, 3, elements, new DefaultTerrainProductionProvider());

    assertNotEquals(null, board);
  }

  @DisplayName("It must call its manager to get a player's production")
  @Tag("CatanBoard")
  @Test
  public void itMustCallItsManagerToGetAPlayerProduction()
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

    CatanBoardForTest board = new CatanBoardForTest(3, 3, elements, terrainProductionProvider);
    IBoardProductionManager productionManager =
        Mockito.spy(new BoardProductionManager(board, terrainProductionProvider));
    board.setProductionManager(productionManager);

    board.getProduction(targetProductionNumber);

    verify(productionManager).getProduction(targetProductionNumber);
  }

  @DisplayName("It must get an element by its position")
  @Tag(value = "CatanBoard")
  @Test
  public void itMustGetAnElementByItsPosition()
      throws InvalidBoardDimensionsException, InvalidBoardElementException {

    IBoardElement element = createNoneStructure();
    IBoardElement[][] elements = {
      {
        element, createVoidConnection(), createNoneStructure(),
      },
      {
        createVoidConnection(), createNoneTerrain(), createVoidConnection(),
      },
      {
        createNoneStructure(), createVoidConnection(), createNoneStructure(),
      },
    };

    CatanBoard board = new CatanBoard(3, 3, elements, new DefaultTerrainProductionProvider());

    assertEquals(element, board.get(0, 0));
  }

  @DisplayName("It must fail if a false connection is provided")
  @Tag(value = "CatanBoard")
  @Test
  public void itMustFailIfAFalseConnectionIsProvided()
      throws InvalidBoardDimensionsException, InvalidBoardElementException {

    IBoardElement[][] elements = {
      {
        createNoneStructure(), createNoneStructure(), createNoneStructure(),
      },
      {
        createVoidConnection(), createNoneTerrain(), createVoidConnection(),
      },
      {
        createNoneStructure(), createVoidConnection(), createNoneStructure(),
      },
    };

    assertThrows(
        InvalidBoardElementException.class,
        () -> new CatanBoard(3, 3, elements, new DefaultTerrainProductionProvider()));
  }

  @DisplayName("It must fail if a false terrain is provided")
  @Tag(value = "CatanBoard")
  @Test
  public void itMustFailIfAFalseTerrainIsProvided()
      throws InvalidBoardDimensionsException, InvalidBoardElementException {

    IBoardElement[][] elements = {
      {
        createNoneStructure(), createVoidConnection(), createNoneStructure(),
      },
      {
        createVoidConnection(), createVoidConnection(), createVoidConnection(),
      },
      {
        createNoneStructure(), createVoidConnection(), createNoneStructure(),
      },
    };

    assertThrows(
        InvalidBoardElementException.class,
        () -> new CatanBoard(3, 3, elements, new DefaultTerrainProductionProvider()));
  }

  @DisplayName("It must fail if a false structure is provided")
  @Tag(value = "CatanBoard")
  @Test
  public void itMustFailIfAFalseStructureIsProvided()
      throws InvalidBoardDimensionsException, InvalidBoardElementException {

    IBoardElement[][] elements = {
      {
        createNoneTerrain(), createVoidConnection(), createNoneStructure(),
      },
      {
        createVoidConnection(), createNoneTerrain(), createVoidConnection(),
      },
      {
        createNoneStructure(), createVoidConnection(), createNoneStructure(),
      },
    };

    assertThrows(
        InvalidBoardElementException.class,
        () -> new CatanBoard(3, 3, elements, new DefaultTerrainProductionProvider()));
  }

  private IBoardTerrain createNoneTerrain() {
    return new BoardTerrain(0, TerrainType.NONE);
  }

  private IBoardTerrain createMountainsTerrain(int productionNumber) {
    return new BoardTerrain(productionNumber, TerrainType.MOUNTAINS);
  }

  private IBoardStructure createNoneStructure() {
    return new BoardStructure(null, new ResourceManager(), StructureType.NONE);
  }

  private IBoardStructure createSettlementStructure(IPlayer owner) {
    return new BoardStructure(owner, new ResourceManager(), StructureType.SETTLEMENT);
  }

  private IBoardConnection createVoidConnection() {
    return new BoardConnection(null, new ResourceManager(), ConnectionType.VOID);
  }
}
