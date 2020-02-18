package com.ucm.dasi.catan.warehouse;

import static org.junit.Assert.assertEquals;
import java.util.Map;
import org.junit.Test;
import com.ucm.dasi.catan.warehouse.exception.NegativeNumberException;
import com.ucm.dasi.catan.warehouse.exception.NotEnoughtResourcesException;

public class WarehouseTest {
	
	public WarehouseTest() {
		
	}
	
	//@Before
	//public void setUp() {}
	
	/* CHECK QUANTITY TESTS */
	
    @Test
	public void checkQuantityNoEmptyConstructor() {
		 	
		try {
			
			Map<ResourceType, Integer> resources = 
					Map.of(ResourceType.Ore, 2, 
							ResourceType.Brick, 2,
							ResourceType.Wool, 2,
							ResourceType.Lumber, 2,
							ResourceType.Gain, 2);
			
			Warehouse warehouse = new Warehouse(resources);
			assertEquals(warehouse.getQuantityResource(), 10, 0);
			
		} catch (NegativeNumberException e) {
			System.err.println(e.getMessage());
		}
				
	}
    
    @Test
	public void checkQuantityEmptyConstructor() {
		Warehouse warehouse = new Warehouse();
		assertEquals(warehouse.getQuantityResource(), 0, 0);
	}
    
    @Test
	public void checkQuantityAdd() {
    	
		Map<ResourceType, Integer> resources = 
				Map.of(ResourceType.Ore, 2, 
						ResourceType.Brick, 2,
						ResourceType.Wool, 2,
						ResourceType.Lumber, 2,
						ResourceType.Gain, 2);
    	
		Warehouse warehouse = new Warehouse();
		
		try {
			warehouse.add(new Warehouse(resources));
		} catch (NegativeNumberException e) {
			System.err.println(e.getMessage());
		}
		
		assertEquals(warehouse.getQuantityResource(), 10, 0);
	}
    
    @Test
	public void checkQuantitySubstract() {
    	
		Map<ResourceType, Integer> resources1 = 
				Map.of(ResourceType.Ore, 2, 
						ResourceType.Brick, 2,
						ResourceType.Wool, 2,
						ResourceType.Lumber, 2,
						ResourceType.Gain, 2);
		
		Map<ResourceType, Integer> resources2 = 
				Map.of(ResourceType.Ore, 2, 
						ResourceType.Brick, 2,
						ResourceType.Wool, 3,
						ResourceType.Lumber, 2,
						ResourceType.Gain, 2);
    			
		try {
			Warehouse warehouse1 = new Warehouse(resources1);
			Warehouse warehouse2 = new Warehouse(resources2);
			
			try {
				warehouse2.substract(warehouse1);
			} catch (NotEnoughtResourcesException e) {
				System.err.println(e.getMessage());
			}
			
			assertEquals(warehouse2.getQuantityResource(), 1, 0);
			
		} catch (NegativeNumberException e) {
			System.err.println(e.getMessage());
		}
			
	}
    
    @Test
	public void checkQuantityAddSubstract() {
    	
		Map<ResourceType, Integer> resources1 = 
				Map.of(ResourceType.Ore, 2, 
						ResourceType.Brick, 2,
						ResourceType.Wool, 2,
						ResourceType.Lumber, 2,
						ResourceType.Gain, 2);
		
		Map<ResourceType, Integer> resources2 = 
				Map.of(ResourceType.Ore, 2, 
						ResourceType.Brick, 2,
						ResourceType.Wool, 3,
						ResourceType.Lumber, 2,
						ResourceType.Gain, 2);
    			
		try {
			Warehouse warehouse1 = new Warehouse(resources1);
			Warehouse warehouse2 = new Warehouse(resources2);
			
			try {
				warehouse2.add(warehouse1);
				warehouse2.substract(warehouse1);
			} catch (NotEnoughtResourcesException e) {
				System.err.println(e.getMessage());
			}
			
			assertEquals(warehouse2.getQuantityResource(), 11, 0);
			
		} catch (NegativeNumberException e) {
			System.err.println(e.getMessage());
		}
			
	}
    
    /* COHERENCE WAREHOSE TESTS */
    
    @Test
    public void checkResources() {
    	
		Map<ResourceType, Integer> resources1 = 
				Map.of(ResourceType.Ore, 1, 
						ResourceType.Brick, 2,
						ResourceType.Wool, 3,
						ResourceType.Lumber, 4,
						ResourceType.Gain, 5);
		
		Map<ResourceType, Integer> resources2 = 
				Map.of(ResourceType.Ore, 9, 
						ResourceType.Brick, 8,
						ResourceType.Wool, 7,
						ResourceType.Lumber, 6,
						ResourceType.Gain, 5);
    			
		try {
			Warehouse warehouse1 = new Warehouse(resources1);
			Warehouse warehouse2 = new Warehouse(resources2);
			
			Warehouse warehouse12 = warehouse1;
			warehouse12.add(warehouse2);
			
			for (ResourceType resourceType : ResourceType.values()) {
				assertEquals(warehouse12.getResource(resourceType), 10, 0);
			}
			
		} catch (NegativeNumberException e) {
			System.err.println(e.getMessage());
		}
		
    }
    
}
