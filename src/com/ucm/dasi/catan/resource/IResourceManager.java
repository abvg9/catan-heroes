package com.ucm.dasi.catan.resource;

import com.ucm.dasi.catan.resource.exception.NegativeNumberException;
import com.ucm.dasi.catan.resource.exception.NotEnoughtResourcesException;

public interface IResourceManager {

    /* Methods */

    void add(IResourceManager resourcesToPay) throws NegativeNumberException;

    int getQuantityResource();

    int getResource(ResourceType resource);

    void substract(IResourceManager resourcesToPay) throws NotEnoughtResourcesException;

}
