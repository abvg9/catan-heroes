package com.ucm.dasi.catan.resource;

import java.util.Map;
import java.util.TreeMap;

import com.ucm.dasi.catan.player.IPlayer;

public class ResourceProduction implements IResourceProduction {
  
  private int productionNumber;
  
  private TreeMap<IPlayer, IResourceManager> resourceManagerMap;
  
  public ResourceProduction(int productionNumber, Map<IPlayer, IResourceManager> resourceManagerMap) {
    
    this.productionNumber = productionNumber;
    this.resourceManagerMap = new TreeMap<IPlayer, IResourceManager>(resourceManagerMap);
  }
  
  @Override
  public IResourceManager getProduction(IPlayer player) {
    return resourceManagerMap.get(player);
  }
  
  @Override
  public int getProductionNumber() {
    return productionNumber;
  }
}
