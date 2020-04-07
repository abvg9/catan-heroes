package io.github.notaphplover.catan.core.command;

import io.github.notaphplover.catan.core.player.IPlayer;

public interface ICommand {
  IPlayer getDestinatary();

  CommandType getType();
}
