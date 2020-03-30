package com.ucm.dasi.catan.board.group;

import com.ucm.dasi.catan.board.structure.StructureType;
import com.ucm.dasi.catan.board.terrain.TerrainType;

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
