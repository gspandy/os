/*
 * Copyright 2013 Lei CHEN (raistlic@gmail.com)
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

package com.hitler.core.raist.progress;

/**
 * This class is to encapsulate the concept 'progress', it is immutable and
 * instantiation controlled, it reuses an instance for the same percentage of
 * progress.
 *
 * @author Lei.C (2013-12-05)
 */
public final class Progress {

  /**
   * This is a static factory method, that returns a {@link Progress} object
   * for the specified amount of progress and total work.
   *
   * @param progress the progress amount as an {@code int} value.
   * @param total the total amount as an {@code int} value.
   * @return the {@link Progress} object of the specified progress and total
   *         amount.
   */
  public static Progress of(int progress, int total) {

    if( progress < 0 )
      throw new IllegalArgumentException("Invalid progress amount: " + progress);

    return POOL[Math.min(TOTAL, progress * 100 / total)];
  }

  public static final Progress CANCELED;
  public static final Progress FAILED;
  public static final Progress DONE;

  private final int progress;

  private Progress(int progress) {

    this.progress = progress;
  }

  public int progress() {

    return progress;
  }

  public int total() {

    return TOTAL;
  }

  public float percent() {

    return (float)progress / TOTAL;
  }

  private static final int TOTAL;
  private static final Progress[] POOL;

  static {

    TOTAL = 100;

    CANCELED = new Progress(-2);
    FAILED = new Progress(-1);
    DONE = new Progress(TOTAL);

    POOL = new Progress[TOTAL + 1];
    for(int i = 0; i < TOTAL; i++){

      POOL[i] = new Progress(i);
    }
    POOL[TOTAL] = DONE;
  }
}
