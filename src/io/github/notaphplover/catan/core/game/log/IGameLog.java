package io.github.notaphplover.catan.core.game.log;

import io.github.notaphplover.catan.core.request.RequestType;

/**
 * Represents a game log.
 *
 * <p>A game log should be enough to recreate a game
 */
public interface IGameLog {

  /**
   * Gets the log entry at a certain turn.
   *
   * @param turn turn number
   * @return Log entry at the turn provided.
   */
  ILogEntry get(int turn);

  /**
   * Determines if at least a request of the specific type has been logged at the specified turn.
   *
   * @param turn request's turn.
   * @param type request's type.
   * @return True if at least a request of the specific type has been logged at the specified turn.
   */
  boolean isRequestPerformedAt(int turn, RequestType type);

  /**
   * Pushes an entry
   *
   * @param turn entry's turn
   * @param entry entry to push
   */
  void set(int turn, ILogEntry entry);

  /**
   * Gets the number of entries registered.
   *
   * @return Number of entries registered.
   */
  int size();
}
