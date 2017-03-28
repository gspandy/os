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

package com.hitler.core.raist.condition;

/**
 * @author Lei.C (2014-01-06)
 */
class DefaultConditionBuilder<E> implements Condition.Builder<E> {

  private Condition<E> condition;

  DefaultConditionBuilder(Condition<E> condition) {

    this.condition = condition;
  }

  @Override
  public Condition.Builder<E> and(Condition<? super E> condition) {

    if (condition == null)
      throw new NullPointerException("Condition<? super E> 'condition' is null.");

    this.condition = new ConditionAnd<E>(this.condition, condition);
    return this;
  }

  @Override
  public Condition.Builder<E> or(Condition<? super E> condition) {

    throw new UnsupportedOperationException("not implemented yet.");
  }

  @Override
  public Condition.Builder<E> not() {

    throw new UnsupportedOperationException("not implemented yet.");
  }

  @Override
  public Condition<E> build() {

    throw new UnsupportedOperationException("not implemented yet.");
  }

  @Override
  public boolean isReady() {

    throw new UnsupportedOperationException("not implemented yet.");
  }
}
