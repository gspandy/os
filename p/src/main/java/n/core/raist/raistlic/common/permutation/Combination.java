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

package n.core.raist.raistlic.common.permutation;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.ZERO;

/**
 * The class provides a convenient way of querying combinations of picking a specified number of
 * elements from a specified collection.
 *
 * <p/>
 * The class is NOT thread safe.
 *
 * <p/>
 * This class re-uses one array to fetch the combination, so if the user want to keep the i-th
 * combination result, make a copy.
 *
 * @author lei.c
 * @since 2012-08-07
 */
public class Combination<E> implements Iterable<List<E>> {

  public static final CombinationAlgorithm DEFAULT_ALGORITHM =
          AlgorithmVER01.INSTANCE;

  /**
   * A static factory method that creates and returns a {@link Combination} instance for picking up
   * {@code numberToPick} number of elements from the specified {@code elements} collection, using
   * the {@link #DEFAULT_ALGORITHM} as the combination algorithm.
   *
   * <p/>
   * The method is a convenient overloading for {@link #of(java.util.Collection, int, CombinationAlgorithm)}
   * using the {@link #DEFAULT_ALGORITHM} .
   *
   * @param elements the collection of elements to pick combinations from, cannot be {@code null}.
   * @param numberToPick the number of elements to pick, cannot be less than {@code 0} or greater
   *                     than {@code elements.length}.
   * @param <E> the referenced generic element type.
   * @return the created {@link n.core.raist.raistlic.common.permutation.Combination} instance.
   *
   * @throws java.lang.NullPointerException if {@code elements} is {@code null}.
   * @throws java.lang.IllegalArgumentException if {@code numberToPick} does not match its parameter
   *         criteria.
   */
  public static <E> Combination<E> of(Collection<E> elements, int numberToPick) {

    return of(elements, numberToPick, DEFAULT_ALGORITHM);
  }

  /**
   * A static factory method that creates and returns a {@link Combination} instance for picking up
   * {@code numberToPick} number of elements from the specified {@code elements} collection, using
   * the specified combination {@code algorithm}.
   *
   * @param elements the collection of elements to pick combinations from, cannot be {@code null}.
   * @param numberToPick the number of elements to pick, cannot be less than {@code 0} or greater
   *                     than {@code elements.length}.
   * @param algorithm the combination algorithm to use, {@link #DEFAULT_ALGORITHM} will be used in
   *                  case it is {@code null}.
   * @param <E> the referenced generic element type.
   * @return the created {@link n.core.raist.raistlic.common.permutation.Combination} instance.
   *
   *
   * @throws java.lang.NullPointerException if {@code elements} is {@code null}.
   * @throws java.lang.IllegalArgumentException if {@code numberToPick} does not match its parameter
   *         criteria.
   */
  public static <E> Combination<E> of(Collection<E> elements,
                                      int numberToPick,
                                      CombinationAlgorithm algorithm) {

    if (elements == null) {

      throw new NullPointerException("'elements' is null.");
    }

    if (numberToPick < 0) {

      throw new IllegalArgumentException("Invalid 'numberToPick': " + numberToPick);
    }

    if (numberToPick > elements.size()) {

      throw new IllegalArgumentException(
              "Invalid number of elements to pick : " + numberToPick + " / " + elements.size()
      );
    }

    if (algorithm == null) {

      algorithm = DEFAULT_ALGORITHM;
    }

    return new Combination<E>(elements, numberToPick, algorithm);
  }

  /**
   * The collection from which to pick elements.
   */
  private E[] elements;

  /**
   * The picked elements, as the result of a combination fetch.
   */
  private E[] picked;

  /**
   * The combination algorithm.
   */
  private CombinationAlgorithm algorithm;

  /**
   * The total number of combinations C(elements.length, picked.length).
   */
  private BigInteger count;

  @SuppressWarnings("unchecked")
  private Combination(Collection<E> elements,
                      int numberToPick,
                      CombinationAlgorithm algorithm) {

    this.elements = (E[]) elements.toArray();
    this.picked = (E[]) new Object[numberToPick];
    this.algorithm = algorithm;
    this.count = this.algorithm.getCombinationCount(
            this.elements.length, numberToPick);
  }

