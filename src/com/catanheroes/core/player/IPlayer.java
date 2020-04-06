package com.catanheroes.core.player;

import com.catanheroes.core.resource.IResourceManager;

public interface IPlayer extends Comparable<IPlayer> {
  int getId();

  IResourceManager getResourceManager();
}
