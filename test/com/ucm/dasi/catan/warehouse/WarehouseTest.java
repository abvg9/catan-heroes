package com.ucm.dasi.catan.warehouse;

import static org.junit.Assert.assertEquals;
import java.util.Map;
import org.junit.Test;

import com.ucm.dasi.catan.resource.ResourceType;
import com.ucm.dasi.catan.resource.ResourceManager;
import com.ucm.dasi.catan.resource.exception.NegativeNumberException;
import com.ucm.dasi.catan.resource.exception.NotEnoughtResourcesException;

public class WarehouseTest {

    /* CHECK QUANTITY TESTS */

    @Test
    public void checkQuantityNoEmptyConstructor() throws NegativeNumberException {

	Map<ResourceType, Integer> resources = Map.of(ResourceType.Ore, 2, ResourceType.Brick, 2, ResourceType.Wool, 2,
		ResourceType.Lumber, 2, ResourceType.Grain, 2);

	ResourceManager warehouse = new ResourceManager(resources);

	assertEquals(warehouse.getQuantityResource(), 10, 0);

    }

    @Test
    public void checkQuantityEmptyConstructor() {

	ResourceManager warehouse = new ResourceManager();

	assertEquals(warehouse.getQuantityResource(), 0, 0);

    }

    @Test
    public void checkQuantityAdd() throws NegativeNumberException {

	Map<ResourceType, Integer> resources = Map.of(ResourceType.Ore, 2, ResourceType.Brick, 2, ResourceType.Wool, 2,
		ResourceType.Lumber, 2, ResourceType.Grain, 2);

	ResourceManager warehouse = new ResourceManager();

	warehouse.add(new ResourceManager(resources));

	assertEquals(warehouse.getQuantityResource(), 10, 0);
    }

    @Test
    public void checkQuantitySubstract() throws NegativeNumberException, NotEnoughtResourcesException {

	Map<ResourceType, Integer> resources1 = Map.of(ResourceType.Ore, 2, ResourceType.Brick, 2, ResourceType.Wool, 2,
		ResourceType.Lumber, 2, ResourceType.Grain, 2);

	Map<ResourceType, Integer> resources2 = Map.of(ResourceType.Ore, 2, ResourceType.Brick, 2, ResourceType.Wool, 3,
		ResourceType.Lumber, 2, ResourceType.Grain, 2);

	ResourceManager warehouse1 = new ResourceManager(resources1);
	ResourceManager warehouse2 = new ResourceManager(resources2);

	warehouse2.substract(warehouse1);

	assertEquals(warehouse2.getQuantityResource(), 1, 0);

    }

    /* COHERENCE WAREHOSE TESTS */

    @Test
    public void checkResources() throws NegativeNumberException {

	Map<ResourceType, Integer> resources1 = Map.of(ResourceType.Ore, 1, ResourceType.Brick, 2, ResourceType.Wool, 3,
		ResourceType.Lumber, 4, ResourceType.Grain, 5);

	Map<ResourceType, Integer> resources2 = Map.of(ResourceType.Ore, 9, ResourceType.Brick, 8, ResourceType.Wool, 7,
		ResourceType.Lumber, 6, ResourceType.Grain, 5);

	ResourceManager warehouse1 = new ResourceManager(resources1);
	ResourceManager warehouse2 = new ResourceManager(resources2);

	ResourceManager warehouse12 = warehouse1;
	warehouse12.add(warehouse2);

	for (ResourceType resourceType : ResourceType.values()) {
	    assertEquals(warehouse12.getResource(resourceType), 10, 0);
	}

    }

}
