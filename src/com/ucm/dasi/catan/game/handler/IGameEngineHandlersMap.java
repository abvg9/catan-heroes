package com.ucm.dasi.catan.game.handler;

import com.ucm.dasi.catan.request.IRequest;
import com.ucm.dasi.catan.request.RequestType;
import java.util.Map;
import java.util.function.Consumer;

public interface IGameEngineHandlersMap extends Map<RequestType, Consumer<? extends IRequest>> {}
