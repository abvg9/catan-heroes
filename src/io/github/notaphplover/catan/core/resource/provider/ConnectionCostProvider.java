package io.github.notaphplover.catan.core.resource.provider;

import io.github.notaphplover.catan.core.board.connection.ConnectionType;
import io.github.notaphplover.catan.core.resource.IResourceStorage;
import java.util.Map;

public class ConnectionCostProvider extends ResourceManagerProvider<ConnectionType> {

  public ConnectionCostProvider(Map<ConnectionType, ? extends IResourceStorage> costMap) {
    super(costMap);
  }
}
