package com.ucm.dasi.catan.player;

import com.ucm.dasi.catan.resource.IResourceManager;
import com.ucm.dasi.catan.resource.ResourceManager;
import com.ucm.dasi.catan.resource.exception.NegativeNumberException;

public class Player implements IPlayer {

  private int id;

  private IResourceManager resourceManager;

  public Player(int id, IResourceManager resourceManager) throws NegativeNumberException {
    this.id = id;
    this.resourceManager = new ResourceManager(resourceManager);
  }

  @Override
  public boolean equals(Object other) {

    if (!(other instanceof IPlayer)) {
      return false;
    }

    return id == ((IPlayer) other).getId();
  }

  @Override
  public int compareTo(IPlayer other) {
    return getId() - other.getId();
  }

  @Override
  public int getId() {
    return id;
  }

  @Override
  public IResourceManager getResourceManager() {
    return resourceManager;
  }
}
