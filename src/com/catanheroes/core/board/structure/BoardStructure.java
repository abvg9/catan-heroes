package com.catanheroes.core.board.structure;

import com.catanheroes.core.board.BoardElementType;
import com.catanheroes.core.board.element.OwnedElement;
import com.catanheroes.core.player.IPlayer;
import com.catanheroes.core.resource.IResourceStorage;

public class BoardStructure extends OwnedElement implements IBoardStructure {

  private StructureType type;

  public BoardStructure(IPlayer owner, IResourceStorage cost, StructureType type) {
    super(BoardElementType.STRUCTURE, cost, owner);

    this.type = type;
  }

  @Override
  public StructureType getType() {
    return type;
  }
}
