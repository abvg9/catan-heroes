package com.ucm.dasi.catan.warehouse;

import com.ucm.dasi.catan.resource.ResourceType;
import com.ucm.dasi.catan.warehouse.exception.NegativeNumberException;
import com.ucm.dasi.catan.warehouse.exception.NotEnoughtResourcesException;

public interface IWarehouse {

    /* Methods */

    void add(IWarehouse resourcesToPay) throws NegativeNumberException;

    int getQuantityResource();

    int getResource(ResourceType resource);

    void substract(IWarehouse resourcesToPay) throws NotEnoughtResourcesException;

}
