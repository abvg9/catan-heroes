package com.ucm.dasi.catan.resource;

import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class ResourceStorageTest {

  @DisplayName("It must get a resource amount")
  @Tag("ResourceStorage")
  @Test
  public void itMustGetAResourceAmount() {
    Map<ResourceType, Integer> resources =
        Map.of(
            ResourceType.ORE,
            1,
            ResourceType.BRICK,
            2,
            ResourceType.WOOL,
            3,
            ResourceType.LUMBER,
            4);

    ResourceStorage storage = new ResourceStorage(resources);

    assertSame(1, storage.getResource(ResourceType.ORE));
    assertSame(2, storage.getResource(ResourceType.BRICK));
    assertSame(3, storage.getResource(ResourceType.WOOL));
    assertSame(4, storage.getResource(ResourceType.LUMBER));
    assertSame(0, storage.getResource(ResourceType.GRAIN));
  }

  @DisplayName("It must get the total amount of resources")
  @Tag(value = "ResourceStorage")
  @Test
  public void itMustGetResourcesAmountI() {

    Map<ResourceType, Integer> resources =
        Map.of(
            ResourceType.ORE,
            2,
            ResourceType.BRICK,
            2,
            ResourceType.WOOL,
            2,
            ResourceType.LUMBER,
            2,
            ResourceType.GRAIN,
            2);

    ResourceStorage storage = new ResourceStorage(resources);

    assertSame(10, storage.getResourcesQuantity());
  }

  @DisplayName("It must get the total amount of resources of an empty resource manager")
  @Tag(value = "ResourceStorage")
  @Test
  public void itMustGetResourcesAmountII() {

    ResourceStorage storage = new ResourceStorage();

    assertSame(0, storage.getResourcesQuantity());
  }
}
