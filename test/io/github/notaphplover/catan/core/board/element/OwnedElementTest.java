package io.github.notaphplover.catan.core.board.element;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import io.github.notaphplover.catan.core.board.BoardElementType;
import io.github.notaphplover.catan.core.player.IPlayer;
import io.github.notaphplover.catan.core.player.Player;
import io.github.notaphplover.catan.core.resource.IResourceManager;
import io.github.notaphplover.catan.core.resource.ResourceManager;
import io.github.notaphplover.catan.core.resource.ResourceType;
import java.util.Map;
import java.util.TreeMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class OwnedElementTest {

  @DisplayName("It must return its cost")
  @Tag(value = "OwnedElement")
  @Test
  public void itMustReturnItsCost() {
    IPlayer player = new Player(0, new ResourceManager());

    Map<ResourceType, Integer> costMap = new TreeMap<ResourceType, Integer>();
    costMap.put(ResourceType.WOOL, 4);

    IResourceManager cost = new ResourceManager(costMap);
    OwnedElement element = new MinimunOwnedElement(BoardElementType.STRUCTURE, cost, player);

    assertEquals(cost, element.getCost());
  }

  @DisplayName("It must return its owner")
  @Tag(value = "OwnedElement")
  @Test
  public void itMustReturnItsOwner() {
    IPlayer player = new Player(0, new ResourceManager());
    OwnedElement element =
        new MinimunOwnedElement(BoardElementType.STRUCTURE, new ResourceManager(), player);

    assertSame(player, element.getOwner());
  }
}
