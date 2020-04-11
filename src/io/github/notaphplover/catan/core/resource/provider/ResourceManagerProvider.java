package io.github.notaphplover.catan.core.resource.provider;

import io.github.notaphplover.catan.core.resource.IResourceStorage;
import io.github.notaphplover.catan.core.resource.ResourceStorage;
import java.util.Map;
import java.util.TreeMap;

public class ResourceManagerProvider<Type extends Comparable<Type>>
    implements IResourceManagerProvider<Type> {

  protected TreeMap<Type, IResourceStorage> resourcesMap;

  public ResourceManagerProvider(Map<Type, ? extends IResourceStorage> resourcesMap) {
    this.resourcesMap = new TreeMap<Type, IResourceStorage>(resourcesMap);
  }

  @Override
  public IResourceStorage getResourceManager(Type type) {
    IResourceStorage storedCost = this.resourcesMap.get(type);

    return storedCost == null ? new ResourceStorage() : storedCost;
  }
}
