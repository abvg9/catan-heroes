package com.ucm.dasi.catan.resource;

import com.ucm.dasi.catan.resource.exception.NegativeNumberException;
import com.ucm.dasi.catan.resource.exception.NotEnoughtResourcesException;
import java.util.Map;

public class ResourceManager extends ResourceStorage implements IResourceManager {

  public ResourceManager() {
    super();
  }

  public ResourceManager(Map<ResourceType, Integer> resources) throws NegativeNumberException {
    super(resources);
  }

  public ResourceManager(IResourceStorage resourceManager) throws NegativeNumberException {
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

  public void add(IResourceStorage resourceManager) throws NegativeNumberException {

    for (ResourceType resourceType : ResourceType.values()) {
      int resourceQuantity = resourceManager.getResource(resourceType);
      setResource(resourceType, getResource(resourceType) + resourceQuantity);
    }
  }
}
