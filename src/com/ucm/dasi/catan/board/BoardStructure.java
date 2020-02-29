package com.ucm.dasi.catan.board;

public class BoardStructure implements IBoardStructure {
	
	private StructureType type;
	
	public BoardStructure(StructureType type) {
		this.type = type;
	}
	
	@Override
	public BoardElementType getElementType() {
		return BoardElementType.Structure;
	}

	@Override
	public StructureType getType() {
		return type;
	}

}
