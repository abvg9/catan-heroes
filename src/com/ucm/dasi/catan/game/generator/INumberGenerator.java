package com.ucm.dasi.catan.game.generator;

import java.util.Map;

/**
 * Represents a production number generator.
 * 
 * A production number generator can be modeled as a discrete probability distribution.
 */
public interface INumberGenerator {
  
  /**
   * Gets the next production number.
   * @return Next production number generated.
   */
  int getNextProductionNumber();
  
  /**
   * Returns the discrete probability distribution.
   * 
   * Each key of the map is a production number.
   * Each value of the map is the probability of generate the production number.
   * 
   * @return Discrete probability distribution.
   */
  Map<Integer, Float> getProbabilityDistribution();
}
