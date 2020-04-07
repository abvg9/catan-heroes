package io.github.notaphplover.catan.core.request;

import io.github.notaphplover.catan.core.board.BoardElementType;

public interface IBuildElementRequest extends IRequest {

  BoardElementType getElementType();

  int getX();

  int getY();
}
