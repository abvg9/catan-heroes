package com.catanheroes.core.board.connection;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.catanheroes.core.board.BoardElementType;
import com.catanheroes.core.resource.ResourceManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class BoardConnectionTest {

  @DisplayName("It must return the right element type")
  @Tag(value = "BoardConnection")
  @Test
  public void itMustReturnTheRightElementType() {
    BoardConnection connection =
        new BoardConnection(null, new ResourceManager(), ConnectionType.VOID);

    assertEquals(BoardElementType.CONNECTION, connection.getElementType());
  }

  @DisplayName("It must return its connection type")
  @Tag(value = "BoardConnection")
  @Test
  public void itMustGetItsConnectionType() {
    ConnectionType type = ConnectionType.VOID;
    BoardConnection connection = new BoardConnection(null, new ResourceManager(), type);

    assertEquals(type, connection.getType());
  }
}
