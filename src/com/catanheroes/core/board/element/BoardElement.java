package com.catanheroes.core.board.element;

import com.catanheroes.core.board.BoardElementType;

public abstract class BoardElement implements IBoardElement {
  private BoardElementType type;

  public BoardElement(BoardElementType type) {
    this.type = type;
  }

  public BoardElementType getElementType() {
    return this.type;
  }
}
