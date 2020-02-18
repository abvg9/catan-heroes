package com.ucm.dasi.catan.warehouse;

import com.ucm.dasi.catan.warehouse.exception.NegativeNumberException;
import com.ucm.dasi.catan.warehouse.exception.NotEnoughtResourcesException;

public interface IWarehouse {

	/* Methods */

	int getResource(ResourceType resource);

	int getQuantityResource();
	
	void substract(Warehouse resourcesToPay) throws NotEnoughtResourcesException ;

	void add(Warehouse resourcesToPay) throws NegativeNumberException;

}
