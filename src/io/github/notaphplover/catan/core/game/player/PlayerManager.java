package io.github.notaphplover.catan.core.game.player;

import io.github.notaphplover.catan.core.exception.NonNullInputException;
import io.github.notaphplover.catan.core.exception.NonVoidCollectionException;
import io.github.notaphplover.catan.core.game.exception.InvalidTurnIndexException;
import io.github.notaphplover.catan.core.player.IPlayer;

public class PlayerManager implements IPlayerManager {

  private IPlayer[] players;

  private int turnNumber;

  private boolean turnStarted;

  public PlayerManager(IPlayer[] players, int turnNumber, boolean turnStarted)
      throws NonNullInputException, NonVoidCollectionException {
    checkPlayers(players);
    checkTurnIndex(players, turnNumber);

    this.players = players;
    this.turnNumber = turnNumber;
    this.turnStarted = turnStarted;
  }

  public IPlayer getActivePlayer() {
    return players[getTurnIndex()];
  }

  public IPlayer[] getPlayers() {
    return players;
  }

  public int getTurnNumber() {
    return turnNumber;
  }

  public boolean isTurnStarted() {
    return turnStarted;
  }

  public void passTurn() {
    ++turnNumber;
  }

  public void switchTurnStarted() {
    turnStarted = !turnStarted;
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

  private int getTurnIndex() {
    return turnNumber % players.length;
  }
}
