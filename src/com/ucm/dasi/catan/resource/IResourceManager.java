package com.ucm.dasi.catan.resource;

import com.ucm.dasi.catan.resource.exception.NotEnoughtResourcesException;

public interface IResourceManager extends IResourceStorage {

  void add(IResourceStorage resourceManager);

  void substract(IResourceStorage resourceManager) throws NotEnoughtResourcesException;
}
