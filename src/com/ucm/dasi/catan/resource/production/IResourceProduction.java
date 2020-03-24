package com.ucm.dasi.catan.resource.production;

import com.ucm.dasi.catan.player.IPlayer;
import com.ucm.dasi.catan.resource.IResourceManager;

public interface IResourceProduction {

  public int getProductionNumber();

  public IResourceManager getProduction(IPlayer player);
}
