package com.ucm.dasi.catan.board.element;

import com.ucm.dasi.catan.player.IPlayer;

public interface IOwnedElement extends IBoardElement {
    /**
     * Gets the elemnt's owner
     * @return Player that owns the element or null if no player owns the element.
     */
    IPlayer getOwner();
}
