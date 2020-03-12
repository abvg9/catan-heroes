package com.ucm.dasi.catan.game;

import com.ucm.dasi.catan.request.IRequest;

public interface ICatanGameEngine extends ICatanGame {
    void processRequests(IRequest[] requests);
}
