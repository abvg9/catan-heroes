package io.github.notaphplover.catan.core.agent;

import io.github.notaphplover.catan.core.game.ICatanGame;
import io.github.notaphplover.catan.core.request.IRequest;

public class MininumAgent extends Agent {

  public MininumAgent(ICatanGame innerGame) {
    super(innerGame);
  }

  @Override
  protected void sendRequest(IRequest request) {}
}
