package io.github.notaphplover.catan.core.board.structure;

import io.github.notaphplover.catan.core.board.BoardElementType;
import io.github.notaphplover.catan.core.board.element.OwnedElement;
import io.github.notaphplover.catan.core.player.IPlayer;
import io.github.notaphplover.catan.core.resource.IResourceStorage;

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
