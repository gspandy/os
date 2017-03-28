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

package com.wise.core.raist.taskqueue;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import com.wise.core.raist.ExceptionHandler;

/**
 * @author Lei.C (2013-12-19)
 */
class DefaultTaskQueue implements TaskQueue, Runnable {

  private final ExceptionHandler exceptionHandler;

  private final LinkedBlockingQueue<Runnable> taskQueue;

  private final AtomicBoolean running;

  private volatile Thread taskExecutionThread;

  DefaultTaskQueue(ExceptionHandler exceptionHandler) {

    this.exceptionHandler = exceptionHandler;
    this.taskQueue = new LinkedBlockingQueue<Runnable>();
    this.running = new AtomicBoolean(false);

    taskExecutionThread = null;
  }

  @Override
  public void run() {

    running.set(true);
    taskExecutionThread = Thread.currentThread();

    try {

      while(running.get()) {

        Runnable task = taskQueue.take();
        task.run();
      }
    }
    catch(InterruptedException ex) {

      // log exception
    }
    finally {

      running.set(false);
      taskExecutionThread = null;
    }
  }

  @Override
  public void shutdown(long timeout) {

    if( !running.getAndSet(false) )
      return;

    invokeLater(EmptyTask.INSTANCE);
    new Timer().schedule(this.new ShutdownTimerTask(), timeout);
  }

  @Override
  public void invokeLater(Runnable task) {

    invokeLater(task, exceptionHandler);
  }

  @Override
  public void invokeLater(Runnable task, ExceptionHandler exceptionHandler) {

    if( !running.get() )
      throw new IllegalStateException("TaskQueue is already shutdown.");

    RunnableTask runnableTask = new RunnableTask(task, exceptionHandler);
    taskQueue.offer(runnableTask);
  }

  @Override
  public <R> R invokeAndWait(Callable<R> task) {

    if( !running.get() )
      throw new IllegalStateException("TaskQueue is already shutdown.");

    try {

      if( isTaskExecutionThread() ) {

        R result = task.call();
        return result;
      }
      else {

        CallableTask<R> callableTask = new CallableTask<R>(task);
        taskQueue.offer(callableTask);
        R result = callableTask.getResult();
        return result;
      }
    }
    catch(Exception ex) {

      throw new TaskExecutionException(ex.getMessage(), ex);
    }
  }

  @Override
  public boolean isTaskExecutionThread() {

    if( !running.get() )
      throw new IllegalStateException("TaskQueue is already shutdown.");

    return Thread.currentThread() == taskExecutionThread;
  }

  private class ShutdownTimerTask extends TimerTask {

    @Override
    public void run() {

      Thread runningThread = taskExecutionThread;
      if( runningThread == null )
        return;
      else
        runningThread.interrupt();
    }
  }

  private static enum EmptyTask implements Runnable {

    INSTANCE;

    @Override
    public void run() {}
  }

  private static class RunnableTask implements Runnable {

    private final Runnable runnable;
    private final ExceptionHandler handler;

    private RunnableTask(Runnable runnable, ExceptionHandler handler) {

      this.runnable = runnable;
      this.handler = handler;
    }

    @Override
    public void run() {

      try {

        runnable.run();
      }
      catch(Exception ex) {

        if( handler != null )
          handler.exceptionOccur(ex);
        // todo: else, log the exception
      }
    }
  }

  private static class CallableTask<R> implements Runnable {

    private final Callable<R> callable;

    private final CountDownLatch cd;

    private volatile R result;

    private volatile Exception exception;

    private CallableTask(Callable<R> callable) {

      this.callable = callable;
      this.cd = new CountDownLatch(1);
    }

    @Override
    public void run() {

      try {

        R r = callable.call();
        result = r;
      }
      catch(Exception ex) {

        exception = ex;
      }
      finally {

        cd.countDown();
      }
    }

    private R getResult() throws Exception {

      cd.await();
      if( exception != null )
        throw exception;
      return result;
    }
  }
}
