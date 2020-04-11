package io.github.notaphplover.catan.core.game.log;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import io.github.notaphplover.catan.core.player.IPlayer;
import io.github.notaphplover.catan.core.player.Player;
import io.github.notaphplover.catan.core.request.EndTurnRequest;
import io.github.notaphplover.catan.core.request.IRequest;
import io.github.notaphplover.catan.core.request.StartTurnRequest;
import io.github.notaphplover.catan.core.resource.ResourceManager;
import java.util.ArrayList;
import java.util.Collection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class LogEntryTest {

  @DisplayName("It must add a request")
  @Tag("LogEntry")
  @Test
  public void itMustAddARequest() {
    int productionNumber = 2;

    IPlayer player = new Player(0, new ResourceManager());

    Collection<IRequest> requests = new ArrayList<IRequest>();
    requests.add(new StartTurnRequest(player));

    LogEntry entry = new LogEntry(productionNumber, requests);

    IRequest requestToAdd = new EndTurnRequest(player);

    entry.add(requestToAdd);

    requests.add(requestToAdd);

    assertEquals(requests, entry.getRequests());
  }

  @DisplayName("It must get its production number")
  @Tag("LogEntry")
  @Test
  public void itMustGetItsProductionNumber() {
    int productionNumber = 2;
    Collection<IRequest> requests = new ArrayList<IRequest>();

    LogEntry entry = new LogEntry(productionNumber, requests);

    assertSame(productionNumber, entry.getProductionNumber());
  }

  @DisplayName("It must get its requests")
  @Tag("LogEntry")
  @Test
  public void itMustGetItsRequests() {
    int productionNumber = 2;
    Collection<IRequest> requests = new ArrayList<IRequest>();
    requests.add(new StartTurnRequest(new Player(0, new ResourceManager())));

    LogEntry entry = new LogEntry(productionNumber, requests);

    assertEquals(requests, entry.getRequests());
  }
}
