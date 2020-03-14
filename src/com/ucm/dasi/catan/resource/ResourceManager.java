package com.ucm.dasi.catan.resource;

import java.util.Map;
import java.util.TreeMap;

import com.ucm.dasi.catan.resource.exception.NegativeNumberException;
import com.ucm.dasi.catan.resource.exception.NotEnoughtResourcesException;

public class ResourceManager implements IResourceManager {

    /* Atributes */

    private TreeMap<ResourceType, Integer> resources;
    private int quantityResources;

    /* Constructors */

    public ResourceManager() {

	resources = new TreeMap<ResourceType, Integer>();
	quantityResources = 0;

	for (ResourceType resourceType : ResourceType.values()) {
	    resources.put(resourceType, 0);
	}
    }

    public ResourceManager(Map<ResourceType, Integer> resources) throws NegativeNumberException {
	this();

	for (ResourceType resourceType : ResourceType.values()) {
	    Integer resourceAmount = resources.get(resourceType);
	    if (null == resourceAmount) {
		resourceAmount = 0;
	    }
	    setResource(resourceType, resourceAmount);
	}

    }
    
    public ResourceManager(IResourceManager warehouse) throws NegativeNumberException {
	this();

	for (ResourceType resourceType : ResourceType.values()) {   
	    setResource(resourceType, warehouse.getResource(resourceType));
	}
    }

    /* Methods */
    
    @Override
    public boolean equals(Object object) {
	if (!(object instanceof IResourceManager)) {
	    return false;
	}
	
	for (ResourceType resourceType : ResourceType.values()) {   
	    if (this.getResource(resourceType) != ((IResourceManager)object).getResource(resourceType)) {
		return false;
	    }
	}
	
	return true;
    }
    
    public int getResource(ResourceType resource) {
	return resources.get(resource);
    }

    public int getQuantityResource() {
	return quantityResources;
    }

    public void substract(IResourceManager resourcesToPay) throws NotEnoughtResourcesException {

	try {
	    for (ResourceType resourceType : ResourceType.values()) {
		int resourceQuantity = resourcesToPay.getResource(resourceType);
		setResource(resourceType, resources.get(resourceType) - resourceQuantity);
	    }
	} catch (NegativeNumberException e) {
	    throw new NotEnoughtResourcesException();
	}
    }

    public void add(IResourceManager resourcesToPay) throws NegativeNumberException {

	for (ResourceType resourceType : ResourceType.values()) {
	    int resourceQuantity = resourcesToPay.getResource(resourceType);
	    setResource(resourceType, resources.get(resourceType) + resourceQuantity);
	}
    }

    protected void setResource(ResourceType resource, int newQuantity) throws NegativeNumberException {

	if (newQuantity < 0) {
	    throw new NegativeNumberException(resource.toString());
	}

	int oldQuantity = resources.get(resource);
	quantityResources += newQuantity - oldQuantity;

	resources.put(resource, newQuantity);
    }

}
