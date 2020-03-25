package com.ucm.dasi.catan.resource.provider;

import com.ucm.dasi.catan.resource.IResourceManager;
import com.ucm.dasi.catan.resource.ResourceManager;
import java.util.Map;
import java.util.TreeMap;

public class ResourceManagerProvider<TType extends Comparable<TType>>
    implements IResourceManagerProvider<TType> {

  protected TreeMap<TType, IResourceManager> costMap;

  public ResourceManagerProvider(Map<TType, IResourceManager> costMap) {
    this.costMap = new TreeMap<TType, IResourceManager>(costMap);
  }

  @Override
  public IResourceManager getResourceManager(TType type) {
    IResourceManager storedCost = this.costMap.get(type);

    return storedCost == null ? new ResourceManager() : new ResourceManager(storedCost);
  }
}
