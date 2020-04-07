package io.github.notaphplover.catan.core.request;

import io.github.notaphplover.catan.core.player.IPlayer;

public interface IRequest {
  IPlayer getPlayer();

  RequestType getType();
}
