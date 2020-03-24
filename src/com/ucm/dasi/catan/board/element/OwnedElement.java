package com.ucm.dasi.catan.board.element;

import com.ucm.dasi.catan.board.BoardElementType;
import com.ucm.dasi.catan.player.IPlayer;
import com.ucm.dasi.catan.resource.IResourceManager;
import com.ucm.dasi.catan.resource.ResourceManager;
import com.ucm.dasi.catan.resource.exception.NegativeNumberException;

public abstract class OwnedElement extends BoardElement implements IOwnedElement {

  private IResourceManager cost;

  private IPlayer owner;

  public OwnedElement(BoardElementType type, IResourceManager cost, IPlayer owner) {
    super(type);

    this.cost = cost;
    this.owner = owner;
  }

  @Override
  public IResourceManager getCost() {
    try {
      return new ResourceManager(cost);
    } catch (NegativeNumberException e) {
      return null;
    }
  }

  @Override
  public IPlayer getOwner() {
    return owner;
  }
}
