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
 * The strategy interface for implementing the permutation algorithm.
 *
 * @author lei.c
 * @since 2014-08-20
 */
public interface PermutationAlgorithm {

  /**
   * The method returns the maximum size of collection that the algorithm can handle, or return
   * {@link Integer#MAX_VALUE} if no limit specified.
   *
   * @return the maximum size of collection that the algorithm can handle
   */
  public int getMaxSupportedSize();

  /**
   * The method returns the number of all possible permutations for the specified number of elements
   * (size of collection).
   *
   * @param numberOfElements the number of elements to permute.
   * @return the number of all possible permutations
   */
  public BigInteger getPermutationCount(int numberOfElements);

  /**
   * The method re-arranges the order of elements in the specified {@code elements} array to be
   * the {@code ordinal}-th permutation.
   *
   * @param elements the elements array to permute, cannot be {@code null}.
   * @param ordinal the index of the permutation, cannot be {@code null}, and must be with-in the
   *                valid bounds {@code [0, getPermutationCount(elements.length))} .
   *
   * @throws java.lang.NullPointerException if {@code elements} or {@code ordinal} is {@code null}.
   * @throws java.lang.IndexOutOfBoundsException if {@code ordinal} is out of the valid bounds.
   */
  public void fetchPermutation(Object[] elements, BigInteger ordinal);
}
