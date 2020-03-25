package com.ucm.dasi.catan.resource;

import com.ucm.dasi.catan.resource.exception.NegativeNumberException;
import com.ucm.dasi.catan.resource.exception.NotEnoughtResourcesException;

public interface IResourceManager extends IResourceStorage {

  void add(IResourceStorage resourceManager) throws NegativeNumberException;

  void substract(IResourceStorage resourceManager) throws NotEnoughtResourcesException;
}
