package com.ucm.dasi.catan.board;

import com.ucm.dasi.catan.board.connection.ConnectionDirection;
import com.ucm.dasi.catan.board.element.IBoardElement;
import com.ucm.dasi.catan.board.structure.IBoardStructure;

public interface ICatanBoard {
  IBoardElement get(int x, int y);

  ConnectionDirection getConnectionDirection(int x, int y);

  int getHeight();

  IBoardStructure getStructure(int x, int y);

  int getWidth();
}
