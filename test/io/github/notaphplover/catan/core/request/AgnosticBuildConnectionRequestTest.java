package io.github.notaphplover.catan.core.request;

import static org.junit.jupiter.api.Assertions.assertSame;

import io.github.notaphplover.catan.core.board.connection.ConnectionType;
import io.github.notaphplover.catan.core.player.IPlayer;
import io.github.notaphplover.catan.core.player.Player;
import io.github.notaphplover.catan.core.resource.ResourceManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class AgnosticBuildConnectionRequestTest {

  @DisplayName("It must store its connection type")
  @Tag(value = "AgnosticBuildConnectionRequest")
  @Test
  public void mustStoreConnectionType() {
    IPlayer player = new Player(0, new ResourceManager());
    ConnectionType type = ConnectionType.ROAD;

    AgnosticBuildConnectionRequest request =
        new AgnosticBuildConnectionRequest(player, RequestType.BUILD_CONNECTION, type, 0, 1);

    assertSame(type, request.getConnectionType());
  }
}
