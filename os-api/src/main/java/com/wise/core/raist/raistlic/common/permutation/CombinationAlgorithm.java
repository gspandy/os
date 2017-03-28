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
 * The strategy interface for implementing the combination algorithm.
 *
 * @author lei.c
 * @since 2012-08-07
 */
public interface CombinationAlgorithm {

  /**
   * The method returns the maximum size of the collection(to calculate combinations) that the algorithm
   * supports.
   *
   * @return the maximum size of the collection(to calculate combinations) that the algorithm supports.
   */
  public int getMaxSupportedSize();

  /**
   * The method returns the number of combinations for the specified {@code numberOfElements} and {@code numberToPick}.
   *
   * @param numberOfElements the number of elements in the collection, to pick combination from, must be no less than
   *                         {@code 0} .
   * @param numberToPick the number of elements to pick from the collection, must be no less than {@code 0} and no greater
   *                     than {@code numberOfElements} .
   * @return the number of of combinations for the specified {@code numberOfElements} and {@code numberToPick}.
   *
   * @throws java.lang.IllegalArgumentException if either {@code numberOfElements} or {@code numberToPick} fails to match
   *         the parameter criteria.
   */
  public BigInteger getCombinationCount(int numberOfElements, int numberToPick);

  /**
   * The method fetches the {@code ordinal}-th combination (of picking {@code target.length} number of elements from
   * {@code source}) and fills the combination result into {@code target} .
   *
   * @param source the source collection to pick the combination from, cannot be {@code null}.
   * @param target the target array to fill the combination result in, cannot be {@code null}, and its length cannot be
   *               greater than {@code source.length} .
   * @param ordinal the index of which combination result to fetch.
   *
   * @throws java.lang.NullPointerException if either {@code source} or {@code target} is {@code null}.
   * @throws java.lang.IllegalArgumentException if {@code target} fails to match its parameter criteria.
   */
  public void fetchCombination(Object[] source, Object[] target, BigInteger ordinal);
}

