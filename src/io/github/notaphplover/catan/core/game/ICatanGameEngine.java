package io.github.notaphplover.catan.core.game;

import io.github.notaphplover.catan.core.game.log.ILogEntry;
import io.github.notaphplover.catan.core.request.IRequest;

public interface ICatanGameEngine extends ICatanGame {

  ILogEntry getLog(int turn);

  void processRequest(IRequest requests);
}
