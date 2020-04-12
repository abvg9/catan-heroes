package io.github.notaphplover.catan.core.game.handler;

import io.github.notaphplover.catan.core.game.GameState;
import io.github.notaphplover.catan.core.request.IRequest;

public abstract class StandardRequestHandlerBuilder<
        R extends IRequest, S extends StandardRequestHandlerBuilder<R, S>>
    extends RequestHandlerBuilder<R, S> {

  private boolean logRequestAfterAction;

  private boolean notifyToPlayers;

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

  public boolean isNotifyToPlayers() {
    return notifyToPlayers;
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

  public S setLogRequestAfterAction(boolean logRequestAfterAction) {
    this.logRequestAfterAction = logRequestAfterAction;

    return getSelf();
  }

  public S setNotifyToPlayers(boolean notifyToPlayers) {
    this.notifyToPlayers = notifyToPlayers;

    return getSelf();
  }

  public S setRejectActivePlayer(boolean rejectActivePlayer) {
    this.rejectActivePlayer = rejectActivePlayer;

    return getSelf();
  }

  public S setRejectIfTurnStarted(boolean rejectIfTurnStarted) {
    this.rejectIfTurnStarted = rejectIfTurnStarted;

    return getSelf();
  }

  public S setRejectIfTurnNotStarted(boolean rejectIfTurnNotStarted) {
    this.rejectIfTurnNotStarted = rejectIfTurnNotStarted;

    return getSelf();
  }

  public S setRejectUnactivePlayers(boolean rejectUnactivePlayers) {
    this.rejectUnactivePlayers = rejectUnactivePlayers;

    return getSelf();
  }

  public S setStateAllowed(GameState stateAllowed) {
    this.stateAllowed = stateAllowed;

    return getSelf();
  }
}
