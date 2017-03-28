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
 * This class provides methods for frequently queried questions about the bit
 * map of any byte pattern. The questions are listed below:
 *
 * (1) - rankOne(byte b, int i) how many '1' bits are there in the specified byte
 *       pattern b, up-to the i-th bit, inclusively?
 *
 * (2) - selectOne(byte b, int i) what is the bit index of the i-th '1' in the
 *       byte pattern b? note that i is from 0 to 7, in case there are insufficient
 *       '1's in the byte pattern, return an index of -1 to indicate that there
 *       is no 'i-th 1'.
 *
 * (3) - rankZero(byte b, int i) similar to (1), just instead of query against
 *       '1's, query against '0's.
 *
 * (4) - selectZero(byte b, int i) similar to (2), just instead of query against
 *       '1's, query against '0's.
 *
 * @author Lei.C (2014-01-14)
 */
final class BitMapUtil {

  static int rankOne(byte b, int index) {

    return RANK_DIC[255 & b][index];
  }

  static int rankZero(byte b, int index) {

    return (index + 1) - rankOne(b, index);
  }

  static int selectOne(byte b, int index) {

    return SELECT_ONE[255 & b][index];
  }

  static int selectZero(byte b, int index) {

    return SELECT_ZERO[255 & b][index];
  }

  static boolean isSet(byte b, int index) {

    return ((1 << index) & b) != 0;
  }

  static byte set(byte b, int index) {

    return (byte) ((1 << index) | (255 & b));
  }

  static byte unset(byte b, int index) {

    return (byte) (255 & (~(1 << index) & (255 & b)));
  }

  private static final byte[][] RANK_DIC;
  private static final byte[][] SELECT_ONE;
  private static final byte[][] SELECT_ZERO;

  static {

    // 6 KB static space

    RANK_DIC = new byte[256][8];
    SELECT_ONE = new byte[256][8];
    SELECT_ZERO = new byte[256][8];

    // all byte patterns

    for (int i = 0; i < 256; i++) {

      for (int j = 0, curr = 0; j < 8; j++) {

        SELECT_ONE[i][j] = (byte)-1;
        SELECT_ZERO[i][j] = (byte)-1;
        int bit = ((1 << j) & i) == 0 ? 0 : 1;
        curr += bit;
        RANK_DIC[i][j] = (byte)curr;
        if (bit > 0)
          SELECT_ONE[i][curr - 1] = (byte)j;
        else
          SELECT_ZERO[i][j - curr] = (byte)j;
      }
    }
  }

  /*
   * This is a static utility class, which is designed not to be instantiated
   * or inherited.
   */
  private BitMapUtil() {}
}
