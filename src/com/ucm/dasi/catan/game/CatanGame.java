package com.ucm.dasi.catan.game;

import com.ucm.dasi.catan.board.ICatanBoard;
import com.ucm.dasi.catan.exception.NonNullInputException;
import com.ucm.dasi.catan.exception.NonVoidCollectionException;
import com.ucm.dasi.catan.game.exception.InvalidTurnIndexException;
import com.ucm.dasi.catan.game.point.IPointsCalculator;
import com.ucm.dasi.catan.game.point.PointsCalculator;
import com.ucm.dasi.catan.player.IPlayer;
import java.util.Map;

public class CatanGame<TBoard extends ICatanBoard> implements ICatanGame<TBoard> {

  private TBoard board;

  private IPlayer[] players;

  private int pointsToWin;

  private IPointsCalculator pointsCalculator;

  private GameState state;
  
  private int turnNumber;

  private boolean turnStarted;

  public CatanGame(
      TBoard board,
      IPlayer[] players,
      int pointsToWin,
      GameState state,
      int turnNumber,
      boolean turnStarted)
      throws NonNullInputException, NonVoidCollectionException {

    checkBoard(board);
    checkPlayers(players);
    checkState(state);
    checkTurnIndex(players, turnNumber);

    this.board = board;
    this.players = players;
    pointsCalculator = new PointsCalculator();
    this.pointsToWin = pointsToWin;
    this.state = state;
    this.turnNumber = turnNumber;
    this.turnStarted = turnStarted;
  }

  @Override
  public IPlayer getActivePlayer() {
    return players[getTurnIndex()];
  }

  @Override
  public TBoard getBoard() {
    return board;
  }

  @Override
  public IPlayer[] getPlayers() {
    return players;
  }

  @Override
  public int getPointsToWin() {
    return pointsToWin;
  }

  @Override
  public Map<IPlayer, Integer> getPoints() {
    return pointsCalculator.getPoints(this);
  }

  @Override
  public GameState getState() {
    return state;
  }
  
  @Override
  public int getTurnNumber() {
    return turnNumber;
  }

  @Override
  public boolean isTurnStarted() {
    return turnStarted;
  }

  protected void endGame() {
    state = GameState.ENDED;
  }

  protected boolean hasActivePlayerWon() {

    if (state == GameState.ENDED) {
      return true;
    }
    return pointsCalculator.getPoints(this).get(getActivePlayer()) >= getPointsToWin();
  }

  protected void passTurn() {
    ++turnNumber;
  }

  protected void switchTurnStarted() {
    turnStarted = !turnStarted;
  }

  private void checkBoard(ICatanBoard board) throws NonNullInputException {
    if (board == null) {
      throw new NonNullInputException();
    }
  }

  private void checkPlayers(IPlayer[] players)
      throws NonNullInputException, NonVoidCollectionException {
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

  private void checkTurnIndex(IPlayer[] players, int turnIndex) {
    if (turnIndex < 0) {
      throw new InvalidTurnIndexException(turnIndex, 0, players.length - 1);
    }
  }

  private void checkState(GameState state) throws NonNullInputException {
    if (state == null) {
      throw new NonNullInputException();
    }
  }
  
  private int getTurnIndex() {
    return turnNumber % players.length;
  }
}
