package com.ucm.dasi.catan.board;

import com.ucm.dasi.catan.board.exception.InvalidBoardSelectionException;

public interface ICatanBoard {

	/**
	 * Gets the terrain element at the position specified.
	 * @param x X coordinate
	 * @param y	Y coordinate.
	 * @return Terrain element found.
	 * @throws InvalidBoardSelectionException 
	 */
	ICatanTerrain get(int x, int y) throws InvalidBoardSelectionException;
	
	/**
	 * Gets the height of the board.
	 * @return Board's height.
	 */
	int getHeight();
	
	/**
	 * Gets the width of the board.
	 * @return Board's width.
	 */
	int getWidth();
}
