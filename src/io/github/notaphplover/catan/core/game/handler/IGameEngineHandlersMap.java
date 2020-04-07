package io.github.notaphplover.catan.core.game.handler;

import io.github.notaphplover.catan.core.request.IRequest;
import io.github.notaphplover.catan.core.request.RequestType;
import java.util.Map;
import java.util.function.Consumer;

public interface IGameEngineHandlersMap extends Map<RequestType, Consumer<? extends IRequest>> {}
