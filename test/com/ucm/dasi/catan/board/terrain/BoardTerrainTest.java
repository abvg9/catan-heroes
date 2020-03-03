package com.ucm.dasi.catan.board.terrain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.ucm.dasi.catan.board.BoardElementType;

public class BoardTerrainTest {

	@Test
	public void itMustReturnTheRightElementType() {
		BoardTerrain terrain = new BoardTerrain(1, TerrainType.None);
		
		assertEquals(BoardElementType.Terrain, terrain.getElementType());
	}
	
	@Test
	public void itMustGetItsProductionNumber() {
		int productionNumber = 1;
		BoardTerrain terrain = new BoardTerrain(productionNumber, TerrainType.None);
		
		assertEquals(productionNumber, terrain.getProductionNumber());
	}
	
	@Test
	public void itMustGetItsStructureType() {
		TerrainType type = TerrainType.None;
		BoardTerrain terrain = new BoardTerrain(1, type);
		
		assertEquals(type, terrain.getType());
	}
	
}
