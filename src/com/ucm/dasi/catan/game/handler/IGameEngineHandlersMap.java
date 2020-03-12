package com.ucm.dasi.catan.game.handler;

import java.util.Map;
import java.util.function.Consumer;

import com.ucm.dasi.catan.request.IRequest;

public interface IGameEngineHandlersMap extends Map<Class<? extends IRequest>, Consumer<? extends IRequest>> {}
