package advent.day16;

import advent.common.BitSetHelpers;
import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import java.math.BigInteger;
import java.util.BitSet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Day16 {
  enum Operators {
    SUM(0),
    PRODUCT(1),
    MINIMUM(2),
    MAXIMUM(3),
    GT(5),
    LT(6),
    EQ(7);

    private final int binaryRepresentation;

    Operators(int binaryRepresentation) {
      this.binaryRepresentation = binaryRepresentation;
    }

    public static Operators fromBinaryRepresentation(int binaryRepresentation) {
      return List.of(values())
          .find(v -> v.binaryRepresentation == binaryRepresentation)
          .getOrNull();
    }
  }

  private static final Map<Operators, BiFunction<Long, Long, Long>> OPERATOR_MAP =
      HashMap.of(
          Operators.SUM, Long::sum,
          Operators.PRODUCT, (a, b) -> a * b,
          Operators.MINIMUM, Math::min,
          Operators.MAXIMUM, Math::max,
          Operators.GT, (a, b) -> a > b ? 1L : 0,
          Operators.LT, (a, b) -> a < b ? 1L : 0,
          Operators.EQ, (a, b) -> a.equals(b) ? 1L : 0);

  private static final Map<Operators, Function<List<Long>, Long>> OPERATOR_LIST_MAP =
      HashMap.of(
          Operators.SUM, a -> a.sum().longValue(),
          Operators.PRODUCT, a -> a.product().longValue(),
          Operators.MINIMUM, a -> a.min().getOrNull(),
          Operators.MAXIMUM, a -> a.max().getOrNull());

  private static final short ENDING_BIT_GROUP_FOR_LITERAL = 0;
  private static final short TOTAL_LENGTH_TYPE = 0;
  private static final short LITERAL_TYPE = 4;
  private static final int VERSION_BIT_LENGTH = 3;
  private static final int TYPE_BIT_LENGTH = 3;
  private static final int LENGTH_TYPE_BIT_LENGTH = 1;
  private static final int BIT_GROUP_FOR_LITERAL_LENGTH = 4;
  private static final int BIT_GROUP_START_FOR_LITERAL_LENGTH = 1;
  private static final int TOTAL_BIT_LENGTH_SIZE = 15;
  private static final int SUBPACKET_LENGTH = 11;

  public static int addVersionNumbers(String hexString) {
    BitSet bitSet = toBitSet(hexString);
    int position = hexString.length() * 4;
    int firstNonZeroBit = bitSet.nextSetBit(0);
    int versionSum = 0;
    while (position > firstNonZeroBit) {
      short version = getShort(bitSet, position, VERSION_BIT_LENGTH);
      position = position - VERSION_BIT_LENGTH;
      versionSum += version;
      short typeId = getShort(bitSet, position, TYPE_BIT_LENGTH);
      position = position - TYPE_BIT_LENGTH;
      if (isLiteralValue(typeId)) {
        boolean stillParsingLiteral = true;
        while (stillParsingLiteral) {
          short bitGroupStart = getShort(bitSet, position, BIT_GROUP_START_FOR_LITERAL_LENGTH);
          position = position - BIT_GROUP_START_FOR_LITERAL_LENGTH;
          if (bitGroupStart == ENDING_BIT_GROUP_FOR_LITERAL) {
            stillParsingLiteral = false;
          }
          position = position - BIT_GROUP_FOR_LITERAL_LENGTH;
        }
      } else {
        // is packet
        Operators op = Operators.fromBinaryRepresentation(typeId);
        short lengthTypeId = getShort(bitSet, position, LENGTH_TYPE_BIT_LENGTH);
        position = position - LENGTH_TYPE_BIT_LENGTH;
        if (isTotalLengthType(lengthTypeId)) {
          // handle total length type
          short totalLength = getShort(bitSet, position, TOTAL_BIT_LENGTH_SIZE);
          position = position - TOTAL_BIT_LENGTH_SIZE;
          // DONT DO THIS WE NEED THE VERSIONS
          // position = position - totalLength;
        } else {
          // is number of sub packets type id
          short subPackets = getShort(bitSet, position, SUBPACKET_LENGTH);
          position = position - SUBPACKET_LENGTH;
        }
      }
    }
    return versionSum;
  }

  public static long evaluate(String hexString) {
    return evaluate(toBitSet(hexString), new AtomicInteger(hexString.length() * 4));
  }

  private static long evaluate(BitSet bitSet, AtomicInteger position) {
    position.set(position.get() - VERSION_BIT_LENGTH);
    short typeId = getShort(bitSet, position.get(), TYPE_BIT_LENGTH);
    position.set(position.get() - TYPE_BIT_LENGTH);

    if (isLiteralValue(typeId)) {

      short bitGroupStart = getShort(bitSet, position.get(), BIT_GROUP_START_FOR_LITERAL_LENGTH);
      position.set(position.get() - BIT_GROUP_START_FOR_LITERAL_LENGTH);
      List<BitSet> bitSets = List.empty();
      bitSets =
          bitSets.prepend(
              subBitSet(bitSet, position.get() - BIT_GROUP_FOR_LITERAL_LENGTH, position.get()));
      position.set(position.get() - BIT_GROUP_FOR_LITERAL_LENGTH);

      while (bitGroupStart != ENDING_BIT_GROUP_FOR_LITERAL) {
        bitGroupStart = getShort(bitSet, position.get(), BIT_GROUP_START_FOR_LITERAL_LENGTH);
        position.set(position.get() - BIT_GROUP_START_FOR_LITERAL_LENGTH);
        bitSets =
            bitSets.prepend(
                subBitSet(bitSet, position.get() - BIT_GROUP_FOR_LITERAL_LENGTH, position.get()));
        position.set(position.get() - BIT_GROUP_FOR_LITERAL_LENGTH);
      }

      return merge(bitSets);
    }

    short lengthTypeId = getShort(bitSet, position.get(), LENGTH_TYPE_BIT_LENGTH);
    position.set(position.get() - LENGTH_TYPE_BIT_LENGTH);

    Operators op = Operators.fromBinaryRepresentation(typeId);
    if (isTotalLengthType(lengthTypeId)) {
      int totalLength = getInt(bitSet, position.get(), TOTAL_BIT_LENGTH_SIZE);
      position.set(position.get() - TOTAL_BIT_LENGTH_SIZE);
      int start = position.get();
      // DONT DO THIS WE NEED THE VERSIONS
      // position = position - totalLength;
      long val1 = evaluate(bitSet, position);
      List<Long> values = List.of(val1);
      while (start - position.get() < totalLength) {
        long val = evaluate(bitSet, position);
        values = values.append(val);
      }
      if (values.size() == 2) {
        long val2 = values.get(1);
        return OPERATOR_MAP.get(op).getOrNull().apply(val1, val2);
      }

      return OPERATOR_LIST_MAP.get(op).getOrNull().apply(values);
    } else {
      // is number of sub packets type id
      short subPackets = getShort(bitSet, position.get(), SUBPACKET_LENGTH);
      position.set(position.get() - SUBPACKET_LENGTH);
      long val1 = evaluate(bitSet, position);
      if (subPackets == 2) {
        long val2 = evaluate(bitSet, position);
        return OPERATOR_MAP.get(op).getOrNull().apply(val1, val2);
      }
      List<Long> values = List.of(val1);
      int subpacketsProcessed = 1;
      while (subpacketsProcessed < subPackets) {
        values = values.append(evaluate(bitSet, position));
        subpacketsProcessed++;
      }
      return OPERATOR_LIST_MAP.get(op).getOrNull().apply(values);
    }
  }

  private static long merge(List<BitSet> bitSets) {
    BitSet bitSet = new BitSet();
    bitSets
        .zipWithIndex()
        .forEach(
            bitSetAndIndex -> {
              int index = bitSetAndIndex._2();
              BitSet currentBitSet = bitSetAndIndex._1();
              for (int i = 0; i < 4; i++) {
                bitSet.set(4 * index + i, currentBitSet.get(i));
              }
            });
    return BitSetHelpers.toLong(bitSet);
  }

  private static boolean isTotalLengthType(short lengthTypeId) {
    return lengthTypeId == TOTAL_LENGTH_TYPE;
  }

  private static boolean isLiteralValue(short typeId) {
    return typeId == LITERAL_TYPE;
  }

  private static BitSet toBitSet(String hexString) {
    BigInteger bigInteger = toBigInteger(hexString);
    BitSet bitSet = new BitSet();
    for (int i = 0; i < bigInteger.bitLength(); i++) {
      bitSet.set(i, bigInteger.testBit(i));
    }
    return bitSet;
  }

  public static BigInteger toBigInteger(String hexString) {
    return new BigInteger(hexString, 16);
  }

  private static short getShort(BitSet packets, int packetLength, int numBits) {
    BitSet bs = new BitSet();
    for (int i = 0; i < numBits; i++) {
      int packetIndex = packetLength - numBits + i;
      bs.set(i, packets.get(packetIndex));
    }
    return BitSetHelpers.toShort(bs);
  }

  private static int getInt(BitSet packets, int packetLength, int numBits) {
    BitSet bs = new BitSet();
    for (int i = 0; i < numBits; i++) {
      int packetIndex = packetLength - numBits + i;
      bs.set(i, packets.get(packetIndex));
    }
    return BitSetHelpers.toInt(bs);
  }

  private static BitSet subBitSet(BitSet bitSet, int start, int end) {
    BitSet bs = new BitSet();
    for (int i = 0; i < end - start; i++) {
      bs.set(i, bitSet.get(start + i));
    }
    return bs;
  }
}
