package com.ucm.dasi.catan.board;

import com.ucm.dasi.catan.board.exception.InvalidBoardDimensionsException;
import com.ucm.dasi.catan.board.exception.InvalidBoardElementException;

public class CatanBoard implements ICatanBoard {
	
	protected int terrainWidth;
	
	protected int terrainHeight;
	
	protected int width;
	
	protected int height;
	
	protected IBoardElement[][] elements;
	
	public CatanBoard(int width, int height, IBoardElement[][] elements) throws InvalidBoardDimensionsException, InvalidBoardElementException {
		setDimensions(width, height);
		setElements(elements);
	}
	
	@Override
	public IBoardElement get(int x, int y) {
		return this.elements[x][y];
	}
	
	@Override
	public IBoardConnection getEastConnection(int x, int y) {
		return (IBoardConnection) this.elements[2 * x + 2][2 * y + 1];
	}
	
	@Override
	public IBoardConnection getNorthConnection(int x, int y) {
		return (IBoardConnection) this.elements[2 * x + 1][2 * y];
	}
	
	@Override
	public IBoardConnection getSouthConnection(int x, int y) {
		return (IBoardConnection) this.elements[2 * x + 1][2 * y + 2];
	}
	
	@Override
	public IBoardConnection getWestConnection(int x, int y) {
		return (IBoardConnection) this.elements[2 * x][2 * y + 1];
	}
	
	@Override
	public IBoardTerrain getTerrain(int x, int y) {
		return (IBoardTerrain) this.elements[2 * x + 1][2 * y + 1];
	}
	
	@Override
	public IBoardStructure getStructure(int x, int y) {
		return (IBoardStructure) this.elements[2 * x][2 * y];
	}
	
	private boolean checkElementType(IBoardElement element, int x, int y) {
		if (1 == (x + y) % 2) {
			return element.getElementType() == BoardElementType.Connection;
		}
		
		if (x % 2 == 0) {
			return element.getElementType() == BoardElementType.Structure;
		}
		
		return element.getElementType() == BoardElementType.Terrain;
	}
	
	private void setDimensions(int width, int height) throws InvalidBoardDimensionsException {
		if (width % 2 == 0 || height % 2 == 0) {
			throw new InvalidBoardDimensionsException("Expected an odd number");
		}
		
		terrainWidth = width / 2;
		terrainHeight = height / 2;
		
		this.width = width;
		this.height = height;
	}
	
	private void setElements(IBoardElement[][] elements) throws InvalidBoardDimensionsException, InvalidBoardElementException {
		if (elements.length != width) {
			throw new InvalidBoardDimensionsException("Expected a width of " + width);
		}
		
		this.elements = new IBoardElement[width][height];
		
		for (int i = 0; i < width; ++i) {
			if (elements[i].length != height) {
				throw new InvalidBoardDimensionsException("Expected a height of " + height);
			}
			
			for (int j = 0; j < height; ++j) {
				if (!checkElementType(elements[i][j], i, j)) {
					throw new InvalidBoardElementException("Expected an element type distinct of " + elements[i][j].getElementType());
				}
				this.elements[i][j] = elements[i][j];
			}
		}
	}
	
}
