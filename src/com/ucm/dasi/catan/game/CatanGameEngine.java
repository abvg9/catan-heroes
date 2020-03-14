package com.ucm.dasi.catan.game;

import com.ucm.dasi.catan.board.ICatanEditableBoard;
import com.ucm.dasi.catan.board.connection.BoardConnection;
import com.ucm.dasi.catan.board.exception.InvalidBoardElementException;
import com.ucm.dasi.catan.board.structure.BoardStructure;
import com.ucm.dasi.catan.exception.NonNullInputException;
import com.ucm.dasi.catan.exception.NonVoidCollectionException;
import com.ucm.dasi.catan.game.handler.GameEngineHandlersMap;
import com.ucm.dasi.catan.game.handler.IGameEngineHandlersMap;
import com.ucm.dasi.catan.player.IPlayer;
import com.ucm.dasi.catan.request.IBuildConnectionRequest;
import com.ucm.dasi.catan.request.IBuildStructureRequest;
import com.ucm.dasi.catan.request.IRequest;
import com.ucm.dasi.catan.warehouse.exception.NotEnoughtResourcesException;
import com.ucm.dasi.catan.warehouse.provider.ConnectionCostProvider;
import com.ucm.dasi.catan.warehouse.provider.StructureCostProvider;

import java.util.function.Consumer;

public abstract class CatanGameEngine extends CatanGame<ICatanEditableBoard> implements ICatanGameEngine {

    private ConnectionCostProvider connectionCostProvider;

    private StructureCostProvider structureCostProvider;

    private Consumer<IRequest> errorHandler;

    private IGameEngineHandlersMap handlersMap;

    public CatanGameEngine(ICatanEditableBoard board, IPlayer[] players, Consumer<IRequest> errorHandler)
	    throws NonNullInputException, NonVoidCollectionException {

	super(board, players);

	connectionCostProvider = ConnectionCostProvider.buildDefaultProvider();
	structureCostProvider = StructureCostProvider.buildDefaultProvider();
	this.errorHandler = errorHandler;
	this.handlersMap = new GameEngineHandlersMap(generateMap());
    }

    @Override
    public void processRequests(IRequest[] requests) {
	for (IRequest request : requests) {
	    processTurnRequest(request);
	}
    }

    protected void handleRequestError(IRequest request) {
	errorHandler.accept(request);
    }

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
	consumer.accept((T) request);
    }

    private IGameEngineHandlersMap generateMap() {
	IGameEngineHandlersMap map = new GameEngineHandlersMap();

	map.put(IBuildConnectionRequest.class,
		(IBuildConnectionRequest request) -> handleBuildConnectionRequest(request));
	map.put(IBuildStructureRequest.class, (IBuildStructureRequest request) -> handleBuildStructureRequest(request));

	return map;
    }

    private void handleBuildConnectionRequest(IBuildConnectionRequest request) {
	if (request.getPlayer().getId() != getActivePlayer().getId()) {
	    handleRequestError(request);
	    return;
	}

	BoardConnection element = new BoardConnection(request.getPlayer(),
		connectionCostProvider.getCost(request.getType()), request.getType());

	try {
	    getBoard().build(element, request.getX(), request.getY());
	    getActivePlayer().getWarehouse().substract(element.getCost());
	} catch (InvalidBoardElementException | NotEnoughtResourcesException e) {
	    handleRequestError(request);
	}
    }

    private void handleBuildStructureRequest(IBuildStructureRequest request) {
	if (request.getPlayer().getId() != getActivePlayer().getId()) {
	    handleRequestError(request);
	    return;
	}

	BoardStructure element = new BoardStructure(request.getPlayer(),
		structureCostProvider.getCost(request.getType()), request.getType());

	try {
	    getBoard().build(element, request.getX(), request.getY());
	    getActivePlayer().getWarehouse().substract(element.getCost());
	} catch (InvalidBoardElementException | NotEnoughtResourcesException e) {
	    handleRequestError(request);
	}
    }

}
