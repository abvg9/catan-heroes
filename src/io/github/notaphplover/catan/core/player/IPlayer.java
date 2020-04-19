package io.github.notaphplover.catan.core.player;

import io.github.notaphplover.catan.core.request.IRequest;
import io.github.notaphplover.catan.core.resource.IResourceManager;
import java.util.Collection;

public interface IPlayer extends Comparable<IPlayer> {
  int getId();

  IResourceManager getResourceManager();

  void emptyMissing();

  Collection<IRequest> getMissing();

  void registerMiss(IRequest request);
}
