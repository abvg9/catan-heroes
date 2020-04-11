package io.github.notaphplover.catan.core.board.element;

import io.github.notaphplover.catan.core.player.IPlayer;
import io.github.notaphplover.catan.core.resource.IResourceStorage;

public interface IOwnedElement extends IBoardElement {

  /**
   * Returns the element's cost.
   *
   * @return Element's cost.
   */
  IResourceStorage getCost();

  /**
   * Gets the elemnt's owner
   *
   * @return Player that owns the element or null if no player owns the element.
   */
  IPlayer getOwner();
}
