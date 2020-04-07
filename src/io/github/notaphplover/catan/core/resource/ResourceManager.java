package io.github.notaphplover.catan.core.resource;

import io.github.notaphplover.catan.core.resource.exception.NegativeNumberException;
import io.github.notaphplover.catan.core.resource.exception.NotEnoughtResourcesException;
import java.util.Map;

public class ResourceManager extends ResourceStorage implements IResourceManager {

  public ResourceManager() {
    super();
  }

  public ResourceManager(Map<ResourceType, Integer> resources) {
    super(resources);
  }

  public ResourceManager(IResourceStorage resourceManager) {
    super(resourceManager);
  }

  public void substract(IResourceStorage resourceManager) throws NotEnoughtResourcesException {

    try {
      for (ResourceType resourceType : ResourceType.values()) {
        int resourceQuantity = resourceManager.getResource(resourceType);
        setResource(resourceType, getResource(resourceType) - resourceQuantity);
      }
    } catch (NegativeNumberException e) {
      throw new NotEnoughtResourcesException();
    }
  }

  public void add(IResourceStorage resourceManager) {

    for (ResourceType resourceType : ResourceType.values()) {
      int resourceQuantity = resourceManager.getResource(resourceType);
      setResource(resourceType, getResource(resourceType) + resourceQuantity);
    }
  }
}
