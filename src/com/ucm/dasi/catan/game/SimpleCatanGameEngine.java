package com.ucm.dasi.catan.game;

import java.util.function.Consumer;

import com.ucm.dasi.catan.board.ICatanEditableBoard;
import com.ucm.dasi.catan.board.connection.BoardConnection;
import com.ucm.dasi.catan.board.element.BoardElement;
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

public class SimpleCatanGameEngine extends CatanGameEngine {

    private Consumer<IRequest> errorHandler;

    public SimpleCatanGameEngine(ICatanEditableBoard board, IPlayer[] players, Consumer<IRequest> errorHandler)
	    throws NonNullInputException, NonVoidCollectionException {

	super(board, players, (engine) -> generateMap(engine));

	this.errorHandler = errorHandler;
    }

    @Override
    protected void handleRequestError(IRequest request) {
	errorHandler.accept(request);
    }

    private static IGameEngineHandlersMap generateMap(CatanGameEngine engine) {
	IGameEngineHandlersMap map = new GameEngineHandlersMap();

	map.put(IBuildConnectionRequest.class,
		(IBuildConnectionRequest request) -> handleBuildConnectionRequest(engine, request));
	map.put(IBuildStructureRequest.class,
		(IBuildStructureRequest request) -> handleBuildStructureRequest(engine, request));

	return map;
    }
    
    private static void handleBuildConnectionRequest(CatanGameEngine engine, IBuildConnectionRequest request) {
	BoardConnection element = new BoardConnection(request.getPlayer(), request.getType());

	try {
	    engine.getBoard().build(element, request.getX(), request.getY());
	    engine.getActivePlayer().getWarehouse().substract(element.getCost());
	} catch (InvalidBoardElementException | NotEnoughtResourcesException e) {
	    engine.handleRequestError(request);
	}
    }

    private static void handleBuildStructureRequest(CatanGameEngine engine, IBuildStructureRequest request) {
	BoardElement element = new BoardStructure(request.getPlayer(), request.getType());

	try {
	    engine.getBoard().build(element, request.getX(), request.getY());
	} catch (InvalidBoardElementException e) {
	    engine.handleRequestError(request);
	}
    }
}
