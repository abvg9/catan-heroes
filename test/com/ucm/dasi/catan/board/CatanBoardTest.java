package com.ucm.dasi.catan.board;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.ucm.dasi.catan.board.connection.BoardConnection;
import com.ucm.dasi.catan.board.connection.ConnectionType;
import com.ucm.dasi.catan.board.element.IBoardElement;
import com.ucm.dasi.catan.board.exception.InvalidBoardDimensionsException;
import com.ucm.dasi.catan.board.exception.InvalidBoardElementException;
import com.ucm.dasi.catan.board.structure.BoardStructure;
import com.ucm.dasi.catan.board.structure.StructureType;
import com.ucm.dasi.catan.board.terrain.BoardTerrain;
import com.ucm.dasi.catan.board.terrain.TerrainType;

public class CatanBoardTest {

    @Test
    public void itMustBeInitializable() throws InvalidBoardDimensionsException, InvalidBoardElementException {
	IBoardElement[][] elements = {
		{ new BoardStructure(null, StructureType.None), new BoardConnection(null, ConnectionType.Void),
			new BoardStructure(null, StructureType.None), },
		{ new BoardConnection(null, ConnectionType.Void), new BoardTerrain(0, TerrainType.None),
			new BoardConnection(null, ConnectionType.Void), },
		{ new BoardStructure(null, StructureType.None), new BoardConnection(null, ConnectionType.Void),
			new BoardStructure(null, StructureType.None), }, };
	CatanBoard board = new CatanBoard(3, 3, elements);

	assertNotEquals(null, board);
    }

    @Test
    public void itMustGetAnElementByItsPosition() throws InvalidBoardDimensionsException, InvalidBoardElementException {
	IBoardElement element = new BoardStructure(null, StructureType.None);
	IBoardElement[][] elements = {
		{ element, new BoardConnection(null, ConnectionType.Void),
			new BoardStructure(null, StructureType.None), },
		{ new BoardConnection(null, ConnectionType.Void), new BoardTerrain(0, TerrainType.None),
			new BoardConnection(null, ConnectionType.Void), },
		{ new BoardStructure(null, StructureType.None), new BoardConnection(null, ConnectionType.Void),
			new BoardStructure(null, StructureType.None), }, };
	CatanBoard board = new CatanBoard(3, 3, elements);

	assertEquals(element, board.get(0, 0));
    }

    @Test
    public void itMustGetATerrainByItsPosition() throws InvalidBoardDimensionsException, InvalidBoardElementException {
	IBoardElement element = new BoardTerrain(0, TerrainType.None);
	IBoardElement[][] elements = {
		{ new BoardStructure(null, StructureType.None), new BoardConnection(null, ConnectionType.Void),
			new BoardStructure(null, StructureType.None), },
		{ new BoardConnection(null, ConnectionType.Void), element,
			new BoardConnection(null, ConnectionType.Void), },
		{ new BoardStructure(null, StructureType.None), new BoardConnection(null, ConnectionType.Void),
			new BoardStructure(null, StructureType.None), }, };
	CatanBoard board = new CatanBoard(3, 3, elements);

	assertEquals(element, board.getTerrain(0, 0));
    }

    @Test
    public void itMustGetAnStructureByItsPosition()
	    throws InvalidBoardDimensionsException, InvalidBoardElementException {
	IBoardElement element = new BoardStructure(null, StructureType.None);
	IBoardElement[][] elements = {
		{ element, new BoardConnection(null, ConnectionType.Void),
			new BoardStructure(null, StructureType.None), },
		{ new BoardConnection(null, ConnectionType.Void), new BoardTerrain(0, TerrainType.None),
			new BoardConnection(null, ConnectionType.Void), },
		{ new BoardStructure(null, StructureType.None), new BoardConnection(null, ConnectionType.Void),
			new BoardStructure(null, StructureType.None), }, };
	CatanBoard board = new CatanBoard(3, 3, elements);

	assertEquals(element, board.getStructure(0, 0));
    }

    @Test()
    public void itMustFailIfAFalseConnectionIsProvided()
	    throws InvalidBoardDimensionsException, InvalidBoardElementException {
	
	IBoardElement[][] elements = {
		{ new BoardStructure(null, StructureType.None), new BoardStructure(null, StructureType.None),
			new BoardStructure(null, StructureType.None), },
		{ new BoardConnection(null, ConnectionType.Void), new BoardTerrain(0, TerrainType.None),
			new BoardConnection(null, ConnectionType.Void), },
		{ new BoardStructure(null, StructureType.None), new BoardConnection(null, ConnectionType.Void),
			new BoardStructure(null, StructureType.None), }, };
	
	assertThrows(InvalidBoardElementException.class, () -> new CatanBoard(3, 3, elements));
    }

    @Test()
    public void itMustFailIfAFalseTerrainIsProvided()
	    throws InvalidBoardDimensionsException, InvalidBoardElementException {
	IBoardElement[][] elements = {
		{ new BoardStructure(null, StructureType.None), new BoardConnection(null, ConnectionType.Void),
			new BoardStructure(null, StructureType.None), },
		{ new BoardConnection(null, ConnectionType.Void), new BoardConnection(null, ConnectionType.Void),
			new BoardConnection(null, ConnectionType.Void), },
		{ new BoardStructure(null, StructureType.None), new BoardConnection(null, ConnectionType.Void),
			new BoardStructure(null, StructureType.None), }, };
	
	assertThrows(InvalidBoardElementException.class, () -> new CatanBoard(3, 3, elements));
    }

    @Test()
    public void itMustFailIfAFalseStructureIsProvided()
	    throws InvalidBoardDimensionsException, InvalidBoardElementException {
	IBoardElement[][] elements = {
		{ new BoardTerrain(0, TerrainType.None), new BoardConnection(null, ConnectionType.Void),
			new BoardStructure(null, StructureType.None), },
		{ new BoardConnection(null, ConnectionType.Void), new BoardTerrain(0, TerrainType.None),
			new BoardConnection(null, ConnectionType.Void), },
		{ new BoardStructure(null, StructureType.None), new BoardConnection(null, ConnectionType.Void),
			new BoardStructure(null, StructureType.None), }, };
	
	assertThrows(InvalidBoardElementException.class, () -> new CatanBoard(3, 3, elements));
    }
}
