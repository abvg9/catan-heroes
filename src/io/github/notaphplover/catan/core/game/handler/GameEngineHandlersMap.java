package io.github.notaphplover.catan.core.game.handler;

import io.github.notaphplover.catan.core.request.IRequest;
import io.github.notaphplover.catan.core.request.RequestType;
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
