package io.github.notaphplover.catan.core.board.structure;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.notaphplover.catan.core.board.BoardElementType;
import io.github.notaphplover.catan.core.resource.ResourceManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class BoardStructureTest {

  @DisplayName("It must return the right element type")
  @Tag(value = "BoardStructure")
  @Test
  public void itMustReturnTheRightElementType() {
    BoardStructure connection = new BoardStructure(null, new ResourceManager(), StructureType.NONE);

    assertEquals(BoardElementType.STRUCTURE, connection.getElementType());
  }

  @DisplayName("It must return its structure type")
  @Tag(value = "BoardStructure")
  @Test
  public void itMustGetItsStructureType() {
    StructureType type = StructureType.NONE;
    BoardStructure structure = new BoardStructure(null, new ResourceManager(), type);

    assertEquals(type, structure.getType());
  }
}
