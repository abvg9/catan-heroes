package com.ucm.dasi.catan.board.connection;

import com.ucm.dasi.catan.board.element.IOwnedElement;

public interface IBoardConnection extends IOwnedElement {
    ConnectionType getType();
}
