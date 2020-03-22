package com.ucm.dasi.catan.game.handler;

import java.util.Map;
import java.util.function.Consumer;

import com.ucm.dasi.catan.lang.ComparableClass;
import com.ucm.dasi.catan.request.IRequest;

public interface IGameEngineHandlersMap extends Map<ComparableClass<? extends IRequest>, Consumer<? extends IRequest>> {}
