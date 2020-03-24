package com.ucm.dasi.catan.request;

import static org.junit.jupiter.api.Assertions.assertSame;

import com.ucm.dasi.catan.board.structure.StructureType;
import com.ucm.dasi.catan.player.IPlayer;
import com.ucm.dasi.catan.player.Player;
import com.ucm.dasi.catan.resource.ResourceManager;
import com.ucm.dasi.catan.resource.exception.NegativeNumberException;
import org.junit.jupiter.api.Test;

public class BuildStructureRequestTest {
  @Test
  public void mustStoreConnectionType() throws NegativeNumberException {
    IPlayer player = new Player(0, new ResourceManager());
    StructureType type = StructureType.Settlement;

    BuildStructureRequest request = new BuildStructureRequest(player, type, 0, 1);

    assertSame(type, request.getStructureType());
  }
}
