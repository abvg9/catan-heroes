package com.ucm.dasi.catan.request;

import static org.junit.jupiter.api.Assertions.assertSame;

import com.ucm.dasi.catan.player.IPlayer;
import com.ucm.dasi.catan.player.Player;
import com.ucm.dasi.catan.resource.ResourceManager;
import com.ucm.dasi.catan.resource.exception.NegativeNumberException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class RequestTest {

  @DisplayName("It must store its player")
  @Tag(value = "Request")
  @Test
  public void itMustStoreItsPlayer() throws NegativeNumberException {
    IPlayer player = new Player(0, new ResourceManager());

    MinimunRequest request = new MinimunRequest(player, RequestType.BuildConnection);

    assertSame(player, request.getPlayer());
  }

  @DisplayName("It must store its request type")
  @Tag(value = "Request")
  @Test
  public void itMustStoreItsRequestType() throws NegativeNumberException {
    RequestType requestType = RequestType.BuildConnection;

    MinimunRequest request = new MinimunRequest(new Player(0, new ResourceManager()), requestType);

    assertSame(requestType, request.getType());
  }
}
