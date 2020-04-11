package io.github.notaphplover.catan.core.resource.provider;

import io.github.notaphplover.catan.core.resource.IResourceStorage;
import io.github.notaphplover.catan.core.resource.ResourceStorage;
import java.util.Map;
import java.util.TreeMap;

public class ResourceManagerProvider<T extends Comparable<T>>
    implements IResourceManagerProvider<T> {

  protected TreeMap<T, IResourceStorage> resourcesMap;

  public ResourceManagerProvider(Map<T, ? extends IResourceStorage> resourcesMap) {
    this.resourcesMap = new TreeMap<T, IResourceStorage>(resourcesMap);
  }

  @Override
  public IResourceStorage getResourceManager(T type) {
    IResourceStorage storedCost = this.resourcesMap.get(type);

    return storedCost == null ? new ResourceStorage() : storedCost;
  }
}
