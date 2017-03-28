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

package com.hitler.core.raist.progress;

/**
 * This interface defines the callback methods for monitoring the progress of
 * a potentially time-consuming task.
 *
 * @author Lei CHEN (2014-04-21)
 */
public interface ProgressMonitor {

  /**
   * This method is to report the progress update.
   *
   * @param current the current amount of progress, as an {@code int} value.
   * @param total the total amount of work to do, as an {@code int} value.
   */
  public void progressUpdated(int current, int total);

  /**
   * This method is to report any message update.
   *
   * @param message the message to report.
   */
  public void progressMessage(String message);

  /**
   * This method is to invoke when the monitored work is done.
   */
  public void progressDone();

  /**
   * this method is to report that the monitored work is terminated for some
   * reason; i.e. user canceled, exception occurred, etc.
   *
   * @param message the message that indicates the reason why the work is
   *                terminated.
   */
  public void progressTerminated(String message);
}
