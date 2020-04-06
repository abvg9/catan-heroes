package com.catanheroes.core.game.log;

import com.catanheroes.core.request.IRequest;
import java.util.ArrayList;
import java.util.Collection;

public class LogEntry implements ILogEntry {

  private int productionNumber;

  private Collection<IRequest> requests;

  public LogEntry(int productionNumber, Collection<IRequest> requests) {

    this.productionNumber = productionNumber;
    this.requests = new ArrayList<IRequest>(requests);
  }

  @Override
  public int getProductionNumber() {
    return productionNumber;
  }

  @Override
  public Iterable<IRequest> getRequests() {
    return requests;
  }

  @Override
  public void add(IRequest request) {
    requests.add(request);
  }
}
