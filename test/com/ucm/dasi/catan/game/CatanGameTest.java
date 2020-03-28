package com.ucm.dasi.catan.game;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.ucm.dasi.catan.board.CatanBoard;
import com.ucm.dasi.catan.board.ICatanBoard;
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
import com.ucm.dasi.catan.exception.NonNullInputException;
import com.ucm.dasi.catan.exception.NonVoidCollectionException;
import com.ucm.dasi.catan.player.IPlayer;
import com.ucm.dasi.catan.player.Player;
import com.ucm.dasi.catan.resource.ResourceManager;
import com.ucm.dasi.catan.resource.provider.TerrainProductionProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class CatanGameTest {

  @DisplayName("It must not build a game with a collection of null players")
  @Tag(value = "CatanGame")
  @Test
  public void itMustNotBuildAGameWithACollectionOfNullPlayers()
      throws NonNullInputException, NonVoidCollectionException, InvalidBoardDimensionsException,
          InvalidBoardElementException {

    IPlayer[] players = {null};
    ICatanBoard board = buildStandardBoard();
    assertThrows(
        NonNullInputException.class,
        () -> new CatanGame<ICatanBoard>(board, players, GameState.NORMAL, 0, false));
  }

  @DisplayName("It must not build a game with a null board")
  @Tag(value = "CatanGame")
  @Test
  public void itMustNotBuildAGameWithANullBoard()
      throws NonNullInputException, NonVoidCollectionException {
    IPlayer[] players = {new Player(0, new ResourceManager())};
    assertThrows(
        NonNullInputException.class,
        () -> new CatanGame<ICatanBoard>(null, players, GameState.NORMAL, 0, false));
  }

  @DisplayName("It must not build a game with a null collection of players")
  @Tag(value = "CatanGame")
  @Test
  public void itMustNotBuildAGameWithANullCollectionOfPlayers()
      throws NonNullInputException, NonVoidCollectionException, InvalidBoardDimensionsException,
          InvalidBoardElementException {

    IPlayer[] players = null;
    ICatanBoard board = buildStandardBoard();
    assertThrows(
        NonNullInputException.class,
        () -> new CatanGame<ICatanBoard>(board, players, GameState.NORMAL, 0, false));
  }

  @DisplayName("It must not build a game with a void collection of players")
  @Tag(value = "CatanGame")
  @Test
  public void itMustNotBuildAGameWithAVoidCollectionOfPlayers()
      throws NonNullInputException, NonVoidCollectionException, InvalidBoardDimensionsException,
          InvalidBoardElementException {

    IPlayer[] players = {};
    ICatanBoard board = buildStandardBoard();
    assertThrows(
        NonVoidCollectionException.class,
        () -> new CatanGame<ICatanBoard>(board, players, GameState.NORMAL, 0, false));
  }

  @DisplayName("It must return the active player")
  @Tag(value = "CatanGame")
  @Test
  public void itMustReturnTheActivePlayer()
      throws NonNullInputException, NonVoidCollectionException, InvalidBoardDimensionsException,
          InvalidBoardElementException {

    IPlayer activePlayer = new Player(0, new ResourceManager());
    IPlayer[] players = {activePlayer};
    ICatanBoard board = buildStandardBoard();
    CatanGame<ICatanBoard> game =
        new CatanGame<ICatanBoard>(board, players, GameState.NORMAL, 0, false);

    assertSame(activePlayer, game.getActivePlayer());
  }

  @DisplayName("It must return the board")
  @Tag(value = "CatanGame")
  @Test
  public void itMustReturnTheBoard()
      throws NonNullInputException, NonVoidCollectionException, InvalidBoardDimensionsException,
          InvalidBoardElementException {

    IPlayer[] players = {new Player(0, new ResourceManager())};
    ICatanBoard board = buildStandardBoard();
    CatanGame<ICatanBoard> game =
        new CatanGame<ICatanBoard>(board, players, GameState.NORMAL, 0, false);

    assertSame(board, game.getBoard());
  }

  @DisplayName("It must return the stored players collection")
  @Tag(value = "CatanGame")
  @Test
  public void itMustReturnTheStoredPlayersCollection()
      throws NonNullInputException, NonVoidCollectionException, InvalidBoardDimensionsException,
          InvalidBoardElementException {

    IPlayer[] players = {new Player(0, new ResourceManager())};
    ICatanBoard board = buildStandardBoard();
    CatanGame<ICatanBoard> game =
        new CatanGame<ICatanBoard>(board, players, GameState.NORMAL, 0, false);

    assertSame(players, game.getPlayers());
  }

  @DisplayName("It must return the turn started attribute")
  @Tag(value = "CatanGame")
  @Test
  public void itMustReturnTheTurnStartedAttribute()
      throws InvalidBoardDimensionsException, InvalidBoardElementException, NonNullInputException,
          NonVoidCollectionException {

    IPlayer[] players = {new Player(0, new ResourceManager())};
    ICatanBoard board = buildStandardBoard();
    boolean turnStarted = true;
    CatanGame<ICatanBoard> game =
        new CatanGame<ICatanBoard>(board, players, GameState.NORMAL, 0, turnStarted);

    assertSame(turnStarted, game.isTurnStarted());
  }

  private IBoardStructure buildNoneStructure() {
    return new BoardStructure(null, new ResourceManager(), StructureType.NONE);
  }

  private IBoardTerrain buildNoneTerrain() {
    return new BoardTerrain(0, TerrainType.NONE);
  }

  private ICatanBoard buildStandardBoard()
      throws InvalidBoardDimensionsException, InvalidBoardElementException {
    IBoardElement[][] elements = {
      {
        buildNoneStructure(), buildVoidConnection(), buildNoneStructure(),
      },
      {
        buildVoidConnection(), buildNoneTerrain(), buildVoidConnection(),
      },
      {
        buildNoneStructure(), buildVoidConnection(), buildNoneStructure(),
      },
    };

    return new CatanBoard(3, 3, elements, TerrainProductionProvider.buildDefaultProvider());
  }

  private IBoardConnection buildVoidConnection() {
    return new BoardConnection(null, new ResourceManager(), ConnectionType.VOID);
  }
}
