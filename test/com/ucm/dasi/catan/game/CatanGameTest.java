package com.ucm.dasi.catan.game;

import static org.junit.Assert.assertSame;

import org.junit.Test;

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
import com.ucm.dasi.catan.resource.exception.NegativeNumberException;

public class CatanGameTest {

    @Test(expected = NonNullInputException.class)
    public void itMustNotBuildAGameWithACollectionOfNullPlayers() throws NonNullInputException,
	    NonVoidCollectionException, InvalidBoardDimensionsException, InvalidBoardElementException {

	IPlayer[] players = { null };
	ICatanBoard board = buildStandardBoard();
	new CatanGame<ICatanBoard>(false, board, players);
    }

    @Test(expected = NonNullInputException.class)
    public void itMustNotBuildAGameWithANullBoard()
	    throws NonNullInputException, NonVoidCollectionException, NegativeNumberException {
	IPlayer[] players = { new Player(0, new ResourceManager()) };
	new CatanGame<ICatanBoard>(false, null, players);
    }

    @Test(expected = NonNullInputException.class)
    public void itMustNotBuildAGameWithANullCollectionOfPlayers() throws NonNullInputException,
	    NonVoidCollectionException, InvalidBoardDimensionsException, InvalidBoardElementException {

	IPlayer[] players = null;
	ICatanBoard board = buildStandardBoard();
	new CatanGame<ICatanBoard>(false, board, players);
    }

    @Test(expected = NonVoidCollectionException.class)
    public void itMustNotBuildAGameWithAVoidCollectionOfPlayers() throws NonNullInputException,
	    NonVoidCollectionException, InvalidBoardDimensionsException, InvalidBoardElementException {

	IPlayer[] players = {};
	ICatanBoard board = buildStandardBoard();
	new CatanGame<ICatanBoard>(false, board, players);
    }

    @Test
    public void itMustReturnTheActivePlayer() throws NonNullInputException, NonVoidCollectionException,
	    NegativeNumberException, InvalidBoardDimensionsException, InvalidBoardElementException {

	IPlayer activePlayer = new Player(0, new ResourceManager());
	IPlayer[] players = { activePlayer };
	ICatanBoard board = buildStandardBoard();
	CatanGame<ICatanBoard> game = new CatanGame<ICatanBoard>(false, board, players);

	assertSame(activePlayer, game.getActivePlayer());
    }

    @Test
    public void itMustReturnTheBoard() throws NonNullInputException, NonVoidCollectionException,
	    NegativeNumberException, InvalidBoardDimensionsException, InvalidBoardElementException {

	IPlayer[] players = { new Player(0, new ResourceManager()) };
	ICatanBoard board = buildStandardBoard();
	CatanGame<ICatanBoard> game = new CatanGame<ICatanBoard>(false, board, players);

	assertSame(board, game.getBoard());
    }

    @Test
    public void itMustReturnTheStoredPlayersCollection() throws NonNullInputException, NonVoidCollectionException,
	    NegativeNumberException, InvalidBoardDimensionsException, InvalidBoardElementException {

	IPlayer[] players = { new Player(0, new ResourceManager()) };
	ICatanBoard board = buildStandardBoard();
	CatanGame<ICatanBoard> game = new CatanGame<ICatanBoard>(false, board, players);

	assertSame(players, game.getPlayers());
    }

    private IBoardStructure buildNoneStructure() {
	return new BoardStructure(null, new ResourceManager(), StructureType.None);
    }

    private IBoardTerrain buildNoneTerrain() {
	return new BoardTerrain(0, TerrainType.None);
    }

    private ICatanBoard buildStandardBoard() throws InvalidBoardDimensionsException, InvalidBoardElementException {
	IBoardElement[][] elements = { { buildNoneStructure(), buildVoidConnection(), buildNoneStructure(), },
		{ buildVoidConnection(), buildNoneTerrain(), buildVoidConnection(), },
		{ buildNoneStructure(), buildVoidConnection(), buildNoneStructure(), }, };

	return new CatanBoard(3, 3, elements);
    }

    private IBoardConnection buildVoidConnection() {
	return new BoardConnection(null, new ResourceManager(), ConnectionType.Void);
    }
}
