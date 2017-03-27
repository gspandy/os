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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import n.core.raist.ExceptionHandler;

/**
 * @author Lei.C (2013-12-19)
 */
public final class TaskQueues {

  public static TaskQueue.Manager getManager() {

    return DefaultManager.INSTANCE;
  }

  private static enum DefaultManager implements TaskQueue.Manager {

    INSTANCE;

    private ExceptionHandler exceptionHandler;

    @Override
    public TaskQueue.Manager setDefaultExceptionHandler(ExceptionHandler exceptionHandler) {

      this.exceptionHandler = exceptionHandler;
      return this;
    }

    @Override
    public TaskQueue startTaskQueue(ThreadFactory factory) {

      ExecutorService executorService;

      if( factory == null )
        executorService = Executors.newSingleThreadExecutor();
      else
        executorService = Executors.newSingleThreadExecutor(factory);

      DefaultTaskQueue result = new DefaultTaskQueue(exceptionHandler);
      executorService.execute(result);
      return result;
    }
  }

  private TaskQueues() {}
}
