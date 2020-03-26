package com.ucm.dasi.catan.game.generator;

import java.util.Random;

public class CatanRandomGenerator extends CatanGenerator {

  private static final int MINIMUN = 1;
  private static final int MODULUS = 6;
  
  private static final int REPETITIONS = 2;
  
  private Random randomGenerator;
  
  public CatanRandomGenerator() {
    randomGenerator = new Random(System.currentTimeMillis());
  }
  
  @Override
  public int getNextProductionNumber() {
    int result = 0;
    
    for (int i = 0; i < REPETITIONS; ++i) {
      result += randomGenerator.nextInt(MODULUS - MINIMUN + 1) + MINIMUN;
    }
    
    return result;
  }
}
