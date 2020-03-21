package com.ucm.dasi.catan.game.handler;

import java.util.Map;
import java.util.function.Consumer;

import com.ucm.dasi.catan.request.IRequest;
import com.ucm.dasi.catan.request.RequestType;

public interface IGameEngineHandlersMap extends Map<RequestType, Consumer<? extends IRequest>> {}
