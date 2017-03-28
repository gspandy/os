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

package com.wise.core.raist.bitmap;

import com.wise.core.raist.Factory;

/**
 * This class provides rank and select queries for an array of binary values.
 *
 * <p/>
 * There are 4 kinds of queries can be done:
 *
 * <ul>
 *   <li>
 *     <b>rankOne : </b> count the number of '1's, up to a certain index,
 *     inclusively.
 *   </li>
 *   <li>
 *     <b>rankZero : </b> count the number of '0's, up to a certain index,
 *     inclusively.
 *   </li>
 *   <li>
 *     <b>selectOne : </b> query the index of the i-th '1' in the array.
 *   </li>
 *   <li>
 *     <b>selectZero : </b> query the index of the i-th '0' in the array.
 *   </li>
 * </ul>
 *
 * <p/>
 * Where '1' or '0' represents the binary state for 'set' or 'not set'.
 *
 * @author Lei.C (2014-01-05)
 */
public interface BitMap {

  /**
   * This method returns the size of the {@link BitMap} instance, positions that
   * exceeds the size of the map is considered 'void', which is neither '1' nor
   * '0', and all queries will be based on this consideration.
   *
   * @return the size of the {@link BitMap} instance.
   */
  public int size();

  public int rankOne(int index);

  public int rankZero(int index);

  public int selectOne(int index);

  public int selectZero(int index);

  public void set(int index);

  public void unset(int index);

  public static interface Builder extends Factory<BitMap> {

    public Builder size(int size);

    public Builder set(int index);

    public Builder set(int index, int length);

    public Builder unset(int index);

    public Builder unset(int index, int length);

    public Builder flip();

    public Builder clear();
  }
}
