package io.github.notaphplover.catan.core.board.production;

import io.github.notaphplover.catan.core.board.exception.InvalidBoardElementException;
import io.github.notaphplover.catan.core.board.structure.IBoardStructure;
import io.github.notaphplover.catan.core.resource.production.IResourceProduction;

public interface IBoardProductionManager {

  IResourceProduction getProduction(int productionNumber);

  void syncProductionOnStructureBuilt(int x, int y) throws InvalidBoardElementException;

  void syncProductionOnStructureUpgrade(IBoardStructure oldStructure, int x, int y)
      throws InvalidBoardElementException;
}
