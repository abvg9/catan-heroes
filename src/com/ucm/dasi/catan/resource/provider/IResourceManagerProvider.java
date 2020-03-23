package com.ucm.dasi.catan.resource.provider;

import com.ucm.dasi.catan.resource.IResourceManager;

public interface IResourceManagerProvider<TType extends Comparable<TType>> {
  IResourceManager getResourceManager(TType type);
}
