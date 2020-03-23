package com.ucm.dasi.catan.resource;

import com.ucm.dasi.catan.player.IPlayer;

public interface IResourceProduction {
  
  public int getProductionNumber();
  
  public IResourceManager getProduction(IPlayer player);
}
