package io.github.notaphplover.catan.core.player;

import io.github.notaphplover.catan.core.request.IRequest;
import io.github.notaphplover.catan.core.resource.IResourceManager;
import io.github.notaphplover.catan.core.resource.ResourceManager;
import java.util.Collection;
import java.util.LinkedList;

public class Player implements IPlayer {

  private int id;

  private LinkedList<IRequest> missingRequests;

  private IResourceManager resourceManager;

  public Player(int id, IResourceManager resourceManager) {
    this.id = id;
    missingRequests = new LinkedList<>();
    this.resourceManager = new ResourceManager(resourceManager);
  }

  @Override
  public boolean equals(Object other) {

    if (!(other instanceof IPlayer)) {
      return false;
    }

    return id == ((IPlayer) other).getId();
  }

  @Override
  public int compareTo(IPlayer other) {
    return Integer.compare(getId(), other.getId());
  }

  @Override
  public Collection<IRequest> emptyMissing() {

    Collection<IRequest> requests = this.missingRequests;

    this.missingRequests = new LinkedList<>();

    return requests;
  }

  @Override
  public int getId() {
    return id;
  }

  @Override
  public IResourceManager getResourceManager() {
    return resourceManager;
  }

  @Override
  public void registerMiss(IRequest request) {

    this.missingRequests.add(request);
  }
}
