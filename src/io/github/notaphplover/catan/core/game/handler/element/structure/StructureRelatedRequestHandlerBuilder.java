package io.github.notaphplover.catan.core.game.handler.element.structure;

import io.github.notaphplover.catan.core.game.handler.element.BuildElementRequestHandlerBuilder;
import io.github.notaphplover.catan.core.request.IStructureRelatedRequest;

public class StructureRelatedRequestHandlerBuilder<
        TRequest extends IStructureRelatedRequest,
        TReturn extends StructureRelatedRequestHandlerBuilder<TRequest, TReturn>>
    extends BuildElementRequestHandlerBuilder<TRequest, TReturn> {}
