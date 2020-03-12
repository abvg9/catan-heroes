package com.ucm.dasi.catan.board.connection;

import java.util.Map;
import java.util.TreeMap;

import com.ucm.dasi.catan.board.BoardElementType;
import com.ucm.dasi.catan.board.element.OwnedElement;
import com.ucm.dasi.catan.player.IPlayer;
import com.ucm.dasi.catan.resource.ResourceType;
import com.ucm.dasi.catan.warehouse.IWarehouse;
import com.ucm.dasi.catan.warehouse.Warehouse;
import com.ucm.dasi.catan.warehouse.exception.NegativeNumberException;

public class BoardConnection extends OwnedElement implements IBoardConnection {

    protected ConnectionType type;

    public BoardConnection(IPlayer owner, ConnectionType type) {
	super(BoardElementType.Connection, BoardConnection.buildCostFromType(type), owner);

	this.type = type;
    }

    @Override
    public ConnectionType getType() {
	return type;
    }

    private static IWarehouse buildCostFromType(ConnectionType type) {
	switch (type) {
	case Road: {
	    return buildRoadCost();
	}
	default:
	    return new Warehouse();
	}
    }

    private static IWarehouse buildRoadCost() {
	Map<ResourceType, Integer> costMap = new TreeMap<ResourceType, Integer>();
	costMap.put(ResourceType.Brick, 1);
	costMap.put(ResourceType.Lumber, 1);
	try {
	    return new Warehouse(costMap);
	} catch (NegativeNumberException e) {
	    return new Warehouse();
	}
    }
}
