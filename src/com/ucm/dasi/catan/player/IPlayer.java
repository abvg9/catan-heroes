package com.ucm.dasi.catan.player;

import com.ucm.dasi.catan.warehouse.IWarehouse;

public interface IPlayer {
    int getId();
    
    IWarehouse getWarehouse();
}
