package com.catanheroes.core.resource;

import com.catanheroes.core.resource.exception.NotEnoughtResourcesException;

public interface IResourceManager extends IResourceStorage {

  void add(IResourceStorage resourceManager);

  void substract(IResourceStorage resourceManager) throws NotEnoughtResourcesException;
}
