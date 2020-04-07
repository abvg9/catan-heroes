package io.github.notaphplover.catan.core.board.group;

import io.github.notaphplover.catan.core.board.structure.StructureType;
import io.github.notaphplover.catan.core.board.terrain.TerrainType;

public interface IStructureTerrainTypesPair extends Comparable<IStructureTerrainTypesPair> {

  public StructureType getStructureType();

  public TerrainType getTerrainType();
}
