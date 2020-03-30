package com.ucm.dasi.catan.game.log;

import com.ucm.dasi.catan.request.IRequest;
import java.util.Collection;

public class LogEntry implements ILogEntry {

  private int productionNumber;

  private Collection<IRequest> requests;

  public LogEntry(int productionNumber, Collection<IRequest> requests) {

    this.productionNumber = productionNumber;
    this.requests = requests;
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
