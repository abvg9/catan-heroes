package com.ucm.dasi.catan.resource.provider;

import java.util.Map;
import java.util.TreeMap;

import com.ucm.dasi.catan.resource.IResourceManager;
import com.ucm.dasi.catan.resource.ResourceManager;
import com.ucm.dasi.catan.resource.exception.NegativeNumberException;

public class CostProvider<TType extends Comparable<TType>> implements ICostProvider<TType> {

    protected TreeMap<TType, IResourceManager> costMap;
    
    public CostProvider(Map<TType, IResourceManager> costMap) {
	this.costMap = new TreeMap<TType, IResourceManager>(costMap);
    }
    
    @Override
    public IResourceManager getCost(TType type) {
	IResourceManager storedCost = this.costMap.get(type);
	
	try {
	    return storedCost == null ? new ResourceManager() : new ResourceManager(storedCost);
	} catch (NegativeNumberException e) {
	    return new ResourceManager();
	}
    }
}
