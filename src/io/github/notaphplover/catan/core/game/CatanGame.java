package io.github.notaphplover.catan.core.game;

import io.github.notaphplover.catan.core.board.ICatanBoard;
import io.github.notaphplover.catan.core.exception.NonNullInputException;
import io.github.notaphplover.catan.core.game.player.IPlayerManager;
import io.github.notaphplover.catan.core.game.point.IPointsCalculator;
import io.github.notaphplover.catan.core.game.point.PointsCalculator;
import io.github.notaphplover.catan.core.player.IPlayer;
import java.util.Map;

public class CatanGame implements ICatanGame {

  private ICatanBoard board;

  private IPlayerManager playerManager;

  private int pointsToWin;

  private IPointsCalculator pointsCalculator;

  private GameState state;

  public CatanGame(
      ICatanBoard board, IPlayerManager playerManager, int pointsToWin, GameState state)
      throws NonNullInputException {

    checkBoard(board);
    checkState(state);

    this.board = board;
    this.playerManager = playerManager;
    pointsCalculator = new PointsCalculator(this);
    this.pointsToWin = pointsToWin;
    this.state = state;
  }

  @Override
  public IPlayer getActivePlayer() {
    return playerManager.getActivePlayer();
  }

  @Override
  public ICatanBoard getBoard() {
    return board;
  }

  @Override
  public IPlayer[] getPlayers() {
    return playerManager.getPlayers();
  }

  @Override
  public int getPointsToWin() {
    return pointsToWin;
  }

  @Override
  public Map<IPlayer, Integer> getPoints() {
    return pointsCalculator.getPoints();
  }

  @Override
  public GameState getState() {
    return state;
  }

  @Override
  public int getTurnNumber() {
    return playerManager.getTurnNumber();
  }

  @Override
  public boolean isTurnStarted() {
    return playerManager.isTurnStarted();
  }

  protected void passTurn() {
    playerManager.passTurn();
  }

  private void checkBoard(ICatanBoard board) throws NonNullInputException {
    if (board == null) {
      throw new NonNullInputException();
    }
  }

  private void checkState(GameState state) throws NonNullInputException {
    if (state == null) {
      throw new NonNullInputException();
    }
  }
}
