package io.github.notaphplover.catan.core.game.handler.element;

import io.github.notaphplover.catan.core.board.element.IOwnedElement;
import io.github.notaphplover.catan.core.board.exception.InvalidBoardElementException;
import io.github.notaphplover.catan.core.game.handler.StandardRequestHandler;
import io.github.notaphplover.catan.core.game.handler.StandardRequestHandlerBuilder;
import io.github.notaphplover.catan.core.game.hearth.ICatanGameHearth;
import io.github.notaphplover.catan.core.request.IBuildElementRequest;
import io.github.notaphplover.catan.core.resource.exception.NotEnoughtResourcesException;
import java.util.function.BiFunction;

public abstract class BuildElementRequestHandler<TRequest extends IBuildElementRequest>
    extends StandardRequestHandler<TRequest> {

  public BuildElementRequestHandler(
      BuildElementRequestHandlerBuilder<TRequest, ?> builder,
      BiFunction<ICatanGameHearth, TRequest, IOwnedElement> elementBuilder) {
    super(processBuilder(builder, elementBuilder));
  }

  private static <TRequest extends IBuildElementRequest> void baseBuildAction(
      ICatanGameHearth hearth, TRequest request, IOwnedElement element)
      throws InvalidBoardElementException {
    hearth.getBoard().build(element, request.getX(), request.getY());
  }

  private static <TRequest extends IBuildElementRequest> void baseSubstractResources(
      ICatanGameHearth hearth, TRequest request, IOwnedElement element)
      throws NotEnoughtResourcesException {
    hearth.getPlayerManager().getActivePlayer().getResourceManager().substract(element.getCost());
  }

  private static <TRequest extends IBuildElementRequest> void baseUpgradeAction(
      ICatanGameHearth hearth, TRequest request, IOwnedElement element)
      throws InvalidBoardElementException {
    hearth.getBoard().upgrade(element, request.getX(), request.getY());
  }

  private static <TRequest extends IBuildElementRequest>
      BiFunction<ICatanGameHearth, TRequest, Boolean> generateBuildAction(
          BuildElementRequestHandlerBuilder<TRequest, ?> builder,
          BiFunction<ICatanGameHearth, TRequest, IOwnedElement> elementBuilder) {

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

  private static <TRequest extends IBuildElementRequest>
      BiFunction<ICatanGameHearth, TRequest, Boolean> generateBuildActionSubstractingResources(
          BuildElementRequestHandlerBuilder<TRequest, ?> builder,
          BiFunction<ICatanGameHearth, TRequest, IOwnedElement> elementBuilder) {

    return (ICatanGameHearth hearth, TRequest request) -> {
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

  private static <TRequest extends IBuildElementRequest>
      BiFunction<ICatanGameHearth, TRequest, Boolean>
          generateBuildActionWithoutSubstractingResources(
              BuildElementRequestHandlerBuilder<TRequest, ?> builder,
              BiFunction<ICatanGameHearth, TRequest, IOwnedElement> elementBuilder) {

    return (ICatanGameHearth hearth, TRequest request) -> {
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

  private static <TRequest extends IBuildElementRequest>
      BiFunction<ICatanGameHearth, TRequest, Boolean> generateUpgradeActionSubstractingResources(
          BuildElementRequestHandlerBuilder<TRequest, ?> builder,
          BiFunction<ICatanGameHearth, TRequest, IOwnedElement> elementBuilder) {

    return (ICatanGameHearth hearth, TRequest request) -> {
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

  private static <TRequest extends IBuildElementRequest>
      BiFunction<ICatanGameHearth, TRequest, Boolean>
          generateUpgradeActionWithoutSubstractingResources(
              BuildElementRequestHandlerBuilder<TRequest, ?> builder,
              BiFunction<ICatanGameHearth, TRequest, IOwnedElement> elementBuilder) {

    return (ICatanGameHearth hearth, TRequest request) -> {
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

  private static <TRequest extends IBuildElementRequest>
      StandardRequestHandlerBuilder<TRequest, ?> processBuilder(
          BuildElementRequestHandlerBuilder<TRequest, ?> builder,
          BiFunction<ICatanGameHearth, TRequest, IOwnedElement> elementBuilder) {

    builder.setPreconditionFullfilledAction(generateBuildAction(builder, elementBuilder));

    return builder;
  }
}
