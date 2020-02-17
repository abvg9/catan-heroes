package com.ucm.dasi.catan.board;

public class CatanTerrain implements ICatanTerrain {

	protected int productionNumber;
	
	protected TerrainType type;
	
	public CatanTerrain(int productionNumber, TerrainType type) {
		this.productionNumber = productionNumber;
		this.type = type;
	}
	
	public int getProductionNumber() {
		return productionNumber;
	}
	
	public TerrainType getType() {
		return type;
	}
}
