package com.ucm.dasi.catan.resource.provider;

import com.ucm.dasi.catan.board.connection.ConnectionType;
import com.ucm.dasi.catan.resource.IResourceStorage;
import java.util.Map;

public class ConnectionCostProvider extends ResourceManagerProvider<ConnectionType> {

  public ConnectionCostProvider(Map<ConnectionType, ? extends IResourceStorage> costMap) {
    super(costMap);
  }
}
