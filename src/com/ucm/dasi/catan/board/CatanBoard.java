package com.ucm.dasi.catan.board;

import com.ucm.dasi.catan.board.exception.InvalidBoardSelectionException;

public class CatanBoard implements ICatanBoard {

	protected int height;
	
	protected ICatanTerrain[][] terrains;

	protected int width;
	
	public CatanBoard(ICatanBoardBuilder builder) {
		height = builder.getHeight();
		terrains = builder.getMatrix();
		width = builder.getWidth();
	}

	@Override
	public ICatanTerrain get(int x, int y) throws InvalidBoardSelectionException {
		if (x < 0 || y < 0 || x >= width || y >= height) {
			throw new InvalidBoardSelectionException();
		}
		return terrains[x][y];
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public int getWidth() {
		return width;
	}
}
