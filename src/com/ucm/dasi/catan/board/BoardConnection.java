package com.ucm.dasi.catan.board;

public class BoardConnection implements IBoardConnection {

	protected ConnectionType type;
	
	public BoardConnection(ConnectionType type) {
		this.type = type;
	}
	
	public BoardElementType getElementType() {
		return BoardElementType.Connection;
	}

	@Override
	public ConnectionType getType() {
		return type;
	}
}
