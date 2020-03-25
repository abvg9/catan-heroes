package com.ucm.dasi.catan.request;

import static org.junit.jupiter.api.Assertions.assertSame;

import com.ucm.dasi.catan.board.BoardElementType;
import com.ucm.dasi.catan.player.IPlayer;
import com.ucm.dasi.catan.player.Player;
import com.ucm.dasi.catan.resource.ResourceManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class BuildElementRequestTest {

  @DisplayName("It must store its initial values")
  @Tag(value = "BuildElementRequest")
  @Test
  public void mustStoreInitialValues() {
    IPlayer player = new Player(0, new ResourceManager());
    BoardElementType type = BoardElementType.Connection;
    int x = 0;
    int y = 1;

    MinimunBuildElementRequest request =
        new MinimunBuildElementRequest(player, RequestType.BuildConnection, type, x, y);

    assertSame(type, request.getElementType());
    assertSame(x, request.getX());
    assertSame(y, request.getY());
  }
}
