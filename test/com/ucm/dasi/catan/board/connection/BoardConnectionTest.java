package com.ucm.dasi.catan.board.connection;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.ucm.dasi.catan.board.BoardElementType;
import com.ucm.dasi.catan.resource.ResourceManager;

public class BoardConnectionTest {

    @Test
    public void itMustReturnTheRightElementType() {
	BoardConnection connection = new BoardConnection(null, new ResourceManager(), ConnectionType.Void);

	assertEquals(BoardElementType.Connection, connection.getElementType());
    }

    @Test
    public void itMustGetItsConnectionType() {
	ConnectionType type = ConnectionType.Void;
	BoardConnection connection = new BoardConnection(null, new ResourceManager(), type);

	assertEquals(type, connection.getType());
    }
}
