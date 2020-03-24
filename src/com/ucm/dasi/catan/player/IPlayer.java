package com.ucm.dasi.catan.player;

import com.ucm.dasi.catan.resource.IResourceManager;

public interface IPlayer {
  int getId();

  IResourceManager getWarehouse();
}
