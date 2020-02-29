package com.ucm.dasi.catan.board;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BoardConnectionTest {

	@Test
	public void itMustReturnTheRightElementType() {
		BoardConnection connection = new BoardConnection(ConnectionType.Void);
		
		assertEquals(BoardElementType.Connection, connection.getElementType());
	}
	
	@Test
	public void itMustGetItsConnectionType() {
		ConnectionType type = ConnectionType.Void;
		BoardConnection connection = new BoardConnection(type);
		
		assertEquals(type, connection.getType());
	}
}
