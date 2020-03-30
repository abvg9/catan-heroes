package com.ucm.dasi.catan.game.log;

/**
 * Represents a game log.
 *
 * A game log should be enough to recreate a game
 */
public interface IGameLog {

  /**
   * Gets the log entry at a certain turn.
   * @param turn turn number
   * @return Log entry at the turn provided.
   */
  public ILogEntry get(int turn);

  /**
   * Pushes an entry
   * @param entry entry to push
   */
  void set(int turn, ILogEntry entry);

  /**
   * Gets the number of entries registered.
   * @return Number of entries registered.
   */
  int size();
}
