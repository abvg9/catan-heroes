package io.github.notaphplover.catan.core.game.handler.element.structure;

import io.github.notaphplover.catan.core.game.handler.element.BuildElementRequestHandlerBuilder;
import io.github.notaphplover.catan.core.request.IStructureRelatedRequest;

public class StructureRelatedRequestHandlerBuilder<
        Req extends IStructureRelatedRequest,
        Self extends StructureRelatedRequestHandlerBuilder<Req, Self>>
    extends BuildElementRequestHandlerBuilder<Req, Self> {}
