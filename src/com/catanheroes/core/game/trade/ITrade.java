package com.catanheroes.core.game.trade;

import com.catanheroes.core.resource.IResourceStorage;
import java.util.Collection;

public interface ITrade extends IReference {

  Collection<IResourceStorage> getAcceptableExchanges();

  IResourceStorage getRequestedResources();
}
