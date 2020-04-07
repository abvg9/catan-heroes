package io.github.notaphplover.catan.core.board;

import io.github.notaphplover.catan.core.board.connection.ConnectionDirection;
import io.github.notaphplover.catan.core.board.element.IBoardElement;
import io.github.notaphplover.catan.core.board.exception.InvalidBoardElementException;
import io.github.notaphplover.catan.core.board.structure.IBoardStructure;
import io.github.notaphplover.catan.core.resource.production.IResourceProduction;

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
