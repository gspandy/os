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

/**
 * @author Lei.C (2014-01-06)
 */
class ConditionOr<E> implements Condition<E> {

  private Condition<? super E> a;

  private Condition<? super E> b;

  ConditionOr(Condition<? super E> a, Condition<? super E> b) {

    this.a = a;
    this.b = b;
  }

  @Override
  public boolean match(E item) {

    return a.match(item) || b.match(item);
  }

  @Override
  public Builder<E> derive() {

    return Conditions.newBuilder(this);
  }
}
