package com.catanheroes.core.board.group;

import com.catanheroes.core.board.structure.StructureType;
import com.catanheroes.core.board.terrain.TerrainType;

public interface IStructureTerrainTypesPair extends Comparable<IStructureTerrainTypesPair> {

  public StructureType getStructureType();

  public TerrainType getTerrainType();
}
