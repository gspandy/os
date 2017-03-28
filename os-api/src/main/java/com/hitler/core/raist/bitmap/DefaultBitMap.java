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

package com.hitler.core.raist.bitmap;

/**
 * (package-private class)
 *
 * The default implementation of {@link BitMap} interface.
 *
 * @author Lei.C (2014-01-14)
 */
class DefaultBitMap implements BitMap {

  private int size;
  private byte[] map;
  private int[] rankOnes;
  private int[] rankZeros;

  DefaultBitMap(int size) {

    this(new byte[size/8 + 1], size);
  }

  DefaultBitMap(byte[] map, int size) {

    assert map != null;
    assert size > 0;
    assert map.length * 8 >= size;

    this.size = size;
    this.map = new byte[size/8 + 1];
    System.arraycopy(map, 0, this.map, 0, Math.min(this.map.length, map.length));

    rankOnes = new int[map.length];
    rankZeros = new int[map.length];

    rankOnes[0] = BitMapUtil.rankOne(map[0], 7);
    rankZeros[0] = BitMapUtil.rankZero(map[0], 7);

    for (int i = 1; i < map.length; i++) {

      rankOnes[i] = rankOnes[i - 1] + BitMapUtil.rankOne(map[i], 7);
      rankZeros[i] = rankZeros[i - 1] + BitMapUtil.rankZero(map[i], 7);
    }
  }

  @Override
  public int size() {

    return size;
  }

  @Override
  public int rankOne(int index) {

    if (index < 0)
      throw new IllegalArgumentException("Invalid index : " + index);

    index = Math.min(size - 1, index);
    int step = index / 8;
    int mod = index % 8;

    return (step > 0 ? rankOnes[step - 1] : 0) + BitMapUtil.rankOne(map[step], mod);
  }

  @Override
  public int rankZero(int index) {

    if (index < 0)
      throw new IllegalArgumentException("Invalid index : " + index);

    index = Math.min(size - 1, index);
    int step = index / 8;
    int mod = index % 8;

    return (step > 0 ? rankZeros[step - 1] : 0) + BitMapUtil.rankZero(map[step], mod);
  }

  @Override
  public int selectOne(int index) {

    if (index < 0)
      throw new IllegalArgumentException("Invalid index : " + index);

    if (rankOnes[rankOnes.length - 1] < index + 1)
      return -1;

    int step = stepForSelectOne(index);
    int remain = index - (step > 0 ? rankOnes[step - 1] : 0);
    return 8 * step + BitMapUtil.selectOne(map[step], remain);
  }

  private int stepForSelectOne(int index) {

    int left = 0;
    int right = rankOnes.length - 1;

    while (right > left) {

      int next = (right + left) / 2;
      if (rankOnes[next] > index) {

        if (next == left)
          return left;
        else
          right = next;
      }
      else {

        if (next == left)
          return right;
        else
          left = next;
      }
    }
    return right; // theoretically should never happen, just for compiling
  }

  @Override
  public int selectZero(int index) {

    if (index < 0)
      throw new IllegalArgumentException("Invalid index : " + index);

    if (rankZeros[rankZeros.length - 1] < index + 1)
      return -1;

    int step = stepForSelectZero(index);
    int remain = index - (step > 0 ? rankZeros[step - 1] : 0);
    return 8 * step + BitMapUtil.selectZero(map[step], remain);
  }

  private int stepForSelectZero(int index) {

    int left = 0;
    int right = rankZeros.length - 1;

    while (right > left) {

      int next = (right + left) / 2;
      if (rankZeros[next] > index) {

        if (next == left)
          return left;
        else
          right = next;
      }
      else {

        if (next == left)
          return right;
        else
          left = next;
      }
    }
    return right;
  }

  @Override
  public void set(int index) {

    if (index < 0 || index >= size)
      throw new IndexOutOfBoundsException("Index out of bounds: " + index + "/" + size);

    int step = index / 8;
    int mod = index % 8;

    if (!BitMapUtil.isSet(map[step], mod)) {

      map[step] = BitMapUtil.set(map[step], mod);
      for (int i = step; i < rankOnes.length; i++) {

        rankOnes[i] ++;
        rankZeros[i] --;
      }
    }
  }

  @Override
  public void unset(int index) {

    if (index < 0 || index >= size)
      throw new IndexOutOfBoundsException("Index out of bounds: " + index + "/" + size);

    int step = index / 8;
    int mod = index % 8;

    if (BitMapUtil.isSet(map[step], mod)) {

      map[step] = BitMapUtil.unset(map[step], mod);
      for (int i = step; i < rankOnes.length; i++) {

        rankOnes[i] --;
        rankZeros[i] ++;
      }
    }
  }
}
