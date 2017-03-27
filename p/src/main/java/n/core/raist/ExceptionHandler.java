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

package n.core.raist;

/**
 * This interface defines a callback for handling exceptions.
 *
 * @author Lei.C (2013-12-17)
 */
public interface ExceptionHandler {

  /**
   * This is a callback method for the client code to call, when an exception
   * occurs which the client code does not have sufficient information to deal
   * with.
   *
   * @param ex the exception.
   */
  public void exceptionOccur(Throwable ex);
}
