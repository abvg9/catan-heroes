package io.github.notaphplover.catan.core.board.terrain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.notaphplover.catan.core.board.BoardElementType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class BoardTerrainTest {

  @DisplayName("It must return the right element type")
  @Tag(value = "BoardTerrain")
  @Test
  public void itMustReturnTheRightElementType() {
    BoardTerrain terrain = new BoardTerrain(1, TerrainType.NONE);

    assertEquals(BoardElementType.TERRAIN, terrain.getElementType());
  }

  @DisplayName("It must get its production number")
  @Tag(value = "BoardTerrain")
  @Test
  public void itMustGetItsProductionNumber() {
    int productionNumber = 1;
    BoardTerrain terrain = new BoardTerrain(productionNumber, TerrainType.NONE);

    assertEquals(productionNumber, terrain.getProductionNumber());
  }

  @DisplayName("It must get its structure type")
  @Tag(value = "BoardTerrain")
  @Test
  public void itMustGetItsStructureType() {
    TerrainType type = TerrainType.NONE;
    BoardTerrain terrain = new BoardTerrain(1, type);

    assertEquals(type, terrain.getType());
  }
}
