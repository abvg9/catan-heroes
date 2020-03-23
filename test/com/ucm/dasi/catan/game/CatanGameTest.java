package com.ucm.dasi.catan.game;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

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

	@Test
	public void itMustNotBuildAGameWithACollectionOfNullPlayers() throws NonNullInputException,
			NonVoidCollectionException, InvalidBoardDimensionsException, InvalidBoardElementException {

		IPlayer[] players = { null };
		ICatanBoard board = buildStandardBoard();
		assertThrows(NonNullInputException.class, () -> new CatanGame<ICatanBoard>(board, players, 0, false));
	}

	@Test
	public void itMustNotBuildAGameWithANullBoard()
			throws NonNullInputException, NonVoidCollectionException, NegativeNumberException {
		IPlayer[] players = { new Player(0, new ResourceManager()) };
		assertThrows(NonNullInputException.class, () -> new CatanGame<ICatanBoard>(null, players, 0, false));
	}

	@Test
	public void itMustNotBuildAGameWithANullCollectionOfPlayers() throws NonNullInputException,
			NonVoidCollectionException, InvalidBoardDimensionsException, InvalidBoardElementException {

		IPlayer[] players = null;
		ICatanBoard board = buildStandardBoard();
		assertThrows(NonNullInputException.class, () -> new CatanGame<ICatanBoard>(board, players, 0, false));
	}

	@Test
	public void itMustNotBuildAGameWithAVoidCollectionOfPlayers() throws NonNullInputException,
			NonVoidCollectionException, InvalidBoardDimensionsException, InvalidBoardElementException {

		IPlayer[] players = {};
		ICatanBoard board = buildStandardBoard();
		assertThrows(NonVoidCollectionException.class, () -> new CatanGame<ICatanBoard>(board, players, 0, false));
	}

	@Test
	public void itMustReturnTheActivePlayer() throws NonNullInputException, NonVoidCollectionException,
			NegativeNumberException, InvalidBoardDimensionsException, InvalidBoardElementException {

		IPlayer activePlayer = new Player(0, new ResourceManager());
		IPlayer[] players = { activePlayer };
		ICatanBoard board = buildStandardBoard();
		CatanGame<ICatanBoard> game = new CatanGame<ICatanBoard>(board, players, 0, false);

		assertSame(activePlayer, game.getActivePlayer());
	}

	@Test
	public void itMustReturnTheBoard() throws NonNullInputException, NonVoidCollectionException,
			NegativeNumberException, InvalidBoardDimensionsException, InvalidBoardElementException {

		IPlayer[] players = { new Player(0, new ResourceManager()) };
		ICatanBoard board = buildStandardBoard();
		CatanGame<ICatanBoard> game = new CatanGame<ICatanBoard>(board, players, 0, false);

		assertSame(board, game.getBoard());
	}

	@Test
	public void itMustReturnTheStoredPlayersCollection() throws NonNullInputException, NonVoidCollectionException,
			NegativeNumberException, InvalidBoardDimensionsException, InvalidBoardElementException {

		IPlayer[] players = { new Player(0, new ResourceManager()) };
		ICatanBoard board = buildStandardBoard();
		CatanGame<ICatanBoard> game = new CatanGame<ICatanBoard>(board, players, 0, false);

		assertSame(players, game.getPlayers());
	}

	@Test
	public void itMustReturnTheTurnStartedAttribute() throws NegativeNumberException, InvalidBoardDimensionsException,
			InvalidBoardElementException, NonNullInputException, NonVoidCollectionException {

		IPlayer[] players = { new Player(0, new ResourceManager()) };
		ICatanBoard board = buildStandardBoard();
		boolean turnStarted = true;
		CatanGame<ICatanBoard> game = new CatanGame<ICatanBoard>(board, players, 0, turnStarted);

		assertSame(turnStarted, game.isTurnStarted());
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
