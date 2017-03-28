/*
 * Copyright 2014 Lei CHEN (raistlic@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wise.core.raist.listview;

/**
 * This interface defines the read only aspect of a list.
 *
 * @author Lei.C (2014-01-05)
 */
public interface ListView<E> extends Iterable<E> {

  /**
   * This method returns the size of the {@link ListView}.
   *
   * @return the size of the {@link ListView}.
   */
  public int size();

  /**
   * This method returns the element in the {@link ListView}, at
   * the specified {@code index}.
   *
   * @param index the specified index to retrieve
   * @return the element in the {@link ListView}, at the specified
   *         {@code index}.
   * @throws java.lang.IndexOutOfBoundsException if the specified {@code index}
   *         is out of bounds (either less than {@code 0} or equals to or greater
   *         than {@link #size()}.
   */
  public E get(int index);

  /**
   * This method returns the index of the specified {@code element} in the
   * {@link ListView}, or {@code -1} if the specified
   * {@code element} is not found.
   *
   * @param element the element to search in the {@link ListView}.
   * @return the index of the specified {@code element}, or {@code -1} if the
   *         element is not found.
   */
  public int indexOf(E element);

  /**
   * This method returns whether the specified {@code element} is present in the
   * {@link ListView}.
   *
   * @param element the element to search in the {@link ListView}.
   * @return {@code true} if the specified {@code element} is present in the
   *         {@link ListView}.
   */
  public boolean contains(E element);
}
