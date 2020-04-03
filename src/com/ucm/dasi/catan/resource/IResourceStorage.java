package com.ucm.dasi.catan.resource;

public interface IResourceStorage extends Comparable<IResourceStorage> {

  int getResourcesQuantity();

  int getResource(ResourceType type);
}
