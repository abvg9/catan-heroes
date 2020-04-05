package com.ucm.dasi.catan.board;

import com.ucm.dasi.catan.board.connection.ConnectionDirection;
import com.ucm.dasi.catan.board.element.IBoardElement;
import com.ucm.dasi.catan.board.exception.InvalidBoardElementException;
import com.ucm.dasi.catan.board.structure.IBoardStructure;
import com.ucm.dasi.catan.resource.production.IResourceProduction;

public interface ICatanBoard {

  void build(IBoardElement element, int x, int y) throws InvalidBoardElementException;

  IBoardElement get(int x, int y);

  ConnectionDirection getConnectionDirection(int x, int y);

  int getHeight();

  IResourceProduction getProduction(int productionNumber);

  IBoardStructure getStructure(int x, int y);

  int getWidth();

  void upgrade(IBoardElement element, int x, int y) throws InvalidBoardElementException;
}
