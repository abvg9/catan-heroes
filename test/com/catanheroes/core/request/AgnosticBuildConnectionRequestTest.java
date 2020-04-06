package com.catanheroes.core.request;

import static org.junit.jupiter.api.Assertions.assertSame;

import com.catanheroes.core.board.connection.ConnectionType;
import com.catanheroes.core.player.IPlayer;
import com.catanheroes.core.player.Player;
import com.catanheroes.core.resource.ResourceManager;
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
