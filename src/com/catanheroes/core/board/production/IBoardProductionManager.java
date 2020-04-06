package com.catanheroes.core.board.production;

import com.catanheroes.core.board.exception.InvalidBoardElementException;
import com.catanheroes.core.board.structure.IBoardStructure;
import com.catanheroes.core.resource.production.IResourceProduction;

public interface IBoardProductionManager {

  IResourceProduction getProduction(int productionNumber);

  void syncProductionOnStructureBuilt(int x, int y) throws InvalidBoardElementException;

  void syncProductionOnStructureUpgrade(IBoardStructure oldStructure, int x, int y)
      throws InvalidBoardElementException;
}
