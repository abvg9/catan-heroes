package io.github.notaphplover.catan.core.player;

import io.github.notaphplover.catan.core.resource.IResourceManager;

public interface IPlayer extends Comparable<IPlayer> {
  int getId();

  IResourceManager getResourceManager();
}
