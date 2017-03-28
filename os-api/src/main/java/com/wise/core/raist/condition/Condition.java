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

package com.wise.core.raist.condition;

import com.wise.core.raist.Factory;

/**
 * This interface defines a callback to check whether an item matches some
 * certain condition.
 *
 * @author Lei.C (2014-01-06)
 */
public interface Condition<E> {

  /**
   * This method returns whether the specified {@code item} matches the condition.
   *
   * @param item the item to check.
   * @return {@code true} if the {@code item} matches the condition.
   */
  public boolean match(E item);

  public Builder<E> derive();

  public static interface Builder<E> extends Factory<Condition<E>> {

    public Builder<E> and(Condition<? super E> condition);

    public Builder<E> or(Condition<? super E> condition);

    public Builder<E> not();
  }
}
