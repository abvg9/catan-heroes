package com.ucm.dasi.catan.game.handler;

import com.ucm.dasi.catan.request.IRequest;
import com.ucm.dasi.catan.request.RequestType;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Consumer;

public class GameEngineHandlersMap extends TreeMap<RequestType, Consumer<? extends IRequest>>
    implements IGameEngineHandlersMap {

  private static final long serialVersionUID = 3725544787318636351L;

  public GameEngineHandlersMap() {
    super();
  }

  public GameEngineHandlersMap(Map<RequestType, Consumer<? extends IRequest>> map) {
    super(map);
  }
}
