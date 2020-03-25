package com.ucm.dasi.catan.resource;

import static org.junit.jupiter.api.Assertions.assertSame;

import com.ucm.dasi.catan.resource.exception.NotEnoughtResourcesException;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class ResourceManagerTest {

  @DisplayName("It must add a resource manager")
  @Tag(value = "ResourceManager")
  @Test
  public void checkQuantityAdd() {

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

    ResourceManager warehouse = new ResourceManager();

    warehouse.add(new ResourceManager(resources));

    assertSame(10, warehouse.getQuantityResource());
  }

  @DisplayName("It must substract a resource manager")
  @Tag(value = "ResourceManager")
  @Test
  public void checkQuantitySubstract() throws NotEnoughtResourcesException {

    Map<ResourceType, Integer> resources1 =
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

    Map<ResourceType, Integer> resources2 =
        Map.of(
            ResourceType.Ore,
            2,
            ResourceType.Brick,
            2,
            ResourceType.Wool,
            3,
            ResourceType.Lumber,
            2,
            ResourceType.Grain,
            2);

    ResourceManager warehouse1 = new ResourceManager(resources1);
    ResourceManager warehouse2 = new ResourceManager(resources2);

    warehouse2.substract(warehouse1);

    assertSame(1, warehouse2.getQuantityResource());
  }

  @DisplayName("It must add a resource manager and return the resulting values")
  @Tag(value = "ResourceManager")
  @Test
  public void checkResources() {

    Map<ResourceType, Integer> resources1 =
        Map.of(
            ResourceType.Ore,
            1,
            ResourceType.Brick,
            2,
            ResourceType.Wool,
            3,
            ResourceType.Lumber,
            4,
            ResourceType.Grain,
            5);

    Map<ResourceType, Integer> resources2 =
        Map.of(
            ResourceType.Ore,
            9,
            ResourceType.Brick,
            8,
            ResourceType.Wool,
            7,
            ResourceType.Lumber,
            6,
            ResourceType.Grain,
            5);

    ResourceManager warehouse1 = new ResourceManager(resources1);
    ResourceManager warehouse2 = new ResourceManager(resources2);

    ResourceManager warehouse12 = warehouse1;
    warehouse12.add(warehouse2);

    for (ResourceType resourceType : ResourceType.values()) {
      assertSame(10, warehouse12.getResource(resourceType));
    }
  }
}
