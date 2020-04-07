package io.github.notaphplover.catan.core.game.generator;

/** Represents a deterministic production number generator. */
public interface IDeterministicNumberGenerator extends INumberGenerator {
  /**
   * Gets a production number with an offset relative to the next production number.
   *
   * <p>An offset of zero is equivalent to request the next production number. A positive offset is
   * equivalent to request production numbers next to the next one. A negative offset is equivalent
   * to request production numbers previous to the next one.
   *
   * @param offset Request offset.
   * @return Production number obtained.
   */
  public int getProductionNumber(int offset);
}
