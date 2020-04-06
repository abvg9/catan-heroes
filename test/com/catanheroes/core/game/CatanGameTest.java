package com.catanheroes.core.game;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.catanheroes.core.board.CatanBoard;
import com.catanheroes.core.board.ICatanBoard;
import com.catanheroes.core.board.connection.BoardConnection;
import com.catanheroes.core.board.connection.ConnectionType;
import com.catanheroes.core.board.connection.IBoardConnection;
import com.catanheroes.core.board.element.IBoardElement;
import com.catanheroes.core.board.exception.InvalidBoardDimensionsException;
import com.catanheroes.core.board.exception.InvalidBoardElementException;
import com.catanheroes.core.board.structure.BoardStructure;
import com.catanheroes.core.board.structure.IBoardStructure;
import com.catanheroes.core.board.structure.StructureType;
import com.catanheroes.core.board.terrain.BoardTerrain;
import com.catanheroes.core.board.terrain.IBoardTerrain;
import com.catanheroes.core.board.terrain.TerrainType;
import com.catanheroes.core.exception.NonNullInputException;
import com.catanheroes.core.exception.NonVoidCollectionException;
import com.catanheroes.core.player.IPlayer;
import com.catanheroes.core.player.Player;
import com.catanheroes.core.resource.ResourceManager;
import com.catanheroes.core.resource.provider.DefaultTerrainProductionProvider;
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
        () -> new CatanGame(board, players, 10, GameState.NORMAL, 0, false));
  }

  @DisplayName("It must not build a game with a null board")
  @Tag(value = "CatanGame")
  @Test
  public void itMustNotBuildAGameWithANullBoard()
      throws NonNullInputException, NonVoidCollectionException {
    IPlayer[] players = {new Player(0, new ResourceManager())};
    assertThrows(
        NonNullInputException.class,
        () -> new CatanGame(null, players, 10, GameState.NORMAL, 0, false));
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
        () -> new CatanGame(board, players, 10, GameState.NORMAL, 0, false));
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
        () -> new CatanGame(board, players, 10, GameState.NORMAL, 0, false));
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
    CatanGame game = new CatanGame(board, players, 10, GameState.NORMAL, 0, false);

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
    CatanGame game = new CatanGame(board, players, 10, GameState.NORMAL, 0, false);

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
    CatanGame game = new CatanGame(board, players, 10, GameState.NORMAL, 0, false);

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
    CatanGame game = new CatanGame(board, players, 10, GameState.NORMAL, 0, turnStarted);

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

    return new CatanBoard(3, 3, elements, new DefaultTerrainProductionProvider());
  }

  private IBoardConnection buildVoidConnection() {
    return new BoardConnection(null, new ResourceManager(), ConnectionType.VOID);
  }
}
