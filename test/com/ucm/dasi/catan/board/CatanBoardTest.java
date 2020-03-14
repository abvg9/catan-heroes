package com.ucm.dasi.catan.board;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

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
import com.ucm.dasi.catan.resource.ResourceManager;

public class CatanBoardTest {

    @Test
    public void itMustBeInitializable() throws InvalidBoardDimensionsException, InvalidBoardElementException {

	IBoardElement[][] elements = { { createNoneStructure(), createVoidConnection(), createNoneStructure(), },
		{ createVoidConnection(), createNoneTerrain(), createVoidConnection(), },
		{ createNoneStructure(), createVoidConnection(), createNoneStructure(), }, };

	CatanBoard board = new CatanBoard(3, 3, elements);

	assertNotEquals(null, board);
    }

    @Test
    public void itMustGetAnElementByItsPosition() throws InvalidBoardDimensionsException, InvalidBoardElementException {

	IBoardElement element = createNoneStructure();
	IBoardElement[][] elements = { { element, createVoidConnection(), createNoneStructure(), },
		{ createVoidConnection(), createNoneTerrain(), createVoidConnection(), },
		{ createNoneStructure(), createVoidConnection(), createNoneStructure(), }, };

	CatanBoard board = new CatanBoard(3, 3, elements);

	assertEquals(element, board.get(0, 0));
    }

    @Test
    public void itMustGetATerrainByItsPosition() throws InvalidBoardDimensionsException, InvalidBoardElementException {

	IBoardElement element = createNoneTerrain();
	IBoardElement[][] elements = { { createNoneStructure(), createVoidConnection(), createNoneStructure(), },
		{ createVoidConnection(), element, createVoidConnection(), },
		{ createNoneStructure(), createVoidConnection(), createNoneStructure(), }, };

	CatanBoard board = new CatanBoard(3, 3, elements);

	assertEquals(element, board.getTerrain(0, 0));
    }

    @Test
    public void itMustGetAnStructureByItsPosition()
	    throws InvalidBoardDimensionsException, InvalidBoardElementException {

	IBoardElement element = createNoneStructure();
	IBoardElement[][] elements = { { element, createVoidConnection(), createNoneStructure(), },
		{ createVoidConnection(), createNoneTerrain(), createVoidConnection(), },
		{ createNoneStructure(), createVoidConnection(), createNoneStructure(), }, };

	CatanBoard board = new CatanBoard(3, 3, elements);

	assertEquals(element, board.getStructure(0, 0));
    }

    @Test(expected = InvalidBoardElementException.class)
    public void itMustFailIfAFalseConnectionIsProvided()
	    throws InvalidBoardDimensionsException, InvalidBoardElementException {

	IBoardElement[][] elements = { { createNoneStructure(), createNoneStructure(), createNoneStructure(), },
		{ createVoidConnection(), createNoneTerrain(), createVoidConnection(), },
		{ createNoneStructure(), createVoidConnection(), createNoneStructure(), }, };

	new CatanBoard(3, 3, elements);
    }

    @Test(expected = InvalidBoardElementException.class)
    public void itMustFailIfAFalseTerrainIsProvided()
	    throws InvalidBoardDimensionsException, InvalidBoardElementException {

	IBoardElement[][] elements = { { createNoneStructure(), createVoidConnection(), createNoneStructure(), },
		{ createVoidConnection(), createVoidConnection(), createVoidConnection(), },
		{ createNoneStructure(), createVoidConnection(), createNoneStructure(), }, };

	new CatanBoard(3, 3, elements);
    }

    @Test(expected = InvalidBoardElementException.class)
    public void itMustFailIfAFalseStructureIsProvided()
	    throws InvalidBoardDimensionsException, InvalidBoardElementException {

	IBoardElement[][] elements = { { createNoneTerrain(), createVoidConnection(), createNoneStructure(), },
		{ createVoidConnection(), createNoneTerrain(), createVoidConnection(), },
		{ createNoneStructure(), createVoidConnection(), createNoneStructure(), }, };

	new CatanBoard(3, 3, elements);
    }

    private IBoardTerrain createNoneTerrain() {
	return new BoardTerrain(0, TerrainType.None);
    }

    private IBoardStructure createNoneStructure() {
	return new BoardStructure(null, new ResourceManager(), StructureType.None);
    }

    private IBoardConnection createVoidConnection() {
	return new BoardConnection(null, new ResourceManager(), ConnectionType.Void);
    }
}
