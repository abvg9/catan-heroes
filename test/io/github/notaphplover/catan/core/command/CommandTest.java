package io.github.notaphplover.catan.core.command;

import static org.junit.jupiter.api.Assertions.assertSame;

import io.github.notaphplover.catan.core.player.IPlayer;
import io.github.notaphplover.catan.core.player.Player;
import io.github.notaphplover.catan.core.resource.ResourceManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class CommandTest {

  @DisplayName("It must return its destinatary")
  @Tag("Command")
  @Test
  public void itMustReturnItsDestinatary() {

    IPlayer player = new Player(0, new ResourceManager());
    CommandType type = CommandType.DIE;
    Command command = new Command(player, type);

    assertSame(player, command.getDestinatary());
  }

  @DisplayName("It must return its type")
  @Tag("Command")
  @Test
  public void itMustReturnItsType() {

    IPlayer player = new Player(0, new ResourceManager());
    CommandType type = CommandType.DIE;
    Command command = new Command(player, type);

    assertSame(type, command.getType());
  }
}
