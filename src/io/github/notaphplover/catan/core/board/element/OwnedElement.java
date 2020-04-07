package io.github.notaphplover.catan.core.board.element;

import io.github.notaphplover.catan.core.board.BoardElementType;
import io.github.notaphplover.catan.core.player.IPlayer;
import io.github.notaphplover.catan.core.resource.IResourceStorage;

public abstract class OwnedElement extends BoardElement implements IOwnedElement {

  private IResourceStorage cost;

  private IPlayer owner;

  public OwnedElement(BoardElementType type, IResourceStorage cost, IPlayer owner) {
    super(type);

    this.cost = cost;
    this.owner = owner;
  }

  @Override
  public IResourceStorage getCost() {
    return cost;
  }

  @Override
  public IPlayer getOwner() {
    return owner;
  }
}
