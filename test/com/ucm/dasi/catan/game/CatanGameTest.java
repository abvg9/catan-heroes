package com.ucm.dasi.catan.game;

import static org.junit.Assert.assertSame;

import org.junit.Test;

import com.ucm.dasi.catan.board.CatanBoard;
import com.ucm.dasi.catan.board.ICatanBoard;
import com.ucm.dasi.catan.board.connection.BoardConnection;
import com.ucm.dasi.catan.board.connection.ConnectionType;
import com.ucm.dasi.catan.board.element.IBoardElement;
import com.ucm.dasi.catan.board.exception.InvalidBoardDimensionsException;
import com.ucm.dasi.catan.board.exception.InvalidBoardElementException;
import com.ucm.dasi.catan.board.structure.BoardStructure;
import com.ucm.dasi.catan.board.structure.StructureType;
import com.ucm.dasi.catan.board.terrain.BoardTerrain;
import com.ucm.dasi.catan.board.terrain.TerrainType;
import com.ucm.dasi.catan.exception.NonNullInputException;
import com.ucm.dasi.catan.exception.NonVoidCollectionException;
import com.ucm.dasi.catan.player.IPlayer;
import com.ucm.dasi.catan.player.Player;
import com.ucm.dasi.catan.warehouse.Warehouse;
import com.ucm.dasi.catan.warehouse.exception.NegativeNumberException;

public class CatanGameTest {

    private ICatanBoard buildStandardBoard() throws InvalidBoardDimensionsException, InvalidBoardElementException {
	IBoardElement[][] elements = {
		{ new BoardStructure(null, StructureType.None), new BoardConnection(null, ConnectionType.Void),
			new BoardStructure(null, StructureType.None), },
		{ new BoardConnection(null, ConnectionType.Void), new BoardTerrain(0, TerrainType.None),
			new BoardConnection(null, ConnectionType.Void), },
		{ new BoardStructure(null, StructureType.None), new BoardConnection(null, ConnectionType.Void),
			new BoardStructure(null, StructureType.None), }, };

	return new CatanBoard(3, 3, elements);
    }

    @Test(expected = NonNullInputException.class)
    public void itMustNotBuildAGameWithACollectionOfNullPlayers() throws NonNullInputException,
	    NonVoidCollectionException, InvalidBoardDimensionsException, InvalidBoardElementException {

	IPlayer[] players = { null };
	ICatanBoard board = buildStandardBoard();
	new CatanGame<ICatanBoard>(board, players);
    }

    @Test(expected = NonNullInputException.class)
    public void itMustNotBuildAGameWithANullBoard()
	    throws NonNullInputException, NonVoidCollectionException, NegativeNumberException {
	IPlayer[] players = { new Player(0, new Warehouse()) };
	new CatanGame<ICatanBoard>(null, players);
    }

    @Test(expected = NonNullInputException.class)
    public void itMustNotBuildAGameWithANullCollectionOfPlayers() throws NonNullInputException,
	    NonVoidCollectionException, InvalidBoardDimensionsException, InvalidBoardElementException {

	IPlayer[] players = null;
	ICatanBoard board = buildStandardBoard();
	new CatanGame<ICatanBoard>(board, players);
    }

    @Test(expected = NonVoidCollectionException.class)
    public void itMustNotBuildAGameWithAVoidCollectionOfPlayers() throws NonNullInputException,
	    NonVoidCollectionException, InvalidBoardDimensionsException, InvalidBoardElementException {

	IPlayer[] players = {};
	ICatanBoard board = buildStandardBoard();
	new CatanGame<ICatanBoard>(board, players);
    }

    @Test
    public void itMustReturnTheActivePlayer() throws NonNullInputException, NonVoidCollectionException,
	    NegativeNumberException, InvalidBoardDimensionsException, InvalidBoardElementException {

	IPlayer activePlayer = new Player(0, new Warehouse());
	IPlayer[] players = { activePlayer };
	ICatanBoard board = buildStandardBoard();
	CatanGame<ICatanBoard> game = new CatanGame<ICatanBoard>(board, players);

	assertSame(activePlayer, game.getActivePlayer());
    }

    @Test
    public void itMustReturnTheBoard() throws NonNullInputException, NonVoidCollectionException,
	    NegativeNumberException, InvalidBoardDimensionsException, InvalidBoardElementException {

	IPlayer[] players = { new Player(0, new Warehouse()) };
	ICatanBoard board = buildStandardBoard();
	CatanGame<ICatanBoard> game = new CatanGame<ICatanBoard>(board, players);

	assertSame(board, game.getBoard());
    }

    @Test
    public void itMustReturnTheStoredPlayersCollection() throws NonNullInputException, NonVoidCollectionException,
	    NegativeNumberException, InvalidBoardDimensionsException, InvalidBoardElementException {

	IPlayer[] players = { new Player(0, new Warehouse()) };
	ICatanBoard board = buildStandardBoard();
	CatanGame<ICatanBoard> game = new CatanGame<ICatanBoard>(board, players);

	assertSame(players, game.getPlayers());
    }
}
