package com.catanheroes.core.request;

import static org.junit.jupiter.api.Assertions.assertSame;

import com.catanheroes.core.board.structure.StructureType;
import com.catanheroes.core.player.IPlayer;
import com.catanheroes.core.player.Player;
import com.catanheroes.core.resource.ResourceManager;
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
