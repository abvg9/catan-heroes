package com.catanheroes.core.board.element;

import com.catanheroes.core.player.IPlayer;
import com.catanheroes.core.resource.IResourceStorage;

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
