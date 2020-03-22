package com.ucm.dasi.catan.game.handler;

import java.util.Map;
import java.util.TreeMap;
import java.util.function.Consumer;

import com.ucm.dasi.catan.lang.ComparableClass;
import com.ucm.dasi.catan.request.IRequest;

public class GameEngineHandlersMap extends TreeMap<ComparableClass<? extends IRequest>, Consumer<? extends IRequest>> implements IGameEngineHandlersMap {

    private static final long serialVersionUID = 3725544787318636351L;

    public GameEngineHandlersMap() {
	super();
    }
    
    public GameEngineHandlersMap(Map<ComparableClass<? extends IRequest>, Consumer<? extends IRequest>> map) {
	super(map);
    }
}
