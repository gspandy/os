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

package n.core.raist.taskqueue;

import java.util.concurrent.Callable;
import java.util.concurrent.ThreadFactory;

import n.core.raist.ExceptionHandler;
import n.core.raist.Factory;

/**
 * This interface defines the API of a single thread runnable queue implementation.
 *
 * @author Lei.C (2013-12-19)
 */
public interface TaskQueue {

  public void shutdown(long timeout);

  public void invokeLater(Runnable task);

  public void invokeLater(Runnable task, ExceptionHandler exceptionHandler);

  public <R> R invokeAndWait(Callable<R> task);

  public boolean isTaskExecutionThread();



  public static interface Manager {

    public Manager setDefaultExceptionHandler(ExceptionHandler exceptionHandler);

    public TaskQueue startTaskQueue(ThreadFactory factory);
  }
}
