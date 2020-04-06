package com.catanheroes.core.game.log;

import com.catanheroes.core.request.IRequest;

public interface ILogEntry {

  public void add(IRequest request);

  public int getProductionNumber();

  public Iterable<IRequest> getRequests();
}
