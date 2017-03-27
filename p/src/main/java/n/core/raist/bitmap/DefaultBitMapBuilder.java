package n.core.raist.bitmap;

/**
 * @author lei (2014-01-14)
 */
class DefaultBitMapBuilder implements BitMap.Builder {

  private byte[] map;
  private int size;

  DefaultBitMapBuilder(int bytes) {

    map = new byte[bytes];
    size = 0;
  }

  @Override
  public BitMap.Builder size(int size) {

    if (size < 0)
      throw new IllegalArgumentException("Invalid BitMap size: " + size);

    this.size = size;
    ensureCapacity(size);
    return this;
  }

  @Override
  public BitMap.Builder set(int index) {

    if (index < 0)
      throw new IllegalArgumentException("Invalid index: " + index);

    ensureCapacity(index);
    map[index / 8] |= 1 << (index % 8);
    size = Math.max(index, size);
    return this;
  }

  @Override
  public BitMap.Builder set(int index, int length) {

    if (length < 0)
      throw new IllegalArgumentException("Invalid portion length: " + length);

    for (int i = index, limit = index + length; i < limit; i++)
      set(i);
    return this;
  }

  @Override
  public BitMap.Builder unset(int index) {

    if (index < 0)
      throw new IllegalArgumentException("Invalid index: " + index);

    ensureCapacity(index);
    map[index / 8] &= ~(1 << (index % 8));
    size = Math.max(index, size);
    return this;
  }

  @Override
  public BitMap.Builder unset(int index, int length) {

    if (length < 0)
      throw new IllegalArgumentException("Invalid portion length: " + length);

    for (int i = index, limit = index + length; i < limit; i++)
      unset(i);
    return this;
  }

  @Override
  public BitMap.Builder flip() {

    for (int i = 0; i < map.length; i++)
      map[i] = (byte) (255 & (~(255 & map[i])));
    return this;
  }

  @Override
  public BitMap.Builder clear() {

    for (int i = 0; i < map.length; i++)
      map[i] = 0;
    return this;
  }

  @Override
  public BitMap build() {

    return new DefaultBitMap(map, size);
  }

  @Override
  public boolean isReady() {

    return true;
  }

  private void ensureCapacity(int capacity) {

    int length = capacity / 8 + 1;
    if (map.length < length) {

      byte[] newMap = new byte[length];
      System.arraycopy(map, 0, newMap, 0, map.length);
      map = newMap;
    }
  }
}
