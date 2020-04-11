package io.github.notaphplover.catan.core.game.handler.element;

import io.github.notaphplover.catan.core.game.handler.StandardRequestHandlerBuilder;
import io.github.notaphplover.catan.core.request.IBuildElementRequest;

public abstract class BuildElementRequestHandlerBuilder<
        Req extends IBuildElementRequest, Self extends StandardRequestHandlerBuilder<Req, Self>>
    extends StandardRequestHandlerBuilder<Req, Self> {

  private boolean substractResources;

  private boolean upgradeHandler;

  public boolean isSubstractResources() {
    return substractResources;
  }

  public boolean isUpgradeHandler() {
    return upgradeHandler;
  }

  public Self setSubstractResources(boolean substractResources) {
    this.substractResources = substractResources;

    return getSelf();
  }

  public Self setUpgradeHandler(boolean upgradeHandler) {
    this.upgradeHandler = upgradeHandler;

    return getSelf();
  }
}
