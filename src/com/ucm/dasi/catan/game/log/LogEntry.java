package com.ucm.dasi.catan.game.log;

import com.ucm.dasi.catan.request.IRequest;

public class LogEntry implements ILogEntry {
  
  private int productionNumber;
  
  private Iterable<IRequest> requests;

  public LogEntry(int productionNumber, Iterable<IRequest> requests) {
    
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

}
