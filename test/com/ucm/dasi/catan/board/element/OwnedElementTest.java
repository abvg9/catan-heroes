package com.ucm.dasi.catan.board.element;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import com.ucm.dasi.catan.board.BoardElementType;
import com.ucm.dasi.catan.player.IPlayer;
import com.ucm.dasi.catan.player.Player;
import com.ucm.dasi.catan.resource.IResourceManager;
import com.ucm.dasi.catan.resource.ResourceManager;
import com.ucm.dasi.catan.resource.ResourceType;
import com.ucm.dasi.catan.resource.exception.NegativeNumberException;
import java.util.Map;
import java.util.TreeMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class OwnedElementTest {

  @DisplayName("It must return its cost")
  @Tag(value = "OwnedElement")
  @Test
  public void itMustReturnItsCost() throws NegativeNumberException {
    IPlayer player = new Player(0, new ResourceManager());

    Map<ResourceType, Integer> costMap = new TreeMap<ResourceType, Integer>();
    costMap.put(ResourceType.Wool, 4);

    IResourceManager cost = new ResourceManager(costMap);
    OwnedElement element = new MinimunOwnedElement(BoardElementType.Structure, cost, player);

    assertEquals(cost, element.getCost());
  }

  @DisplayName("It must return its owner")
  @Tag(value = "OwnedElement")
  @Test
  public void itMustReturnItsOwner() throws NegativeNumberException {
    IPlayer player = new Player(0, new ResourceManager());
    OwnedElement element =
        new MinimunOwnedElement(BoardElementType.Structure, new ResourceManager(), player);

    assertSame(player, element.getOwner());
  }
}
