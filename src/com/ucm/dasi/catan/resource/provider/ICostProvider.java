package com.ucm.dasi.catan.warehouse.provider;

import com.ucm.dasi.catan.warehouse.IWarehouse;

public interface ICostProvider<TType extends Comparable<TType>> {
    IWarehouse getCost(TType type);
}
