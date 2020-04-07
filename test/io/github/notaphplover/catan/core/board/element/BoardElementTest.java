package io.github.notaphplover.catan.core.board.element;

import static org.junit.jupiter.api.Assertions.assertSame;

import io.github.notaphplover.catan.core.board.BoardElementType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class BoardElementTest {

  @DisplayName("It must return its type")
  @Tag(value = "BoardElement")
  @Test
  public void itMustReturnItsType() {
    BoardElementType type = BoardElementType.STRUCTURE;
    BoardElement boardElement = new MinimunBoardElement(type);

    assertSame(type, boardElement.getElementType());
  }
}
