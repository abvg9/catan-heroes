package com.ucm.dasi.catan.game;

import com.ucm.dasi.catan.board.ICatanBoard;
import com.ucm.dasi.catan.exception.NonNullInputException;
import com.ucm.dasi.catan.exception.NonVoidCollectionException;
import com.ucm.dasi.catan.player.IPlayer;

public class CatanGame<TBoard extends ICatanBoard> implements ICatanGame<TBoard> {

    private TBoard board;

    private IPlayer[] players;

    private int turnIndex;

    private boolean turnStarted;

    public CatanGame(TBoard board, IPlayer[] players, boolean turnStarted)
	    throws NonNullInputException, NonVoidCollectionException {

	checkBoard(board);
	checkPlayers(players);

	this.board = board;
	this.players = players;
	this.turnIndex = 0;
	this.turnStarted = turnStarted;
    }

    public IPlayer getActivePlayer() {
	return players[turnIndex];
    }

    public TBoard getBoard() {
	return board;
    }

    public IPlayer[] getPlayers() {
	return players;
    }

    public boolean isTurnStarted() {
	return turnStarted;
    }

    protected void passTurn() {
	turnIndex = (turnIndex + 1) % players.length;
    }

    protected void switchTurnStarted() {
	turnStarted = !turnStarted;
    }

    private void checkBoard(ICatanBoard board) throws NonNullInputException {
	if (board == null) {
	    throw new NonNullInputException();
	}
    }

    private void checkPlayers(IPlayer[] players) throws NonNullInputException, NonVoidCollectionException {
	if (players == null) {
	    throw new NonNullInputException();
	}
	if (players.length == 0) {
	    throw new NonVoidCollectionException();
	}

	for (IPlayer player : players) {
	    if (player == null) {
		throw new NonNullInputException();
	    }
	}
    }
}
