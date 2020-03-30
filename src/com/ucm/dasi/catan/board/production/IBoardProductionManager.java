package com.ucm.dasi.catan.board.production;

import com.ucm.dasi.catan.board.exception.InvalidBoardElementException;
import com.ucm.dasi.catan.board.structure.IBoardStructure;
import com.ucm.dasi.catan.resource.production.IResourceProduction;

public interface IBoardProductionManager {

  IResourceProduction getProduction(int productionNumber);

  void syncProductionOnStructureBuilt(int x, int y) throws InvalidBoardElementException;

  void syncProductionOnStructureUpgrade(IBoardStructure oldStructure, int x, int y)
      throws InvalidBoardElementException;
}
