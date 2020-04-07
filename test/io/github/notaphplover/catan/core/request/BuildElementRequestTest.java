package io.github.notaphplover.catan.core.request;

import static org.junit.jupiter.api.Assertions.assertSame;

import io.github.notaphplover.catan.core.board.BoardElementType;
import io.github.notaphplover.catan.core.player.IPlayer;
import io.github.notaphplover.catan.core.player.Player;
import io.github.notaphplover.catan.core.resource.ResourceManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class BuildElementRequestTest {

  @DisplayName("It must store its initial values")
  @Tag(value = "BuildElementRequest")
  @Test
  public void mustStoreInitialValues() {
    IPlayer player = new Player(0, new ResourceManager());
    BoardElementType type = BoardElementType.CONNECTION;
    int x = 0;
    int y = 1;

    MinimunBuildElementRequest request =
        new MinimunBuildElementRequest(player, RequestType.BUILD_CONNECTION, type, x, y);

    assertSame(type, request.getElementType());
    assertSame(x, request.getX());
    assertSame(y, request.getY());
  }
}
