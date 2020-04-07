package io.github.notaphplover.catan.core.game.log;

import io.github.notaphplover.catan.core.exception.NonNullInputException;
import io.github.notaphplover.catan.core.game.exception.InvalidLogInsertionException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeMap;

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
    int size = size();

    if (turn > size) {
      throw new InvalidLogInsertionException(size);
    }

    entries.put(turn, entry);
  }

  @Override
  public int size() {
    return entries.size();
  }
}
