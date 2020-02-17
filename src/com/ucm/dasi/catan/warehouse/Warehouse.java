package com.ucm.dasi.catan.warehouse;

import java.util.Map;
import java.util.TreeMap;

import com.ucm.dasi.catan.warehouse.enums.Resource;
import com.ucm.dasi.catan.warehouse.exceptions.ENegativeNumber;
import com.ucm.dasi.catan.warehouse.exceptions.ENotEnoughtResources;

public class Warehouse {

	/* Atributes */
	private TreeMap<Resource, Integer> resources;
	private int quantityResources;

	/* Constructors */
	public Warehouse() throws ENegativeNumber {
		
		initializeAttributes();
		
		for (Resource resourceType : Resource.values()) {
			setResource(resourceType, 0);
		}
	}

	public Warehouse(Map<Resource, Integer> resources) throws ENegativeNumber {
		
		this();

		for (Resource resourceType : Resource.values()) {
			setResource(resourceType, resources.get(resourceType));
		}

	}

	/* Getters and Setters */

	public int getResource(Resource resource) {
		return resources.get(resource);
	}

	public int getQuantityResource() {
		return quantityResources;
	}

	/* Methods */

	public void substract(Warehouse resourcesToPay) throws ENotEnoughtResources {

		try {
			for (Resource resourceType : Resource.values()) {
				int resourceQuantity = resourcesToPay.getResource(resourceType);
				setResource(resourceType, resources.get(resourceType) - resourceQuantity);
			}
		} catch (ENegativeNumber e) {
			throw new ENotEnoughtResources();
		}
	}

	public void add(Warehouse resourcesToPay) throws ENegativeNumber {

		for (Resource resourceType : Resource.values()) {
			int resourceQuantity = resourcesToPay.getResource(resourceType);
			setResource(resourceType, resources.get(resourceType) + resourceQuantity);
		}
	}

	protected void setResource(Resource resource, int newQuantity) throws ENegativeNumber {

		if (newQuantity < 0) {
			throw new ENegativeNumber(resource.toString());
		}

		int oldQuantity = resources.get(resource);
		quantityResources += newQuantity - oldQuantity;

		resources.put(resource, newQuantity);
	}
	
	private void initializeAttributes() {
		resources = new TreeMap<Resource, Integer>();
		quantityResources = 0;
	}
}
