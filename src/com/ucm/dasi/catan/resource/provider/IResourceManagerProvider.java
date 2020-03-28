package com.ucm.dasi.catan.resource.provider;

import com.ucm.dasi.catan.resource.IResourceStorage;

public interface IResourceManagerProvider<TType extends Comparable<TType>> {
  IResourceStorage getResourceManager(TType type);
}
