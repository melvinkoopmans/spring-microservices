package com.thoughtmechanix.licenses;

import java.util.Random;

public class Utils {
  /**
   * Randomly run long to induce a timeout. Used for testing Hystrix behavior.
   */
  public static void randomlyRunLong() {
    Random rand = new Random();
    int randomNum = rand.nextInt((3 - 1) + 1) + 1;
    if (randomNum==3) {
      try {
        Thread.sleep(11000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
