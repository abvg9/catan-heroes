package io.github.notaphplover.catan.core.resource;

public interface IResourceStorage extends Comparable<IResourceStorage> {

  boolean canSubstract(IResourceStorage resources);

  int getResourcesQuantity();

  int getResource(ResourceType type);
}
