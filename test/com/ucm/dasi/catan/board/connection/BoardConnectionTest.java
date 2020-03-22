package com.ucm.dasi.catan.board.connection;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.ucm.dasi.catan.board.BoardElementType;

public class BoardConnectionTest {

    @Test
    public void itMustReturnTheRightElementType() {
	BoardConnection connection = new BoardConnection(null, ConnectionType.Void);

	assertEquals(BoardElementType.Connection, connection.getElementType());
    }

    @Test
    public void itMustGetItsConnectionType() {
	ConnectionType type = ConnectionType.Void;
	BoardConnection connection = new BoardConnection(null, type);

	assertEquals(type, connection.getType());
    }
}
