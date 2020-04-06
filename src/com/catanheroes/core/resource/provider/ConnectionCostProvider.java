package com.catanheroes.core.resource.provider;

import com.catanheroes.core.board.connection.ConnectionType;
import com.catanheroes.core.resource.IResourceStorage;
import java.util.Map;

public class ConnectionCostProvider extends ResourceManagerProvider<ConnectionType> {

  public ConnectionCostProvider(Map<ConnectionType, ? extends IResourceStorage> costMap) {
    super(costMap);
  }
}
