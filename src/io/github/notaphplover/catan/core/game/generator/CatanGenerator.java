package io.github.notaphplover.catan.core.game.generator;

import java.util.AbstractMap;
import java.util.Map;

public abstract class CatanGenerator implements INumberGenerator {

  private static final float P_2 = 1f / 36f;
  private static final float P_3 = 2f / 36f;
  private static final float P_4 = 3f / 36f;
  private static final float P_5 = 4f / 36f;
  private static final float P_6 = 5f / 36f;
  private static final float P_7 = 6f / 36f;
  private static final float P_8 = 5f / 36f;
  private static final float P_9 = 4f / 36f;
  private static final float P_10 = 3f / 36f;
  private static final float P_11 = 2f / 36f;
  private static final float P_12 = 1f / 36f;

  @Override
  public abstract int getNextProductionNumber();

  @Override
  public Map<Integer, Float> getProbabilityDistribution() {
    return Map.ofEntries(
        new AbstractMap.SimpleEntry<Integer, Float>(2, P_2),
        new AbstractMap.SimpleEntry<Integer, Float>(3, P_3),
        new AbstractMap.SimpleEntry<Integer, Float>(4, P_4),
        new AbstractMap.SimpleEntry<Integer, Float>(5, P_5),
        new AbstractMap.SimpleEntry<Integer, Float>(6, P_6),
        new AbstractMap.SimpleEntry<Integer, Float>(7, P_7),
        new AbstractMap.SimpleEntry<Integer, Float>(8, P_8),
        new AbstractMap.SimpleEntry<Integer, Float>(9, P_9),
        new AbstractMap.SimpleEntry<Integer, Float>(10, P_10),
        new AbstractMap.SimpleEntry<Integer, Float>(11, P_11),
        new AbstractMap.SimpleEntry<Integer, Float>(12, P_12));
  }
}
