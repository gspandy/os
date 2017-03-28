/*
 * Copyright 2012 Lei CHEN (raistlic@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wise.core.raist.raistlic.common.permutation;

import java.math.BigInteger;

/**
 * The class is a helper to calculate factorials
 *
 * @author lei.c
 * @since 2012-08-07
 */
class Calculator {

  /**
   * The method returns the factorial of the specified {@code number}.
   *
   * @param number the number to calculate its factorial, must not be less than {@code 0} .
   * @return the factorial result.
   */
  static BigInteger factorial(int number) {

    if (number < 0) {

      throw new IllegalArgumentException("'number' is less than 0.");
    }

    int index = lastCachedIndex(number);
    BigInteger result = FACT_RESULT_POOL[index];

    for (int i = index + 1; i <= number; i++) {

      result = result.multiply(BigInteger.valueOf(i));
      if (i < FACT_RESULT_POOL.length) {

        FACT_RESULT_POOL[i] = result;
      }
    }
    return result;
  }

  private static int lastCachedIndex(int number) {

    for (int i = Math.min(number, FACT_RESULT_POOL.length - 1); i >= 2; i--) {

      if (FACT_RESULT_POOL[i] != null) {

        return i;
      }
    }
    return 0;
  }

  /**
   * The class is a static utility class, and is designed not to be instantiated
   * or inherited.
   */
  private Calculator() {}

  /**
   * To cache frequently queried results for performance.
   */
  private static final BigInteger[] FACT_RESULT_POOL = new BigInteger[1024];

  static {

    FACT_RESULT_POOL[0] = BigInteger.ONE;
    FACT_RESULT_POOL[1] = BigInteger.ONE;
  }
}
