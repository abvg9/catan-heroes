package com.ucm.dasi.catan.warehouse;

import com.ucm.dasi.catan.warehouse.exceptions.ENegativeNumber;
import com.ucm.dasi.catan.warehouse.exceptions.ENotEnoughtResources;

public interface IWarehouse {

	/* Methods */

	int getResource(ResourceType resource);

	int getQuantityResource();
	
	void substract(Warehouse resourcesToPay) throws ENotEnoughtResources ;

	void add(Warehouse resourcesToPay) throws ENegativeNumber;

}
