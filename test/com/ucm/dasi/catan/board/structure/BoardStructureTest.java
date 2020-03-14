package com.ucm.dasi.catan.board.structure;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.ucm.dasi.catan.board.BoardElementType;
import com.ucm.dasi.catan.resource.ResourceManager;

public class BoardStructureTest {

    @Test
    public void itMustReturnTheRightElementType() {
	BoardStructure connection = new BoardStructure(null, new ResourceManager(), StructureType.None);

	assertEquals(BoardElementType.Structure, connection.getElementType());
    }

    @Test
    public void itMustGetItsStructureType() {
	StructureType type = StructureType.None;
	BoardStructure structure = new BoardStructure(null, new ResourceManager(), type);

	assertEquals(type, structure.getType());
    }

}
