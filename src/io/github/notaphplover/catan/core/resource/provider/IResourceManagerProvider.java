package io.github.notaphplover.catan.core.resource.provider;

import io.github.notaphplover.catan.core.resource.IResourceStorage;

public interface IResourceManagerProvider<T extends Comparable<T>> {
  IResourceStorage getResourceManager(T type);
}
