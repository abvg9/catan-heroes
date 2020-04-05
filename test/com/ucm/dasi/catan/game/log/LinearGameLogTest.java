package com.ucm.dasi.catan.game.log;

import static org.junit.jupiter.api.Assertions.assertSame;

import com.ucm.dasi.catan.exception.NonNullInputException;
import com.ucm.dasi.catan.request.IRequest;
import java.util.ArrayList;
import java.util.Collection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class LinearGameLogTest {

  @DisplayName("It must get a log entry")
  @Tag("LinearGameLogTest")
  @Test
  public void itMustGetALogEntry() throws NonNullInputException {

    Collection<IRequest> requests = new ArrayList<IRequest>();
    ILogEntry entry = new LogEntry(3, requests);

    Collection<ILogEntry> entries = new ArrayList<ILogEntry>();
    entries.add(entry);

    LinearGameLog log = new LinearGameLog(entries);

    assertSame(entry, log.get(0));
  }

  @DisplayName("It must get the entries size")
  @Tag("LinearGameLogTest")
  @Test
  public void itMustGetTheEntriesSize() throws NonNullInputException {
    Collection<IRequest> requests = new ArrayList<IRequest>();
    ILogEntry entry = new LogEntry(3, requests);

    Collection<ILogEntry> entries = new ArrayList<ILogEntry>();
    entries.add(entry);

    LinearGameLog log = new LinearGameLog(entries);

    assertSame(entries.size(), log.size());
  }

  @DisplayName("It must set a log entry")
  @Tag("LinearGameLogTest")
  @Test
  public void itMustSetALogEntry() throws NonNullInputException {

    LinearGameLog log = new LinearGameLog();

    Collection<IRequest> requests = new ArrayList<IRequest>();
    ILogEntry entry = new LogEntry(3, requests);
    int turn = 0;

    log.set(turn, entry);

    assertSame(entry, log.get(turn));
  }
}
