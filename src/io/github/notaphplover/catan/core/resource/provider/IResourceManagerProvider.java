package io.github.notaphplover.catan.core.resource.provider;

import io.github.notaphplover.catan.core.resource.IResourceStorage;

public interface IResourceManagerProvider<Type extends Comparable<Type>> {
  IResourceStorage getResourceManager(Type type);
}
