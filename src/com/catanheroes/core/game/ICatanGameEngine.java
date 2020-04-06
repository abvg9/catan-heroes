package com.catanheroes.core.game;

import com.catanheroes.core.game.log.ILogEntry;
import com.catanheroes.core.request.IRequest;

public interface ICatanGameEngine extends ICatanGame {

  ILogEntry getLog(int turn);

  void processRequest(IRequest requests);
}
