package io.github.notaphplover.catan.core.game.handler;

import io.github.notaphplover.catan.core.game.GameState;
import io.github.notaphplover.catan.core.request.IRequest;

public abstract class StandardRequestHandlerBuilder<
        TRequest extends IRequest, TReturn extends StandardRequestHandlerBuilder<TRequest, TReturn>>
    extends RequestHandlerBuilder<TRequest, TReturn> {

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

  public TReturn setRejectActivePlayer(boolean rejectActivePlayer) {
    this.rejectActivePlayer = rejectActivePlayer;

    return getSelf();
  }

  public TReturn setRejectIfTurnStarted(boolean rejectIfTurnStarted) {
    this.rejectIfTurnStarted = rejectIfTurnStarted;

    return getSelf();
  }

  public TReturn setRejectIfTurnNotStarted(boolean rejectIfTurnNotStarted) {
    this.rejectIfTurnNotStarted = rejectIfTurnNotStarted;

    return getSelf();
  }

  public TReturn setRejectUnactivePlayers(boolean rejectUnactivePlayers) {
    this.rejectUnactivePlayers = rejectUnactivePlayers;

    return getSelf();
  }

  public TReturn setStateAllowed(GameState stateAllowed) {
    this.stateAllowed = stateAllowed;

    return getSelf();
  }

  public TReturn setLogRequestAfterAction(boolean logRequestAfterAction) {
    this.logRequestAfterAction = logRequestAfterAction;

    return getSelf();
  }
}
