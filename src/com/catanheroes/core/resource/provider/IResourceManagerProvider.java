package com.catanheroes.core.resource.provider;

import com.catanheroes.core.resource.IResourceStorage;

public interface IResourceManagerProvider<TType extends Comparable<TType>> {
  IResourceStorage getResourceManager(TType type);
}
