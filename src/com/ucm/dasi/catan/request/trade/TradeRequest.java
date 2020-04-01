package com.ucm.dasi.catan.request.trade;

import com.ucm.dasi.catan.player.IPlayer;
import com.ucm.dasi.catan.request.Request;
import com.ucm.dasi.catan.request.RequestType;
import com.ucm.dasi.catan.resource.IResourceStorage;
import com.ucm.dasi.catan.resource.ResourceStorage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public class TradeRequest extends Request implements ITradeRequest {

  private Collection<IResourceStorage> acceptableExchanges;

  private UUID id;

  private IResourceStorage requestedResources;

  public TradeRequest(
      IPlayer player,
      RequestType type,
      Collection<IResourceStorage> acceptableExchanges,
      UUID id,
      IResourceStorage requestedResources) {
    super(player, type);

    this.acceptableExchanges = new ArrayList<IResourceStorage>(acceptableExchanges);
    this.id = id;
    this.requestedResources = new ResourceStorage(requestedResources);
  }

  @Override
  public Collection<IResourceStorage> getAcceptableExchanges() {
    return acceptableExchanges;
  }

  @Override
  public UUID getId() {
    return id;
  }

  @Override
  public IResourceStorage getRequestedResources() {
    return requestedResources;
  }
}
