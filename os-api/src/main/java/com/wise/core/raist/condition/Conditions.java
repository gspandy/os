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
 * This class serves as a static factory class, and exports commonly used
 * {@link Condition} instances.
 *
 * @author Lei.C (2014-01-06)
 */
public class Conditions {

  @SuppressWarnings({ "unchecked"})
  public static <E> Condition<E> dummyTrue() {

    return (Condition<E>) DummyCondition.True;
  }

  @SuppressWarnings({"unchecked"})
  public static <E> Condition<E> dummyFalse() {

    return (Condition<E>) DummyCondition.False;
  }

  public static <E> Condition<E> not(Condition<? super E> condition) {

    if (condition == null)
      throw new NullPointerException("Condition<? super E> 'condition' is null.");

    return new ConditionNot<E>(condition);
  }

  public static <E> Condition.Builder<E> newBuilder(Condition<E> base) {

    if (base == null)
      throw new NullPointerException("Condition<E> 'base' is null.");

    return new DefaultConditionBuilder<E>(base);
  }

  /*
   * This enum defines the "dummy conditions", which will always return a
   * pre-defined result without checking the given item.
   */
  @SuppressWarnings("rawtypes")
  private static enum DummyCondition implements Condition {

    /*
     * This instance always returns 'true' for any item.
     */
    True  (true),

    /*
     * This instance always returns 'false' for any item.
     */
    False (false),
    ;

    /*
     * The pre-defined result to return.
     */
    private final boolean result;

    private DummyCondition(boolean result) {

      this.result = result;
    }

    @Override
    public boolean match(Object item) {

      return result;
    }

    @SuppressWarnings("unchecked")
	@Override
    public Builder derive() {

      return Conditions.newBuilder(this);
    }
  }

  /*
   * The class is designed not to be instantiated or inherited.
   */
  private Conditions() {}
}
