package io.github.notaphplover.catan.core.game.handler.element;

import io.github.notaphplover.catan.core.game.handler.StandardRequestHandlerBuilder;
import io.github.notaphplover.catan.core.request.IBuildElementRequest;

public abstract class BuildElementRequestHandlerBuilder<
        TRequest extends IBuildElementRequest,
        TReturn extends StandardRequestHandlerBuilder<TRequest, TReturn>>
    extends StandardRequestHandlerBuilder<TRequest, TReturn> {

  private boolean substractResources;

  private boolean upgradeHandler;

  public boolean isSubstractResources() {
    return substractResources;
  }

  public boolean isUpgradeHandler() {
    return upgradeHandler;
  }

  public TReturn setSubstractResources(boolean substractResources) {
    this.substractResources = substractResources;

    return getSelf();
  }

  public TReturn setUpgradeHandler(boolean upgradeHandler) {
    this.upgradeHandler = upgradeHandler;

    return getSelf();
  }
}
