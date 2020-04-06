package com.catanheroes.core.game;

import com.catanheroes.core.board.ICatanBoard;
import com.catanheroes.core.exception.NonNullInputException;
import com.catanheroes.core.exception.NonVoidCollectionException;
import com.catanheroes.core.game.exception.InvalidTurnIndexException;
import com.catanheroes.core.game.point.IPointsCalculator;
import com.catanheroes.core.game.point.PointsCalculator;
import com.catanheroes.core.player.IPlayer;
import java.util.Map;

public class CatanGame implements ICatanGame {

  private ICatanBoard board;

  private IPlayer[] players;

  private int pointsToWin;

  private IPointsCalculator pointsCalculator;

  private GameState state;

  private int turnNumber;

  private boolean turnStarted;

  public CatanGame(
      ICatanBoard board,
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
  public ICatanBoard getBoard() {
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

  protected void passTurn() {
    ++turnNumber;
  }

  protected void switchTurnStarted() {
    turnStarted = !turnStarted;
  }

  protected void switchStateIfNeeded() {
    switch (getState()) {
      case ENDED:
        return;
      case FOUNDATION:
        if (isLastFoundationPhaseTurn()) {
          state = GameState.NORMAL;
        }
        return;
      case NORMAL:
        if (hasActivePlayerWon()) {
          state = GameState.ENDED;
        }
        return;
    }
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

  private boolean hasActivePlayerWon() {
    return pointsCalculator.getPoints(this).get(getActivePlayer()) >= getPointsToWin();
  }

  private boolean isLastFoundationPhaseTurn() {
    return getTurnNumber() == 4 * getPlayers().length - 1;
  }
}
