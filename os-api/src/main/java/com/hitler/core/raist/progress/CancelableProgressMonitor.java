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
 * This interface extends the functionality provided by its parent interface
 * {@link com.hitler.core.raist.progress.ProgressMonitor}, and makes it possible for
 * the progress to cancel.
 *
 * @author Lei CHEN (2014-04-21)
 */
public interface CancelableProgressMonitor extends ProgressMonitor {

  /**
   * This is a callback method, for the progress code to report whether the
   * work it's doing can now be canceled or not. For example, the UI sits behind
   * the interface may then determine accordingly whether the widget for
   * canceling the work should be enabled or not.
   *
   * @param cancelable {@code true} if the progress can be canceled.
   */
  public void progressCancelableFlagUpdated(boolean cancelable);

  /**
   * This method is to suggest the progress whether it should be terminated, i.e.
   * the user has decided to terminate the progress. Note that the progress
   * that is monitored is expected to query this method frequently, i.e. during
   * each iteration of its work.
   *
   * @return {@code true} if the progress is suggested to terminate.
   */
  public boolean isProgressBeingCanceled();
}
