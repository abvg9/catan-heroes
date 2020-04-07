package io.github.notaphplover.catan.core.resource;

import io.github.notaphplover.catan.core.resource.exception.NotEnoughtResourcesException;

public interface IResourceManager extends IResourceStorage {

  void add(IResourceStorage resourceManager);

  void substract(IResourceStorage resourceManager) throws NotEnoughtResourcesException;
}
