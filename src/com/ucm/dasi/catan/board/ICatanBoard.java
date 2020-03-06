package com.ucm.dasi.catan.board;

import com.ucm.dasi.catan.board.connection.IBoardConnection;
import com.ucm.dasi.catan.board.element.IBoardElement;
import com.ucm.dasi.catan.board.structure.IBoardStructure;
import com.ucm.dasi.catan.board.terrain.IBoardTerrain;

public interface ICatanBoard {
    IBoardElement get(int x, int y);

    IBoardConnection getEastConnection(int x, int y);

    IBoardConnection getNorthConnection(int x, int y);

    IBoardConnection getSouthConnection(int x, int y);

    IBoardConnection getWestConnection(int x, int y);

    IBoardTerrain getTerrain(int x, int y);

    IBoardStructure getStructure(int x, int y);
}
