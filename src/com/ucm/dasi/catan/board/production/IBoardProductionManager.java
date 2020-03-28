package com.ucm.dasi.catan.board.production;

import com.ucm.dasi.catan.board.ICatanBoard;
import com.ucm.dasi.catan.board.structure.IBoardStructure;
import com.ucm.dasi.catan.resource.production.IResourceProduction;

public interface IBoardProductionManager {

  IResourceProduction getProduction(ICatanBoard board, int productionNumber);

  void syncProductionOnStructureBuilt(ICatanBoard board, int x, int y);

  void syncProductionOnStructureUpgrade(
      ICatanBoard board, IBoardStructure oldStructure, int x, int y);
}
