package com.catanheroes.core.game.handler;

import com.catanheroes.core.request.IRequest;
import com.catanheroes.core.request.RequestType;
import java.util.Map;
import java.util.function.Consumer;

public interface IGameEngineHandlersMap extends Map<RequestType, Consumer<? extends IRequest>> {}
