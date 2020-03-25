package com.ucm.dasi.catan.resource;

import com.ucm.dasi.catan.resource.exception.NegativeNumberException;
import java.util.Map;
import java.util.TreeMap;

public class ResourceStorage implements IResourceStorage {

  protected int resourceQuantity;

  private TreeMap<ResourceType, Integer> resources;

  public ResourceStorage() {

    resources = new TreeMap<ResourceType, Integer>();
    resourceQuantity = 0;

    for (ResourceType resourceType : ResourceType.values()) {
      resources.put(resourceType, 0);
    }
  }

  public ResourceStorage(Map<ResourceType, Integer> resources) {
    this();

    for (ResourceType resourceType : ResourceType.values()) {
      Integer resourceAmount = resources.get(resourceType);
      if (null == resourceAmount) {
        resourceAmount = 0;
      }
      setResource(resourceType, resourceAmount);
    }
  }

  public ResourceStorage(IResourceStorage resourceManager) {
    this();

    for (ResourceType resourceType : ResourceType.values()) {
      setResource(resourceType, resourceManager.getResource(resourceType));
    }
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof IResourceStorage)) {
      return false;
    }

    for (ResourceType resourceType : ResourceType.values()) {
      if (this.getResource(resourceType) != ((IResourceStorage) object).getResource(resourceType)) {
        return false;
      }
    }

    return true;
  }

  public int getResource(ResourceType type) {
    return resources.get(type);
  }

  public int getQuantityResource() {
    return resourceQuantity;
  }

  protected void setResource(ResourceType type, int newQuantity) {

    if (newQuantity < 0) {
      throw new NegativeNumberException(type.toString());
    }

    int oldQuantity = resources.get(type);
    resourceQuantity += newQuantity - oldQuantity;

    resources.put(type, newQuantity);
  }
}