  /**
   * The method returns the total number of possible combinations.
   *
   * @return the total number of possible combinations.
   */
  public BigInteger getCombinationCount() {

    return count;
  }

  /**
   * The method returns the {@code ordinal}-th combination from the (ordered) list of all possible
   * combinations.
   *
   * @param ordinal the index of which combination to get, cannot be {@code null}, and must be within
   *                range {@code [0, getCombinationCount())}.
   * @return the {@code ordinal}-th combination.
   * @throws java.lang.NullPointerException      if {@code ordinal} is {@code null}.
   * @throws java.lang.IndexOutOfBoundsException if ordinal is out
   */
  public List<E> getCombination(BigInteger ordinal) {

    algorithm.fetchCombination(elements, picked, ordinal);
    return Arrays.asList(picked);
  }

  /**
   * {@inheritDoc}
   *
   * <p/>
   * The iterator returned from the method is read-only, any modification call may cause an
   * {@link UnsupportedOperationException} thrown.
   */
  @Override
  public Iterator<List<E>> iterator() {

    return new OrdinalIterator();
  }

  private class OrdinalIterator implements Iterator<List<E>> {

    private BigInteger ordinal;

    private OrdinalIterator() {

      ordinal = ZERO;
    }

    @Override
    public boolean hasNext() {

      return ordinal.compareTo(getCombinationCount()) < 0;
    }

    @Override
    public List<E> next() {

      List<E> result = getCombination(ordinal);
      ordinal = ordinal.add(ONE);
      return result;
    }

    @Override
    public void remove() {

      throw new UnsupportedOperationException("Cannot remove from read-only iterator.");
    }
  }

  private static enum AlgorithmVER01 implements CombinationAlgorithm {

    INSTANCE;

    @Override
    public int getMaxSupportedSize() {

      return MAX_SUPPORT;
    }

    @Override
    public BigInteger getCombinationCount(int numberOfElements, int numberToPick) {

      if (numberOfElements < 0) {

        throw new IllegalArgumentException(
                "Invalid number of elements : " + numberOfElements);
      }

      if (numberOfElements > getMaxSupportedSize()) {

        throw new IllegalArgumentException(
                "Number of elements out of range : " + numberOfElements);
      }

      if (numberToPick < 0 || numberToPick > numberOfElements) {

        throw new IllegalArgumentException(
                "Invalid number to pick : " + numberToPick);
      }

      if (numberToPick == 0 || numberToPick == numberOfElements)
        return ONE;
      else
        return Calculator.factorial(numberOfElements).divide(
                Calculator.factorial(numberToPick).multiply(
                        Calculator.factorial(numberOfElements - numberToPick)));
    }

    @Override
    public void fetchCombination(Object[] source,
                                 Object[] target,
                                 BigInteger ordinal) {

      if (source == null) {

        throw new NullPointerException("'source' is null.");
      }

      if (target == null) {

        throw new NullPointerException("'target' is null.");
      }

      if (target.length > source.length) {

        throw new IllegalArgumentException("target.length is greater than source.length.");
      }

      if (ordinal == null) {

        throw new NullPointerException("'ordinal' is null.");
      }

      if (ordinal.compareTo(ZERO) < 0) {

        throw new IndexOutOfBoundsException("Index out of bounds: " + ordinal);
      }

      BigInteger combinationCount = getCombinationCount(source.length, target.length);
      if (ordinal.compareTo(combinationCount) >= 0) {

        throw new IndexOutOfBoundsException(
                "Index out of bounds: " + ordinal + " / " + combinationCount
        );
      }

      for (int i = 0, si = 0; i < target.length; i++, si++) {

        if (ordinal.compareTo(ZERO) > 0) {

          BigInteger cLeft = getCombinationCount(
                  source.length - si - 1, target.length - i - 1);
          while (ordinal.compareTo(cLeft) >= 0) {

            si++;
            ordinal = ordinal.subtract(cLeft);
            if (ordinal.compareTo(ZERO) == 0)
              break;
            cLeft = getCombinationCount(
                    source.length - si - 1, target.length - i - 1);
          }
        }
        target[i] = source[si];
      }
    }

    private static final int MAX_SUPPORT = Integer.MAX_VALUE;
  }
}
