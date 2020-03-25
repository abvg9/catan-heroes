package com.ucm.dasi.catan.resource.production;

import com.ucm.dasi.catan.player.IPlayer;
import com.ucm.dasi.catan.resource.IResourceStorage;
import com.ucm.dasi.catan.resource.ResourceManager;
import com.ucm.dasi.catan.resource.ResourceStorage;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class ResourceProduction implements IResourceProduction {

  private int productionNumber;

  private TreeMap<IPlayer, IResourceStorage> resourceStorageMap;

  public ResourceProduction(
      int productionNumber, Map<IPlayer, ? extends IResourceStorage> resourceStorageMap) {

    this.productionNumber = productionNumber;

    initStorageMap(resourceStorageMap);
  }

  @Override
  public IResourceStorage getProduction(IPlayer player) {
    IResourceStorage playerProduction = resourceStorageMap.get(player);

    if (playerProduction == null) {
      playerProduction = new ResourceManager();
    }

    return playerProduction;
  }

  @Override
  public int getProductionNumber() {
    return productionNumber;
  }

  private void initStorageMap(Map<IPlayer, ? extends IResourceStorage> resourceStorageMap) {
    this.resourceStorageMap = new TreeMap<IPlayer, IResourceStorage>();

    for (Entry<IPlayer, ? extends IResourceStorage> entry : resourceStorageMap.entrySet()) {
      this.resourceStorageMap.put(entry.getKey(), new ResourceStorage(entry.getValue()));
    }
  }
}
