package io.github.notaphplover.catan.core.game.handler.element.structure;

import io.github.notaphplover.catan.core.game.handler.element.BuildElementRequestHandlerBuilder;
import io.github.notaphplover.catan.core.request.IStructureRelatedRequest;

public class StructureRelatedRequestHandlerBuilder<
        R extends IStructureRelatedRequest, S extends StructureRelatedRequestHandlerBuilder<R, S>>
    extends BuildElementRequestHandlerBuilder<R, S> {}
