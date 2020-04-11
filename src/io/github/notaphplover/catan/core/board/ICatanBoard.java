package io.github.notaphplover.catan.core.board;

import io.github.notaphplover.catan.core.board.connection.ConnectionDirection;
import io.github.notaphplover.catan.core.board.element.IBoardElement;
import io.github.notaphplover.catan.core.board.exception.InvalidBoardElementException;
import io.github.notaphplover.catan.core.player.IPlayer;
import io.github.notaphplover.catan.core.resource.production.IResourceProduction;

public interface ICatanBoard {

  void build(IBoardElement element, int x, int y) throws InvalidBoardElementException;

  IBoardElement get(int x, int y);

  ConnectionDirection getConnectionDirection(int x, int y);

  int getHeight();

  IResourceProduction getProduction(int productionNumber);

  int getWidth();

  /**
   * Determines if a connection point is connected by a certain player's connections.
   *
   * @param player Player
   * @param x X coordinate of the point.
   * @param y Y coordinate of the point
   * @return true if the provided player has connections to reach the point.
   */
  boolean isConnectionConnected(IPlayer player, int x, int y);

  /**
   * Determines if a structure point is connected by a certain player's connections
   *
   * @param player Player
   * @param x X coordinate of the point.
   * @param y Y coordinate of the point
   * @return true if the provided player has connections to reach the point.
   */
  boolean isStructurePointConnected(IPlayer player, int x, int y);

  void upgrade(IBoardElement element, int x, int y) throws InvalidBoardElementException;
}
