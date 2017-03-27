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

package n.core.raist.listview;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * The class defines a collection of static factory methods to create
 * {@link n.core.raist.listview.ListView} instances.
 *
 * @author Lei.C (2014-06-10)
 */
public final class ListViews {

  /**
   * The class makes a copy of the specified {@code array}, and wraps the copy
   * into a {@link n.core.raist.listview.ListView} instance.
   *
   * @param array the array to copy and wrap, cannot be {@code null}.
   * @param <E> the referenced element type
   * @return the newly created {@link n.core.raist.listview.ListView} instance
   *         that is used to wrap the copy of the {@code array}.
   *
   * @throws java.lang.NullPointerException if the specified {@code array} is
   *         {@code null}.
   */
  public static <E> ListView<E> wrapCopyOfArray(E[] array) {

    if (array == null)
      throw new NullPointerException("'array' is null.");

    return new ArrayWrapper<E>(array.clone());
  }

  /**
   * The method creates a {@link n.core.raist.listview.ListView} instance that
   * wraps the specified {@code array}.
   *
   * @param array the array to wrap, cannot be {@code null}.
   * @param <E> the referenced element type.
   * @return the newly created {@link n.core.raist.listview.ListView} instance
   *         that is used to wrap the {@code array}.
   *
   * @throws java.lang.NullPointerException if the {@code array} is {@code null}.
   */
  public static <E> ListView<E> wrapArray(E[] array) {

    if (array == null)
      throw new NullPointerException("'array' is null.");

    return new ArrayWrapper<E>(array);
  }

  /**
   * The method makes a copy of the specified {@link java.util.Collection}, and
   * wraps the copy into a {@link n.core.raist.listview.ListView} instance.
   *
   * @param collection the collection to copy and wrap, cannot be {@code null}.
   * @param <E> the referenced element type.
   * @return the newly created {@link n.core.raist.listview.ListView} instance
   *         that is used to wrap the copy of the {@code collection}.
   *
   * @throws java.lang.NullPointerException if the {@code collection} is {@code null}.
   */
  public static <E> ListView<E> wrapCopyOfCollection(Collection<? extends E> collection) {

    if (collection == null)
      throw new NullPointerException("'collection' is null.");

    return new ListWrapper<E>(new ArrayList<E>(collection));
  }

  /**
   * The method creates a {@link n.core.raist.listview.ListView} instance
   * that wraps the specified {@code list}.
   *
   * @param list the list to wrap, cannot be {@code null}.
   * @param <E> the referenced element type.
   * @return the newly created {@link n.core.raist.listview.ListView} instance
   *         that is used to wrap the {@code list}.
   *
   * @throws java.lang.NullPointerException if the {@code list} is {@code null}.
   */
  public static <E> ListView<E> wrapList(List<? extends E> list) {

    if (list == null)
      throw new NullPointerException("'list' is null.");

    return new ListWrapper<E>(list);
  }

  private static class ListWrapper<E> implements ListView<E> {

    private final List<? extends E> list;

    private ListWrapper(List<? extends E> list) {

      this.list = list;
    }

    @Override
    public int size() {

      return list.size();
    }

    @Override
    public E get(int index) {

      return list.get(index);
    }

    @Override
    public int indexOf(E element) {

      return list.indexOf(element);
    }

    @Override
    public boolean contains(E element) {

      return list.contains(element);
    }

    @Override
    public Iterator<E> iterator() {

      return new IndexIterator<E>(this);
    }
  }

  private static class ArrayWrapper<E> implements ListView<E> {

    private final E[] array;

    private ArrayWrapper(E[] array) {

      this.array = array;
    }

    @Override
    public int size() {

      return array.length;
    }

    @Override
    public E get(int index) {

      return array[index];
    }

    @Override
    public int indexOf(E element) {

      for (int i = 0, len = array.length; i < len; i++) {

        E ith = array[i];
        if (element == null) {

          if (ith == null)
            return i;
        }
        else {

          if (element.equals(ith))
            return i;
        }
      }
      return -1;
    }

    @Override
    public boolean contains(E element) {

      return indexOf(element) >= 0;
    }

    @Override
    public Iterator<E> iterator() {

      return new IndexIterator<E>(this);
    }
  }

  private static class IndexIterator<E> implements Iterator<E> {

    private final ListView<E> listView;

    private int index;

    private IndexIterator(ListView<E> listView) {

      this.listView = listView;
      this.index = 0;
    }

    @Override
    public boolean hasNext() {

      return index < listView.size();
    }

    @Override
    public E next() {

      E result = listView.get(index);
      index ++;
      return result;
    }

    @Override
    public void remove() {

      throw new UnsupportedOperationException(
          "Cannot remove from read only ListView.");
    }
  }

  /**
   * The class is a static factory class that is designed not to be instantiated
   * or inherited.
   */
  private ListViews() {}
}
