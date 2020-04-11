package io.github.notaphplover.catan.core.game.handler;

import io.github.notaphplover.catan.core.game.GameState;
import io.github.notaphplover.catan.core.request.IRequest;

public abstract class StandardRequestHandlerBuilder<
        Req extends IRequest, Self extends StandardRequestHandlerBuilder<Req, Self>>
    extends RequestHandlerBuilder<Req, Self> {

  private boolean logRequestAfterAction;

  private boolean rejectActivePlayer;

  private boolean rejectIfTurnStarted;

  private boolean rejectIfTurnNotStarted;

  private boolean rejectUnactivePlayers;

  private GameState stateAllowed;

  public GameState getStateAllowed() {
    return stateAllowed;
  }

  public boolean isLogRequestAfterAction() {
    return logRequestAfterAction;
  }

  public boolean isRejectActivePlayer() {
    return rejectActivePlayer;
  }

  public boolean isRejectIfTurnStarted() {
    return rejectIfTurnStarted;
  }

  public boolean isRejectIfTurnNotStarted() {
    return rejectIfTurnNotStarted;
  }

  public boolean isRejectUnactivePlayers() {
    return rejectUnactivePlayers;
  }

  public Self setRejectActivePlayer(boolean rejectActivePlayer) {
    this.rejectActivePlayer = rejectActivePlayer;

    return getSelf();
  }

  public Self setRejectIfTurnStarted(boolean rejectIfTurnStarted) {
    this.rejectIfTurnStarted = rejectIfTurnStarted;

    return getSelf();
  }

  public Self setRejectIfTurnNotStarted(boolean rejectIfTurnNotStarted) {
    this.rejectIfTurnNotStarted = rejectIfTurnNotStarted;

    return getSelf();
  }

  public Self setRejectUnactivePlayers(boolean rejectUnactivePlayers) {
    this.rejectUnactivePlayers = rejectUnactivePlayers;

    return getSelf();
  }

  public Self setStateAllowed(GameState stateAllowed) {
    this.stateAllowed = stateAllowed;

    return getSelf();
  }

  public Self setLogRequestAfterAction(boolean logRequestAfterAction) {
    this.logRequestAfterAction = logRequestAfterAction;

    return getSelf();
  }
}
