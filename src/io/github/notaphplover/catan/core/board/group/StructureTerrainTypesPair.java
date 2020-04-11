package io.github.notaphplover.catan.core.board.group;

import io.github.notaphplover.catan.core.board.structure.StructureType;
import io.github.notaphplover.catan.core.board.terrain.TerrainType;

public class StructureTerrainTypesPair implements IStructureTerrainTypesPair {

  private StructureType structureType;

  private TerrainType terrainType;

  public StructureTerrainTypesPair(StructureType structureType, TerrainType terrainType) {

    this.structureType = structureType;
    this.terrainType = terrainType;
  }

  @Override
  public int compareTo(IStructureTerrainTypesPair other) {
    int structureTypeComparison = structureType.compareTo(other.getStructureType());
    if (structureTypeComparison != 0) {
      return structureTypeComparison;
    }
    return terrainType.compareTo(other.getTerrainType());
  }

  @Override
  public StructureType getStructureType() {
    return structureType;
  }

  @Override
  public TerrainType getTerrainType() {
    return terrainType;
  }
}
