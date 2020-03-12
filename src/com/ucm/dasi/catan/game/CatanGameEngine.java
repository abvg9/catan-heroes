package com.ucm.dasi.catan.game;

import com.ucm.dasi.catan.board.ICatanEditableBoard;
import com.ucm.dasi.catan.exception.NonNullInputException;
import com.ucm.dasi.catan.exception.NonVoidCollectionException;
import com.ucm.dasi.catan.game.handler.GameEngineHandlersMap;
import com.ucm.dasi.catan.game.handler.IGameEngineHandlersMap;
import com.ucm.dasi.catan.player.IPlayer;
import com.ucm.dasi.catan.request.IRequest;

import java.util.function.Consumer;
import java.util.function.Function;

public abstract class CatanGameEngine extends CatanGame<ICatanEditableBoard> implements ICatanGameEngine {

    private IGameEngineHandlersMap handlersMap;

    public CatanGameEngine(ICatanEditableBoard board, IPlayer[] players,
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

    protected abstract void handleRequestError(IRequest request);
    
    private void processTurnRequest(IRequest request) {
	Class<?>[] interfaces = request.getClass().getInterfaces();

	for (Class<?> interfaceType : interfaces) {
	    Consumer<? extends IRequest> consumer = handlersMap.get(interfaceType);
	    if (consumer != null) {
		consumeRequest(consumer, request);
		break;
	    }
	}
    }
    
    @SuppressWarnings("unchecked")
    private <T extends IRequest> void consumeRequest(Consumer<T> consumer, IRequest request) {
	consumer.accept((T)request);
    }

}
