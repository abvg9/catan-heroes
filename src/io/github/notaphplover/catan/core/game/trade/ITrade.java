package io.github.notaphplover.catan.core.game.trade;

import io.github.notaphplover.catan.core.resource.IResourceStorage;
import java.util.Collection;

public interface ITrade extends IReference {

  Collection<IResourceStorage> getAcceptableExchanges();

  IResourceStorage getRequestedResources();
}
