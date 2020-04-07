package io.github.notaphplover.catan.core.resource.production;

import io.github.notaphplover.catan.core.player.IPlayer;
import io.github.notaphplover.catan.core.resource.IResourceStorage;

public interface IResourceProduction {

  public int getProductionNumber();

  public IResourceStorage getProduction(IPlayer player);
}
