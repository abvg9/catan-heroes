package com.ucm.dasi.catan.board;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BoardStructureTest {

	@Test
	public void itMustReturnTheRightElementType() {
		BoardStructure connection = new BoardStructure(StructureType.None);
		
		assertEquals(BoardElementType.Structure, connection.getElementType());
	}
	
	@Test
	public void itMustGetItsStructureType() {
		StructureType type = StructureType.None;
		BoardStructure structure = new BoardStructure(type);
		
		assertEquals(type, structure.getType());
	}
	
}
