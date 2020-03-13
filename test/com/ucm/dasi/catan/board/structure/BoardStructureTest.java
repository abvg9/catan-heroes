package com.ucm.dasi.catan.board.structure;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.ucm.dasi.catan.board.BoardElementType;
import com.ucm.dasi.catan.warehouse.Warehouse;

public class BoardStructureTest {

    @Test
    public void itMustReturnTheRightElementType() {
	BoardStructure connection = new BoardStructure(null, new Warehouse(), StructureType.None);

	assertEquals(BoardElementType.Structure, connection.getElementType());
    }

    @Test
    public void itMustGetItsStructureType() {
	StructureType type = StructureType.None;
	BoardStructure structure = new BoardStructure(null, new Warehouse(), type);

	assertEquals(type, structure.getType());
    }

}
