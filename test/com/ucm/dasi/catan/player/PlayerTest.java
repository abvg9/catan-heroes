package com.ucm.dasi.catan.player;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ucm.dasi.catan.resource.IResourceManager;
import com.ucm.dasi.catan.resource.ResourceManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class PlayerTest {

  @DisplayName("It must return its id")
  @Tag(value = "Player")
  @Test
  public void itMustReturnItsId() {
    int id = 0;
    Player player = new Player(id, new ResourceManager());

    assertEquals(id, player.getId());
  }

  @DisplayName("It must return its  resource manager")
  @Tag(value = "Player")
  @Test
  public void itMustReturnItsResourceManager() {
    IResourceManager warehouse = new ResourceManager();
    Player player = new Player(0, warehouse);

    assertEquals(warehouse, player.getResourceManager());
  }
}
