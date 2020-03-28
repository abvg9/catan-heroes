package com.ucm.dasi.catan.resource.production;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import com.ucm.dasi.catan.player.IPlayer;
import com.ucm.dasi.catan.player.Player;
import com.ucm.dasi.catan.resource.IResourceManager;
import com.ucm.dasi.catan.resource.ResourceManager;
import com.ucm.dasi.catan.resource.ResourceType;
import java.util.TreeMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class ResourceProductionTest {

  @DisplayName("It must store its production number")
  @Tag("ResourceProduction")
  @Test
  public void itMustStoreItsProductionNumber() {
    int productionNumber = 3;
    ResourceProduction resourceProduction =
        new ResourceProduction(productionNumber, new TreeMap<IPlayer, IResourceManager>());

    assertSame(productionNumber, resourceProduction.getProductionNumber());
  }

  @DisplayName("It must store get a player production")
  @Tag("ResourceProduction")
  @Test
  public void itMustGetAPlayerProduction() {

    IPlayer player = new Player(0, new ResourceManager());

    TreeMap<ResourceType, Integer> playerProductionMap = new TreeMap<ResourceType, Integer>();
    playerProductionMap.put(ResourceType.WOOL, 3);

    IResourceManager playerProduction = new ResourceManager(playerProductionMap);

    TreeMap<IPlayer, IResourceManager> productionMap = new TreeMap<IPlayer, IResourceManager>();
    productionMap.put(player, playerProduction);

    ResourceProduction resourceProduction = new ResourceProduction(5, productionMap);

    assertEquals(playerProduction, resourceProduction.getProduction(player));
  }
}
