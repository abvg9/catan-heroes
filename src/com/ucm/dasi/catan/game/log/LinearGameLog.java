package com.ucm.dasi.catan.game.log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeMap;

import com.ucm.dasi.catan.exception.NonNullInputException;

public class LinearGameLog implements IGameLog {
  
  private TreeMap<Integer, ILogEntry> entries;
  
  public LinearGameLog() throws NonNullInputException {
    this(null);
  }
  
  public LinearGameLog(Collection<ILogEntry> entriesCollection) throws NonNullInputException {
    
    if (entriesCollection == null) {
      entriesCollection = new ArrayList<ILogEntry>();
    }
    
    entries = new TreeMap<Integer, ILogEntry>();
    
    int index = 0;
    for (ILogEntry entry : entriesCollection) {
      if (entry == null) {
        throw new NonNullInputException();
      } else {
        entries.put(index++, entry);
      }
    }
  }
  
  @Override
  public ILogEntry get(int turn) {
    return entries.get(turn);
  }

  @Override
  public void set(int turn, ILogEntry entry) {
    entries.put(turn, entry);
  }
  
  @Override
  public int size() {
    return entries.size();
  }
 
}
