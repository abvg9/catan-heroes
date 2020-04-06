package com.catanheroes.core.resource.production;

import com.catanheroes.core.player.IPlayer;
import com.catanheroes.core.resource.IResourceStorage;

public interface IResourceProduction {

  public int getProductionNumber();

  public IResourceStorage getProduction(IPlayer player);
}
