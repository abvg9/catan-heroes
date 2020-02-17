package com.ucm.dasi.catan.board;

public interface ICatanBoard {

	/**
	 * Gets the terrain element at the position specified.
	 * @param x X coordinate
	 * @param y	Y coordinate.
	 * @return Terrain element found.
	 */
	ICatanTerrain get(int x, int y);
	
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
