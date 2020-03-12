package com.ucm.dasi.catan.game;

import com.ucm.dasi.catan.board.ICatanEditableBoard;
import com.ucm.dasi.catan.request.IRequest;

public interface ICatanGameEngine extends ICatanGame<ICatanEditableBoard> {
    void processRequests(IRequest[] requests);
}
