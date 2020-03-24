package com.ucm.dasi.catan.resource.provider;

import com.ucm.dasi.catan.resource.IResourceManager;

public interface ICostProvider<TType extends Comparable<TType>> {
  IResourceManager getCost(TType type);
}
