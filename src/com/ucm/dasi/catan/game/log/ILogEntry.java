package com.ucm.dasi.catan.game.log;

import com.ucm.dasi.catan.request.IRequest;

public interface ILogEntry {

  public void add(IRequest request);

  public int getProductionNumber();

  public Iterable<IRequest> getRequests();
}
