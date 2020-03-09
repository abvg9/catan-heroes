package com.ucm.dasi.catan.board;

public interface ICatanBoard {
	IBoardElement get(int x, int y);
	
	IBoardConnection getEastConnection(int x, int y);
	
	IBoardConnection getNorthConnection(int x, int y);
	
	IBoardConnection getSouthConnection(int x, int y);
	
	IBoardConnection getWestConnection(int x, int y);
	
	IBoardTerrain getTerrain(int x, int y);
	
	IBoardStructure getStructure(int x, int y);
}
