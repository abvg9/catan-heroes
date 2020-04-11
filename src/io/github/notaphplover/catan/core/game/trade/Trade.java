package io.github.notaphplover.catan.core.game.trade;

import io.github.notaphplover.catan.core.resource.IResourceStorage;
import io.github.notaphplover.catan.core.resource.ResourceStorage;
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
