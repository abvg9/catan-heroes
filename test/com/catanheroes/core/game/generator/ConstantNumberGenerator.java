package com.catanheroes.core.game.generator;

import java.util.Map;

public class ConstantNumberGenerator implements IDeterministicNumberGenerator {

  private int number;

  public ConstantNumberGenerator(int number) {
    this.number = number;
  }

  @Override
  public int getNextProductionNumber() {
    return getProductionNumber(0);
  }

  @Override
  public Map<Integer, Float> getProbabilityDistribution() {
    return Map.of(number, 1f);
  }

  @Override
  public int getProductionNumber(int offset) {
    return number;
  }
}
