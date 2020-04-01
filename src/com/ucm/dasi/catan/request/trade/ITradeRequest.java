package com.ucm.dasi.catan.request.trade;

import com.ucm.dasi.catan.request.IRequest;
import com.ucm.dasi.catan.resource.IResourceStorage;
import java.util.Collection;
import java.util.UUID;

public interface ITradeRequest extends IRequest {

  Collection<IResourceStorage> getAcceptableExchanges();

  UUID getId();

  IResourceStorage getRequestedResources();
}
