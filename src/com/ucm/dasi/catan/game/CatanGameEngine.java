package com.ucm.dasi.catan.game;

import com.ucm.dasi.catan.board.ICatanBoard;
import com.ucm.dasi.catan.exception.NonNullInputException;
import com.ucm.dasi.catan.exception.NonVoidCollectionException;
import com.ucm.dasi.catan.player.IPlayer;
import com.ucm.dasi.catan.request.IRequest;

import java.util.TreeMap;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class CatanGameEngine extends CatanGame implements ICatanGameEngine {

    private TreeMap<Class<? extends IRequest>, Consumer<IRequest>> handlersMap;

    public CatanGameEngine(ICatanBoard board, IPlayer[] players,
	    Function<CatanGameEngine, IGameEngineHandlersMap> handlersMapGenerator)
	    throws NonNullInputException, NonVoidCollectionException {
	
	super(board, players);

	this.handlersMap = new GameEngineHandlersMap(handlersMapGenerator.apply(this));
    }

    @Override
    public void processRequests(IRequest[] requests) {
	for (IRequest request : requests) {
	    processTurnRequest(request);
	}
    }

    private void processTurnRequest(IRequest request) {
	Class<?>[] interfaces = request.getClass().getInterfaces();

	for (Class<?> interfaceType : interfaces) {
	    Consumer<IRequest> consumer = handlersMap.get(interfaceType);
	    if (consumer != null) {
		consumer.accept(request);
		break;
	    }
	}
    }

}
