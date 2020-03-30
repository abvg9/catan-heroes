package com.ucm.dasi.catan.board.group;

import com.ucm.dasi.catan.board.structure.StructureType;
import com.ucm.dasi.catan.board.terrain.TerrainType;

public interface IStructureTerrainTypesPair extends Comparable<IStructureTerrainTypesPair> {

  public StructureType getStructureType();

  public TerrainType getTerrainType();
}
