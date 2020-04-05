package com.ucm.dasi.catan.game;

import com.ucm.dasi.catan.game.log.ILogEntry;
import com.ucm.dasi.catan.request.IRequest;

public interface ICatanGameEngine extends ICatanGame {

  ILogEntry getLog(int turn);

  void processRequests(IRequest[] requests);
}
