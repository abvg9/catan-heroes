package com.ucm.dasi.catan.resource;

public interface IResourceStorage {

  int getResourcesQuantity();

  int getResource(ResourceType type);
}
