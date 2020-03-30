package com.ucm.dasi.catan.request;

import com.ucm.dasi.catan.board.BoardElementType;

public interface IBuildElementRequest extends IRequest {

  BoardElementType getElementType();

  int getX();

  int getY();
}
