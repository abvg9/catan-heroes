package com.ucm.dasi.catan.resource;

import static org.junit.jupiter.api.Assertions.assertSame;

import com.ucm.dasi.catan.resource.exception.NegativeNumberException;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class ResourceStorageTest {

  @DisplayName("It must get a resource amount")
  @Tag("ResourceStorage")
  @Test
  public void itMustGetAResourceAmount() throws NegativeNumberException {
    Map<ResourceType, Integer> resources =
        Map.of(
            ResourceType.Ore,
            1,
            ResourceType.Brick,
            2,
            ResourceType.Wool,
            3,
            ResourceType.Lumber,
            4);

    ResourceStorage storage = new ResourceStorage(resources);

    assertSame(1, storage.getResource(ResourceType.Ore));
    assertSame(2, storage.getResource(ResourceType.Brick));
    assertSame(3, storage.getResource(ResourceType.Wool));
    assertSame(4, storage.getResource(ResourceType.Lumber));
    assertSame(0, storage.getResource(ResourceType.Grain));
  }

  @DisplayName("It must get the total amount of resources")
  @Tag(value = "ResourceStorage")
  @Test
  public void itMustGetResourcesAmountI() throws NegativeNumberException {

    Map<ResourceType, Integer> resources =
        Map.of(
            ResourceType.Ore,
            2,
            ResourceType.Brick,
            2,
            ResourceType.Wool,
            2,
            ResourceType.Lumber,
            2,
            ResourceType.Grain,
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
