package com.ucm.dasi.catan.game;

import java.util.Map;
import java.util.TreeMap;
import java.util.function.Consumer;

import com.ucm.dasi.catan.request.IRequest;

public class GameEngineHandlersMap extends TreeMap<Class<? extends IRequest>, Consumer<IRequest>> implements IGameEngineHandlersMap {

    private static final long serialVersionUID = 3725544787318636351L;

    public GameEngineHandlersMap() {
	super();
    }
    
    public GameEngineHandlersMap(Map<Class<? extends IRequest>, Consumer<IRequest>> map) {
	super(map);
    }
}
