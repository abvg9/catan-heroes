package com.catanheroes.core.resource;

import static org.junit.jupiter.api.Assertions.assertSame;

import com.catanheroes.core.resource.exception.NotEnoughtResourcesException;
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

    ResourceManager warehouse = new ResourceManager();

    warehouse.add(new ResourceManager(resources));

    assertSame(10, warehouse.getResourcesQuantity());
  }

  @DisplayName("It must substract a resource manager")
  @Tag(value = "ResourceManager")
  @Test
  public void checkQuantitySubstract() throws NotEnoughtResourcesException {

    Map<ResourceType, Integer> resources1 =
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

    Map<ResourceType, Integer> resources2 =
        Map.of(
            ResourceType.ORE,
            2,
            ResourceType.BRICK,
            2,
            ResourceType.WOOL,
            3,
            ResourceType.LUMBER,
            2,
            ResourceType.GRAIN,
            2);

    ResourceManager warehouse1 = new ResourceManager(resources1);
    ResourceManager warehouse2 = new ResourceManager(resources2);

    warehouse2.substract(warehouse1);

    assertSame(1, warehouse2.getResourcesQuantity());
  }

  @DisplayName("It must add a resource manager and return the resulting values")
  @Tag(value = "ResourceManager")
  @Test
  public void checkResources() {

    Map<ResourceType, Integer> resources1 =
        Map.of(
            ResourceType.ORE,
            1,
            ResourceType.BRICK,
            2,
            ResourceType.WOOL,
            3,
            ResourceType.LUMBER,
            4,
            ResourceType.GRAIN,
            5);

    Map<ResourceType, Integer> resources2 =
        Map.of(
            ResourceType.ORE,
            9,
            ResourceType.BRICK,
            8,
            ResourceType.WOOL,
            7,
            ResourceType.LUMBER,
            6,
            ResourceType.GRAIN,
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
