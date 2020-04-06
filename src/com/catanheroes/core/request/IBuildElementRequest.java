package com.catanheroes.core.request;

import com.catanheroes.core.board.BoardElementType;

public interface IBuildElementRequest extends IRequest {

  BoardElementType getElementType();

  int getX();

  int getY();
}
