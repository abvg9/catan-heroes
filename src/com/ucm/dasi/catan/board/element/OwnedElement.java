package com.ucm.dasi.catan.board.element;

import com.ucm.dasi.catan.board.BoardElementType;
import com.ucm.dasi.catan.player.IPlayer;
import com.ucm.dasi.catan.resource.IResourceStorage;

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
