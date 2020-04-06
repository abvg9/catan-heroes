package com.catanheroes.core.player;

import com.catanheroes.core.resource.IResourceManager;
import com.catanheroes.core.resource.ResourceManager;

public class Player implements IPlayer {

  private int id;

  private IResourceManager resourceManager;

  public Player(int id, IResourceManager resourceManager) {
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
    return Integer.compare(getId(), other.getId());
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
