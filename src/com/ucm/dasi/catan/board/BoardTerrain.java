package com.ucm.dasi.catan.board;

public class BoardTerrain implements IBoardTerrain {

	protected int productionNumber;
	
	protected TerrainType type;
	
	public BoardTerrain(int productionNumber, TerrainType type) {
		this.productionNumber = productionNumber;
		this.type = type;
	}
	
	public BoardElementType getElementType() {
		return BoardElementType.Terrain;
	}
	
	public int getProductionNumber() {
		return productionNumber;
	}
	
	public TerrainType getType() {
		return type;
	}
}
