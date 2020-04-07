package io.github.notaphplover.catan.core.game.log;

import io.github.notaphplover.catan.core.request.IRequest;

public interface ILogEntry {

  public void add(IRequest request);

  public int getProductionNumber();

  public Iterable<IRequest> getRequests();
}
