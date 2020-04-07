package io.github.notaphplover.catan.core.resource.provider;

import io.github.notaphplover.catan.core.resource.IResourceStorage;

public interface IResourceManagerProvider<TType extends Comparable<TType>> {
  IResourceStorage getResourceManager(TType type);
}
