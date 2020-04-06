package com.catanheroes.core.board;

import com.catanheroes.core.board.connection.ConnectionDirection;
import com.catanheroes.core.board.element.IBoardElement;
import com.catanheroes.core.board.exception.InvalidBoardElementException;
import com.catanheroes.core.board.structure.IBoardStructure;
import com.catanheroes.core.resource.production.IResourceProduction;

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
