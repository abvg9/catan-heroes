package io.github.notaphplover.catan.core.request;

import static org.junit.jupiter.api.Assertions.assertSame;

import io.github.notaphplover.catan.core.board.structure.StructureType;
import io.github.notaphplover.catan.core.player.IPlayer;
import io.github.notaphplover.catan.core.player.Player;
import io.github.notaphplover.catan.core.resource.ResourceManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class AgnosticBuildStructureRequestTest {

  @DisplayName("It must store its structure type")
  @Tag(value = "AgnosticBuildStructureRequest")
  @Test
  public void mustStoreStructureType() {
    IPlayer player = new Player(0, new ResourceManager());
    StructureType type = StructureType.SETTLEMENT;

    AgnosticBuildStructureRequest request =
        new AgnosticBuildStructureRequest(player, RequestType.BUILD_STRUCTURE, type, 0, 1);

    assertSame(type, request.getStructureType());
  }
}
