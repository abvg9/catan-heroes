package com.ucm.dasi.catan.board.element;

import com.ucm.dasi.catan.player.IPlayer;
import com.ucm.dasi.catan.resource.IResourceManager;

public interface IOwnedElement extends IBoardElement {

  /**
   * Returns the element's cost.
   *
   * @return Element's cost.
   */
  IResourceManager getCost();

  /**
   * Gets the elemnt's owner
   *
   * @return Player that owns the element or null if no player owns the element.
   */
  IPlayer getOwner();
}
