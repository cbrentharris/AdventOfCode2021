package advent.common;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.BitSet;

public class BitSetHelpers {
  public static short toShort(BitSet b) {
    ByteBuffer bb = ByteBuffer.allocate(2).put(Arrays.copyOf(b.toByteArray(), 2));
    bb.order(ByteOrder.LITTLE_ENDIAN);
    return bb.getShort(0);
  }

  public static int toInt(BitSet b) {
    ByteBuffer bb = ByteBuffer.allocate(4).put(Arrays.copyOf(b.toByteArray(), 4));
    bb.order(ByteOrder.LITTLE_ENDIAN);
    return bb.getInt(0);
  }

  public static long toLong(BitSet b) {
    ByteBuffer bb = ByteBuffer.allocate(8).put(Arrays.copyOf(b.toByteArray(), 8));
    bb.order(ByteOrder.LITTLE_ENDIAN);
    return bb.getLong(0);
  }
}
