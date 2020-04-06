package com.catanheroes.core.game.trade;

import com.catanheroes.core.resource.IResourceStorage;
import com.catanheroes.core.resource.ResourceStorage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public class Trade extends Reference implements ITrade {

  private Collection<IResourceStorage> acceptableExchanges;

  private IResourceStorage requestedResources;

  public Trade(
      UUID id,
      Collection<IResourceStorage> acceptableExchanges,
      IResourceStorage requestedResources) {

    super(id);

    if (acceptableExchanges == null) {
      this.acceptableExchanges = null;
    } else {
      this.acceptableExchanges = new ArrayList<IResourceStorage>(acceptableExchanges);
    }

    this.requestedResources = new ResourceStorage(requestedResources);
  }

  @Override
  public Collection<IResourceStorage> getAcceptableExchanges() {
    return acceptableExchanges;
  }

  @Override
  public IResourceStorage getRequestedResources() {
    return requestedResources;
  }
}
