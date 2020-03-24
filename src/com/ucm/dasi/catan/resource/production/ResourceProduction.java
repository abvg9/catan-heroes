package com.ucm.dasi.catan.resource.production;

import com.ucm.dasi.catan.player.IPlayer;
import com.ucm.dasi.catan.resource.IResourceManager;
import com.ucm.dasi.catan.resource.ResourceManager;
import com.ucm.dasi.catan.resource.exception.NegativeNumberException;
import java.util.Map;
import java.util.TreeMap;

public class ResourceProduction implements IResourceProduction {

  private int productionNumber;

  private TreeMap<IPlayer, IResourceManager> resourceManagerMap;

  public ResourceProduction(
      int productionNumber, Map<IPlayer, IResourceManager> resourceManagerMap) {

    this.productionNumber = productionNumber;
    this.resourceManagerMap = new TreeMap<IPlayer, IResourceManager>(resourceManagerMap);
  }

  @Override
  public IResourceManager getProduction(IPlayer player) {
    IResourceManager playerProduction = resourceManagerMap.get(player);

    if (playerProduction == null) {
      return new ResourceManager();
    } else {
      try {
        return new ResourceManager(playerProduction);
      } catch (NegativeNumberException e) {
        return new ResourceManager();
      }
    }
  }

  @Override
  public int getProductionNumber() {
    return productionNumber;
  }
}
