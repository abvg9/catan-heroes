package io.github.notaphplover.catan.core.request;

import static org.junit.jupiter.api.Assertions.assertSame;

import io.github.notaphplover.catan.core.board.structure.StructureType;
import io.github.notaphplover.catan.core.player.IPlayer;
import io.github.notaphplover.catan.core.player.Player;
import io.github.notaphplover.catan.core.resource.ResourceManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class BuildStructureRequestTest {

  @DisplayName("It must store its request type")
  @Tag(value = "BuildStructureRequest")
  @Test
  public void mustStoreItsRequestType() {
    IPlayer player = new Player(0, new ResourceManager());
    StructureType type = StructureType.SETTLEMENT;

    BuildStructureRequest request = new BuildStructureRequest(player, type, 0, 1);

    assertSame(RequestType.BUILD_STRUCTURE, request.getType());
  }
}
