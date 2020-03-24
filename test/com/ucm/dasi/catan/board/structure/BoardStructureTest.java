package com.ucm.dasi.catan.board.structure;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ucm.dasi.catan.board.BoardElementType;
import com.ucm.dasi.catan.resource.ResourceManager;
import org.junit.jupiter.api.Test;

public class BoardStructureTest {

  @Test
  public void itMustReturnTheRightElementType() {
    BoardStructure connection = new BoardStructure(null, new ResourceManager(), StructureType.None);

    assertEquals(BoardElementType.Structure, connection.getElementType());
  }

  @Test
  public void itMustGetItsStructureType() {
    StructureType type = StructureType.None;
    BoardStructure structure = new BoardStructure(null, new ResourceManager(), type);

    assertEquals(type, structure.getType());
  }
}
