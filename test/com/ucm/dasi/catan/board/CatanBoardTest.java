package com.ucm.dasi.catan.board;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import com.ucm.dasi.catan.board.exception.InvalidBoardDimensionsException;
import com.ucm.dasi.catan.board.exception.InvalidBoardElementException;

public class CatanBoardTest {

	@Test
	public void itMustBeInitializable() throws InvalidBoardDimensionsException, InvalidBoardElementException {
		IBoardElement[][] elements = {
			{
				new BoardStructure(StructureType.None), new BoardConnection(ConnectionType.Void), new BoardStructure(StructureType.None),
			},
			{
				new BoardConnection(ConnectionType.Void), new BoardTerrain(0, TerrainType.None), new BoardConnection(ConnectionType.Void),
			},
			{
				new BoardStructure(StructureType.None), new BoardConnection(ConnectionType.Void), new BoardStructure(StructureType.None),
			},
		};
		CatanBoard board = new CatanBoard(3, 3, elements);
		
		assertNotEquals(null, board);
	}
	
	@Test
	public void itMustGetAnElementByItsPosition() throws InvalidBoardDimensionsException, InvalidBoardElementException {
		IBoardElement element = new BoardStructure(StructureType.None);
		IBoardElement[][] elements = {
			{
				element, new BoardConnection(ConnectionType.Void), new BoardStructure(StructureType.None),
			},
			{
				new BoardConnection(ConnectionType.Void), new BoardTerrain(0, TerrainType.None), new BoardConnection(ConnectionType.Void),
			},
			{
				new BoardStructure(StructureType.None), new BoardConnection(ConnectionType.Void), new BoardStructure(StructureType.None),
			},
		};
		CatanBoard board = new CatanBoard(3, 3, elements);
		
		assertEquals(element, board.get(0, 0));
	}
	
	@Test
	public void itMustGetATerrainByItsPosition() throws InvalidBoardDimensionsException, InvalidBoardElementException {
		IBoardElement element = new BoardTerrain(0, TerrainType.None);
		IBoardElement[][] elements = {
			{
				new BoardStructure(StructureType.None), new BoardConnection(ConnectionType.Void), new BoardStructure(StructureType.None),
			},
			{
				new BoardConnection(ConnectionType.Void), element, new BoardConnection(ConnectionType.Void),
			},
			{
				new BoardStructure(StructureType.None), new BoardConnection(ConnectionType.Void), new BoardStructure(StructureType.None),
			},
		};
		CatanBoard board = new CatanBoard(3, 3, elements);
		
		assertEquals(element, board.getTerrain(0, 0));
	}
	
	@Test
	public void itMustGetAnStructureByItsPosition() throws InvalidBoardDimensionsException, InvalidBoardElementException {
		IBoardElement element = new BoardStructure(StructureType.None);
		IBoardElement[][] elements = {
			{
				element, new BoardConnection(ConnectionType.Void), new BoardStructure(StructureType.None),
			},
			{
				new BoardConnection(ConnectionType.Void), new BoardTerrain(0, TerrainType.None), new BoardConnection(ConnectionType.Void),
			},
			{
				new BoardStructure(StructureType.None), new BoardConnection(ConnectionType.Void), new BoardStructure(StructureType.None),
			},
		};
		CatanBoard board = new CatanBoard(3, 3, elements);
		
		assertEquals(element, board.getStructure(0, 0));
	}
	
	@Test(expected = InvalidBoardElementException.class)
	public void itMustFailIfAFalseConnectionIsProvided() throws InvalidBoardDimensionsException, InvalidBoardElementException {
		IBoardElement[][] elements = {
			{
				new BoardStructure(StructureType.None), new BoardStructure(StructureType.None), new BoardStructure(StructureType.None),
			},
			{
				new BoardConnection(ConnectionType.Void), new BoardTerrain(0, TerrainType.None), new BoardConnection(ConnectionType.Void),
			},
			{
				new BoardStructure(StructureType.None), new BoardConnection(ConnectionType.Void), new BoardStructure(StructureType.None),
			},
		};
		new CatanBoard(3, 3, elements);
	}
	
	@Test(expected = InvalidBoardElementException.class)
	public void itMustFailIfAFalseTerrainIsProvided() throws InvalidBoardDimensionsException, InvalidBoardElementException {
		IBoardElement[][] elements = {
			{
				new BoardStructure(StructureType.None), new BoardConnection(ConnectionType.Void), new BoardStructure(StructureType.None),
			},
			{
				new BoardConnection(ConnectionType.Void), new BoardConnection(ConnectionType.Void), new BoardConnection(ConnectionType.Void),
			},
			{
				new BoardStructure(StructureType.None), new BoardConnection(ConnectionType.Void), new BoardStructure(StructureType.None),
			},
		};
		new CatanBoard(3, 3, elements);
	}
	
	@Test(expected = InvalidBoardElementException.class)
	public void itMustFailIfAFalseStructureIsProvided() throws InvalidBoardDimensionsException, InvalidBoardElementException {
		IBoardElement[][] elements = {
			{
				new BoardTerrain(0, TerrainType.None), new BoardConnection(ConnectionType.Void), new BoardStructure(StructureType.None),
			},
			{
				new BoardConnection(ConnectionType.Void), new BoardTerrain(0, TerrainType.None), new BoardConnection(ConnectionType.Void),
			},
			{
				new BoardStructure(StructureType.None), new BoardConnection(ConnectionType.Void), new BoardStructure(StructureType.None),
			},
		};
		new CatanBoard(3, 3, elements);
	}
}
