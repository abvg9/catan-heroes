package com.ucm.dasi.catan.player;

import com.ucm.dasi.catan.resource.IResourceManager;

public interface IPlayer extends Comparable<IPlayer> {
  int getId();

  IResourceManager getResourceManager();
}
