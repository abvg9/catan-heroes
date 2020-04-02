package com.ucm.dasi.catan.game.trade;

import com.ucm.dasi.catan.resource.IResourceStorage;
import java.util.Collection;

public interface ITrade extends IReference {

  Collection<IResourceStorage> getAcceptableExchanges();

  IResourceStorage getRequestedResources();
}
