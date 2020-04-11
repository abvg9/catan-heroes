package io.github.notaphplover.catan.core.game.player;

import io.github.notaphplover.catan.core.player.IPlayer;

public interface IPlayerManager {

  IPlayer getActivePlayer();

  IPlayer[] getPlayers();

  int getTurnNumber();

  boolean isTurnStarted();

  void passTurn();

  void switchTurnStarted();
}
