package com.ucm.dasi.catan.game;

import com.ucm.dasi.catan.board.BoardElementType;
import com.ucm.dasi.catan.board.ICatanEditableBoard;
import com.ucm.dasi.catan.board.connection.BoardConnection;
import com.ucm.dasi.catan.board.element.IOwnedElement;
import com.ucm.dasi.catan.board.exception.InvalidBoardElementException;
import com.ucm.dasi.catan.board.structure.BoardStructure;
import com.ucm.dasi.catan.exception.NonNullInputException;
import com.ucm.dasi.catan.exception.NonVoidCollectionException;
import com.ucm.dasi.catan.game.handler.GameEngineHandlersMap;
import com.ucm.dasi.catan.game.handler.IGameEngineHandlersMap;
import com.ucm.dasi.catan.lang.ComparableClass;
import com.ucm.dasi.catan.player.IPlayer;
import com.ucm.dasi.catan.request.IBuildConnectionRequest;
import com.ucm.dasi.catan.request.IBuildStructureRequest;
import com.ucm.dasi.catan.request.IRequest;
import com.ucm.dasi.catan.resource.exception.NotEnoughtResourcesException;
import com.ucm.dasi.catan.resource.provider.ConnectionCostProvider;
import com.ucm.dasi.catan.resource.provider.StructureCostProvider;

import java.util.function.Consumer;

public class CatanGameEngine extends CatanGame<ICatanEditableBoard> implements ICatanGameEngine {

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

    private boolean isStructurePointConnected(IPlayer player, int x, int y) {
	return board.get(x, y).getElementType() == BoardElementType.Structure
		&& ((x > 0 && isStructureConnectedCheckConnection(player, (IOwnedElement) board.get(x - 1, y)))
			|| (x + 1 < board.getWidth()
				&& isStructureConnectedCheckConnection(player, (IOwnedElement) board.get(x + 1, y)))
			|| (y > 0 && isStructureConnectedCheckConnection(player, (IOwnedElement) board.get(x, y - 1)))
			|| (y + 1 > board.getHeight()
				&& isStructureConnectedCheckConnection(player, (IOwnedElement) board.get(x, y + 1))));
    }

    private boolean isStructureConnectedCheckConnection(IPlayer player, IOwnedElement element) {
	return element.getOwner() != null && element.getOwner().getId() == player.getId();
    }

    private void processTurnRequest(IRequest request) {
	Class<?>[] interfaces = request.getClass().getInterfaces();

	for (Class<?> interfaceType : interfaces) {
	    @SuppressWarnings({ "rawtypes", "unchecked" })
	    Consumer<? extends IRequest> consumer = handlersMap.get(new ComparableClass(interfaceType));
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

	map.put(new ComparableClass<IBuildConnectionRequest>(IBuildConnectionRequest.class),
		(IBuildConnectionRequest request) -> handleBuildConnectionRequest(request));
	map.put(new ComparableClass<IBuildStructureRequest>(IBuildStructureRequest.class),
		(IBuildStructureRequest request) -> handleBuildStructureRequest(request));

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
	
	if (!isStructurePointConnected(request.getPlayer(), request.getX(), request.getY())) {
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
