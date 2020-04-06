package com.catanheroes.core.game;

/** Represents a state of a Catan game */
public enum GameState {
  /** The game is ended. A set of winners are declared. */
  ENDED,
  /**
   * The game is in the foundation phase.
   *
   * <p>This phase is the initial one. Players take turns to place their initial elements for free.
   */
  FOUNDATION,
  /** The game is in the normal phase. */
  NORMAL
}
