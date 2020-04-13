package io.github.notaphplover.catan.core.agent;

import io.github.notaphplover.catan.core.command.ICommand;
import io.github.notaphplover.catan.core.game.ICatanGame;
import io.github.notaphplover.catan.core.request.IRequest;
import java.util.Collection;

public abstract class Agent implements IAgent {

  private ICatanGame innerGame;

  public Agent(ICatanGame innerGame) {
    this.innerGame = innerGame;
  }

  @Override
  public void handle(ICommand command) {

    registerPendingRequests(command.getDestinatary().emptyMissing());
  }

  protected ICatanGame getInnerGame() {
    return innerGame;
  };

  protected abstract void sendRequest(IRequest request);

  private void registerPendingRequests(Collection<IRequest> requests) {

    requests.forEach((IRequest request) -> getInnerGame().processRequest(request));
  }
}
