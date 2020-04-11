package io.github.notaphplover.catan.core.game.handler.element;

import io.github.notaphplover.catan.core.board.element.IOwnedElement;
import io.github.notaphplover.catan.core.board.exception.InvalidBoardElementException;
import io.github.notaphplover.catan.core.game.handler.StandardRequestHandler;
import io.github.notaphplover.catan.core.game.handler.StandardRequestHandlerBuilder;
import io.github.notaphplover.catan.core.game.hearth.ICatanGameHearth;
import io.github.notaphplover.catan.core.request.IBuildElementRequest;
import io.github.notaphplover.catan.core.resource.exception.NotEnoughtResourcesException;
import java.util.function.BiFunction;

public abstract class BuildElementRequestHandler<R extends IBuildElementRequest>
    extends StandardRequestHandler<R> {

  public BuildElementRequestHandler(
      BuildElementRequestHandlerBuilder<R, ?> builder,
      BiFunction<ICatanGameHearth, R, IOwnedElement> elementBuilder) {
    super(processBuilder(builder, elementBuilder));
  }

  private static <R extends IBuildElementRequest> void baseBuildAction(
      ICatanGameHearth hearth, R request, IOwnedElement element)
      throws InvalidBoardElementException {
    hearth.getBoard().build(element, request.getX(), request.getY());
  }

  private static <R extends IBuildElementRequest> void baseSubstractResources(
      ICatanGameHearth hearth, R request, IOwnedElement element)
      throws NotEnoughtResourcesException {
    hearth.getPlayerManager().getActivePlayer().getResourceManager().substract(element.getCost());
  }

  private static <R extends IBuildElementRequest> void baseUpgradeAction(
      ICatanGameHearth hearth, R request, IOwnedElement element)
      throws InvalidBoardElementException {
    hearth.getBoard().upgrade(element, request.getX(), request.getY());
  }

  private static <R extends IBuildElementRequest>
      BiFunction<ICatanGameHearth, R, Boolean> generateBuildAction(
          BuildElementRequestHandlerBuilder<R, ?> builder,
          BiFunction<ICatanGameHearth, R, IOwnedElement> elementBuilder) {

    if (builder.isUpgradeHandler()) {
      if (builder.isSubstractResources()) {
        return generateUpgradeActionSubstractingResources(builder, elementBuilder);
      } else {
        return generateUpgradeActionWithoutSubstractingResources(builder, elementBuilder);
      }
    } else {
      if (builder.isSubstractResources()) {
        return generateBuildActionSubstractingResources(builder, elementBuilder);
      } else {
        return generateBuildActionWithoutSubstractingResources(builder, elementBuilder);
      }
    }
  }

  private static <R extends IBuildElementRequest>
      BiFunction<ICatanGameHearth, R, Boolean> generateBuildActionSubstractingResources(
          BuildElementRequestHandlerBuilder<R, ?> builder,
          BiFunction<ICatanGameHearth, R, IOwnedElement> elementBuilder) {

    return (ICatanGameHearth hearth, R request) -> {
      IOwnedElement element = elementBuilder.apply(hearth, request);

      try {
        baseSubstractResources(hearth, request, element);
        baseBuildAction(hearth, request, element);
        return true;
      } catch (InvalidBoardElementException | NotEnoughtResourcesException e) {
        if (builder.getPreconditionRejectedAction() != null) {
          builder.getPreconditionRejectedAction().accept(hearth, request);
        }

        return false;
      }
    };
  }

  private static <R extends IBuildElementRequest>
      BiFunction<ICatanGameHearth, R, Boolean> generateBuildActionWithoutSubstractingResources(
          BuildElementRequestHandlerBuilder<R, ?> builder,
          BiFunction<ICatanGameHearth, R, IOwnedElement> elementBuilder) {

    return (ICatanGameHearth hearth, R request) -> {
      IOwnedElement element = elementBuilder.apply(hearth, request);

      try {
        baseBuildAction(hearth, request, element);
        return true;
      } catch (InvalidBoardElementException e) {
        if (builder.getPreconditionRejectedAction() != null) {
          builder.getPreconditionRejectedAction().accept(hearth, request);
        }

        return false;
      }
    };
  }

  private static <R extends IBuildElementRequest>
      BiFunction<ICatanGameHearth, R, Boolean> generateUpgradeActionSubstractingResources(
          BuildElementRequestHandlerBuilder<R, ?> builder,
          BiFunction<ICatanGameHearth, R, IOwnedElement> elementBuilder) {

    return (ICatanGameHearth hearth, R request) -> {
      IOwnedElement element = elementBuilder.apply(hearth, request);

      try {
        baseSubstractResources(hearth, request, element);
        baseUpgradeAction(hearth, request, element);
        return true;
      } catch (InvalidBoardElementException | NotEnoughtResourcesException e) {
        if (builder.getPreconditionRejectedAction() != null) {
          builder.getPreconditionRejectedAction().accept(hearth, request);
        }

        return false;
      }
    };
  }

  private static <R extends IBuildElementRequest>
      BiFunction<ICatanGameHearth, R, Boolean> generateUpgradeActionWithoutSubstractingResources(
          BuildElementRequestHandlerBuilder<R, ?> builder,
          BiFunction<ICatanGameHearth, R, IOwnedElement> elementBuilder) {

    return (ICatanGameHearth hearth, R request) -> {
      IOwnedElement element = elementBuilder.apply(hearth, request);

      try {
        baseUpgradeAction(hearth, request, element);
        return true;
      } catch (InvalidBoardElementException e) {
        if (builder.getPreconditionRejectedAction() != null) {
          builder.getPreconditionRejectedAction().accept(hearth, request);
        }

        return false;
      }
    };
  }

  private static <R extends IBuildElementRequest>
      StandardRequestHandlerBuilder<R, ?> processBuilder(
          BuildElementRequestHandlerBuilder<R, ?> builder,
          BiFunction<ICatanGameHearth, R, IOwnedElement> elementBuilder) {

    builder.setPreconditionFullfilledAction(generateBuildAction(builder, elementBuilder));

    return builder;
  }
}
