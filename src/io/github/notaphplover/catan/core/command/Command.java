package io.github.notaphplover.catan.core.command;

import io.github.notaphplover.catan.core.player.IPlayer;

public class Command implements ICommand {

  private IPlayer destinatary;

  private CommandType type;

  public Command(IPlayer destinatary, CommandType type) {
    this.destinatary = destinatary;
    this.type = type;
  }

  public IPlayer getDestinatary() {
    return destinatary;
  }

  public CommandType getType() {
    return type;
  }
}
