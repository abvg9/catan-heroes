package io.github.notaphplover.catan.core.game.handler;

import io.github.notaphplover.catan.core.game.hearth.ICatanGameHearth;
import io.github.notaphplover.catan.core.request.IRequest;

public interface IRequestHandler<R extends IRequest> {

  void handle(ICatanGameHearth hearth, R request);
}
