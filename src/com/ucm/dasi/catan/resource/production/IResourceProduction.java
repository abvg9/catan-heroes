package com.ucm.dasi.catan.resource.production;

import com.ucm.dasi.catan.player.IPlayer;
import com.ucm.dasi.catan.resource.IResourceStorage;

public interface IResourceProduction {

  public int getProductionNumber();

  public IResourceStorage getProduction(IPlayer player);
}
