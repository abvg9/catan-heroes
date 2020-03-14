package com.ucm.dasi.catan.warehouse;

import com.ucm.dasi.catan.resource.ResourceType;
import com.ucm.dasi.catan.warehouse.exception.NegativeNumberException;
import com.ucm.dasi.catan.warehouse.exception.NotEnoughtResourcesException;

import jade.util.leap.Serializable;

public interface IWarehouse extends Serializable {

    /* Methods */

    void add(Warehouse resourcesToPay) throws NegativeNumberException;

    int getQuantityResource();

    int getResource(ResourceType resource);

    void substract(Warehouse resourcesToPay) throws NotEnoughtResourcesException;

}
