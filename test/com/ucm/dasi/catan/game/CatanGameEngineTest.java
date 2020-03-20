package com.ucm.dasi.catan.game;

import static org.junit.Assert.assertSame;

import java.util.Map;
import java.util.TreeMap;
import java.util.function.Consumer;

import org.junit.Test;

import com.ucm.dasi.catan.board.BoardElementType;
import com.ucm.dasi.catan.board.CatanEditableBoard;
import com.ucm.dasi.catan.board.ICatanEditableBoard;
import com.ucm.dasi.catan.board.connection.BoardConnection;
import com.ucm.dasi.catan.board.connection.ConnectionType;
import com.ucm.dasi.catan.board.connection.IBoardConnection;
import com.ucm.dasi.catan.board.element.IBoardElement;
import com.ucm.dasi.catan.board.element.IOwnedElement;
import com.ucm.dasi.catan.board.exception.InvalidBoardDimensionsException;
import com.ucm.dasi.catan.board.exception.InvalidBoardElementException;
import com.ucm.dasi.catan.board.structure.BoardStructure;
import com.ucm.dasi.catan.board.structure.IBoardStructure;
import com.ucm.dasi.catan.board.structure.StructureType;
import com.ucm.dasi.catan.board.terrain.BoardTerrain;
import com.ucm.dasi.catan.board.terrain.IBoardTerrain;
import com.ucm.dasi.catan.board.terrain.TerrainType;
import com.ucm.dasi.catan.exception.NonNullInputException;
import com.ucm.dasi.catan.exception.NonVoidCollectionException;
import com.ucm.dasi.catan.player.IPlayer;
import com.ucm.dasi.catan.player.Player;
import com.ucm.dasi.catan.request.BuildStructureRequest;
import com.ucm.dasi.catan.request.IRequest;
import com.ucm.dasi.catan.resource.ResourceManager;
import com.ucm.dasi.catan.resource.ResourceType;
import com.ucm.dasi.catan.resource.exception.NegativeNumberException;

public class CatanGameEngineTest {

    @Test
    public void itMustProcessAValidBuildStructureRequest() throws InvalidBoardDimensionsException, InvalidBoardElementException,
	    NegativeNumberException, NonNullInputException, NonVoidCollectionException {

	Map<ResourceType, Integer> playerResources = new TreeMap<ResourceType, Integer>();
	
	playerResources.put(ResourceType.Brick, 1);
	playerResources.put(ResourceType.Grain, 1);
	playerResources.put(ResourceType.Lumber, 1);
	playerResources.put(ResourceType.Wool, 1);
	
	IPlayer player = new Player(0, new ResourceManager(playerResources));
	IPlayer[] players = { player };
	ICatanEditableBoard board = buildStandardBoard(player);
	Consumer<IRequest> errorHandler = (request) -> {
	};

	CatanGameEngine engine = new CatanGameEngine(board, players, errorHandler);

	IRequest[] requests = { new BuildStructureRequest(player, StructureType.Settlement, 2, 2) };

	engine.processRequests(requests);
	
	IBoardElement elementBuilt = board.get(2, 2);
	
	assertSame(BoardElementType.Structure, elementBuilt.getElementType());
	assertSame(StructureType.Settlement, ((IBoardStructure)elementBuilt).getType());
	assertSame(player.getId(), ((IOwnedElement)elementBuilt).getOwner().getId());
	assertSame(0, player.getWarehouse().getResource(ResourceType.Brick));
    }
    
    public void itMustProcessAValidBuildConnectionRequest() {
	
    }

    private IBoardTerrain buildMountainTerrain() {
	return new BoardTerrain(6, TerrainType.Mountains);
    }

    private IBoardStructure buildNoneStructure() {
	return new BoardStructure(null, new ResourceManager(), StructureType.None);
    }

    private IBoardTerrain buildNoneTerrain() {
	return new BoardTerrain(0, TerrainType.None);
    }
    
    private IBoardConnection buildPlayerConnection(IPlayer player) {
	return new BoardConnection(player, new ResourceManager(), ConnectionType.Road);
    }

    private ICatanEditableBoard buildStandardBoard(IPlayer player1)
	    throws InvalidBoardDimensionsException, InvalidBoardElementException {
	IBoardElement[][] elements = {
		{ buildNoneStructure(), buildVoidConnection(), buildNoneStructure(), buildVoidConnection(),
			buildNoneStructure(), },
		{ buildVoidConnection(), buildNoneTerrain(), buildPlayerConnection(player1), buildMountainTerrain(),
			buildVoidConnection(), },
		{ buildNoneStructure(), buildVoidConnection(), buildNoneStructure(), buildVoidConnection(),
			buildNoneStructure(), },
		{ buildVoidConnection(), buildNoneTerrain(), buildVoidConnection(), buildMountainTerrain(),
			buildVoidConnection(), },
		{ buildNoneStructure(), buildVoidConnection(), buildNoneStructure(), buildVoidConnection(),
			buildNoneStructure(), }, };

	return new CatanEditableBoard(5, 5, elements);
    }

    private IBoardConnection buildVoidConnection() {
	return new BoardConnection(null, new ResourceManager(), ConnectionType.Void);
    }
}
