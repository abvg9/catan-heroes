package com.catanheroes.core.request;

import static org.junit.jupiter.api.Assertions.assertSame;

import com.catanheroes.core.board.structure.StructureType;
import com.catanheroes.core.player.IPlayer;
import com.catanheroes.core.player.Player;
import com.catanheroes.core.resource.ResourceManager;
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
