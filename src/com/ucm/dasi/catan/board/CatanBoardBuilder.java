package com.ucm.dasi.catan.board;

import java.util.Collection;
import java.util.Iterator;

import com.ucm.dasi.catan.board.exception.InvalidBoardDimensionsException;

public class CatanBoardBuilder implements ICatanBoardBuilder {
	
	protected int height;
	
	protected ICatanTerrain[][] matrix;
	
	protected int width;
	
	public CatanBoardBuilder(int height, int width, Collection<ICatanTerrain> terrains) throws InvalidBoardDimensionsException {
		
		if (width * height != terrains.size()) {
			throw new InvalidBoardDimensionsException();
		}
		
		this.height = height;
		matrix = new ICatanTerrain[width][height];
		this.width = width;
		
		Iterator<ICatanTerrain> terrainIterator = terrains.iterator();
		
		for (int i = 0; i < width; ++i) {
			for (int j = 0; j < height; ++j) {
				matrix[i][j] = terrainIterator.next();
			}
		}
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public ICatanTerrain[][] getMatrix() {
		return matrix;
	}

	@Override
	public int getWidth() {
		return width;
	}
}
