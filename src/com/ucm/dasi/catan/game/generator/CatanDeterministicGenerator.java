package com.ucm.dasi.catan.game.generator;

import java.util.Random;

public class CatanDeterministicGenerator extends CatanGenerator implements IDeterministicNumberGenerator {

  private static final int N_0 = 0;
  private static final int N_1 = 0;
  private static final int N_2 = 1;
  private static final int N_3 = 2;
  private static final int N_4 = 3;
  private static final int N_5 = 4;
  private static final int N_6 = 5;
  private static final int N_7 = 6;
  private static final int N_8 = 5;
  private static final int N_9 = 4;
  private static final int N_10 = 3;
  private static final int N_11 = 2;
  private static final int N_12 = 1;
  
  private int innerOffset;
  
  private int[] productionSequence;
  
  public CatanDeterministicGenerator() {
    
    innerOffset = 0;
    productionSequence = generateSequence();
  }
  
  @Override
  public int getNextProductionNumber() {
    
    int productionNumber = getProductionNumber(0);
    
    innerOffset = (innerOffset + 1) % productionSequence.length;
    
    return productionNumber;
  }

  @Override
  public int getProductionNumber(int offset) {
    
    int index = (offset + innerOffset) % productionSequence.length;
    if (index < 0) {
      index += productionSequence.length;
    }
    
    return productionSequence[index];
  }
  
  private int[] generateSequence(int[] remainingNumbersSet) {
    
    int sum = 0;
    
    for(int numberAmount : remainingNumbersSet) {
      sum += numberAmount;
    }
    
    int[] sequence = new int[sum];
    
    Random random = new Random(System.currentTimeMillis());
    
    int sequenceIndex = sum;
    
    while (sequenceIndex > 0) {
      int nextNumber = random.nextInt(sequenceIndex) + 1;
      int setIndex = getIndexOfSet(remainingNumbersSet, nextNumber);
      
      --sequenceIndex;
      
      sequence[sequenceIndex] = remainingNumbersSet[setIndex];
      
      --remainingNumbersSet[setIndex];
    }
    
    return sequence;
  }

  private int[] generateSequence() {
    
    int[] remaininNumbersSet = { N_0, N_1, N_2, N_3, N_4, N_5, N_6, N_7, N_8, N_9, N_10, N_11, N_12 };
    
    return generateSequence(remaininNumbersSet);
  }
  
  private int getIndexOfSet(int[] set, int number) {
    
    int setIndex = 0;
    
    while (number > 0 && setIndex < set.length) {
      number -= set[setIndex];
      ++setIndex;
    }
    
    return setIndex;
  }

}
