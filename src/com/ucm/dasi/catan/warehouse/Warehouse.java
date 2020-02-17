package com.ucm.dasi.catan.warehouse;

import java.util.Map;
import java.util.TreeMap;

import com.ucm.dasi.catan.warehouse.exceptions.ENegativeNumber;
import com.ucm.dasi.catan.warehouse.exceptions.ENotEnoughtResources;

public class Warehouse implements IWarehouse {

	/* Atributes */
	
	private TreeMap<ResourceType, Integer> resources;
	private int quantityResources;

	/* Constructors */
	
	public Warehouse() throws ENegativeNumber {
		
		resources = new TreeMap<ResourceType, Integer>();
		quantityResources = 0;
		
		for (ResourceType resourceType : ResourceType.values()) {
			resources.put(resourceType, 0);
		}
	}

	public Warehouse(Map<ResourceType, Integer> resources) throws ENegativeNumber {
		
		this();

		for (ResourceType resourceType : ResourceType.values()) {
			setResource(resourceType, resources.get(resourceType));
		}

	}

	/* Methods */

	public int getResource(ResourceType resource) {
		return resources.get(resource);
	}

	public int getQuantityResource() {
		return quantityResources;
	}

	public void substract(Warehouse resourcesToPay) throws ENotEnoughtResources {

		try {
			for (ResourceType resourceType : ResourceType.values()) {
				int resourceQuantity = resourcesToPay.getResource(resourceType);
				setResource(resourceType, resources.get(resourceType) - resourceQuantity);
			}
		} catch (ENegativeNumber e) {
			throw new ENotEnoughtResources();
		}
	}

	public void add(Warehouse resourcesToPay) throws ENegativeNumber {

		for (ResourceType resourceType : ResourceType.values()) {
			int resourceQuantity = resourcesToPay.getResource(resourceType);
			setResource(resourceType, resources.get(resourceType) + resourceQuantity);
		}
	}

	protected void setResource(ResourceType resource, int newQuantity) throws ENegativeNumber {

		if (newQuantity < 0) {
			throw new ENegativeNumber(resource.toString());
		}

		int oldQuantity = resources.get(resource);
		quantityResources += newQuantity - oldQuantity;

		resources.put(resource, newQuantity);
	}

}
