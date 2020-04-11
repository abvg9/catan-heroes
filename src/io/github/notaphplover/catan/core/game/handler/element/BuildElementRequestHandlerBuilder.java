package io.github.notaphplover.catan.core.game.handler.element;

import io.github.notaphplover.catan.core.game.handler.StandardRequestHandlerBuilder;
import io.github.notaphplover.catan.core.request.IBuildElementRequest;

public abstract class BuildElementRequestHandlerBuilder<
        R extends IBuildElementRequest, S extends StandardRequestHandlerBuilder<R, S>>
    extends StandardRequestHandlerBuilder<R, S> {

  private boolean substractResources;

  private boolean upgradeHandler;

  public boolean isSubstractResources() {
    return substractResources;
  }

  public boolean isUpgradeHandler() {
    return upgradeHandler;
  }

  public S setSubstractResources(boolean substractResources) {
    this.substractResources = substractResources;

    return getSelf();
  }

  public S setUpgradeHandler(boolean upgradeHandler) {
    this.upgradeHandler = upgradeHandler;

    return getSelf();
  }
}
