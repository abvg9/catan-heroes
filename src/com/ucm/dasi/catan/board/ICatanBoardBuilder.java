package com.ucm.dasi.catan.board;

public interface ICatanBoardBuilder {
	
	int getHeight();
	
	ICatanTerrain[][] getMatrix();
	
	int getWidth();
}
