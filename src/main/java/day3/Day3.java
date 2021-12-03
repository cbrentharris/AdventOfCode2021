package day3;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day3 {

  public static int powerConsuption(List<BitSet> bitSets) {
    int numBits = getMaxBitSetLength(bitSets);
    BitSet gammaBits = new BitSet(numBits);
    BitSet epsilonBits = new BitSet(numBits);
    for (int i = numBits - 1; i >= 0; i--) {
      Map<Boolean, Long> frequencies = getBitFrequencies(bitSets, i);

      boolean largestBit = frequencies.getOrDefault(true, 0L) > frequencies.getOrDefault(false, 0L);

      gammaBits.set(i, largestBit);
      epsilonBits.set(i, !largestBit);
    }
    return toShort(gammaBits) * toShort(epsilonBits);
  }

  public static int lifeSupport(List<BitSet> bitSets) {
    int numBits = getMaxBitSetLength(bitSets);
    List<BitSet> oxygenRatings = bitSets.subList(0, bitSets.size());
    List<BitSet> co2ScrubberRatings = bitSets.subList(0, bitSets.size());
    // Traverse MSB to LSB
    for (int i = numBits - 1; i >= 0; i--) {
      boolean oxygenBit = mostCommonBit(oxygenRatings, i);
      boolean co2Bit = leastCommonBit(co2ScrubberRatings, i);
      oxygenRatings = filter(i, oxygenRatings, oxygenBit);
      co2ScrubberRatings = filter(i, co2ScrubberRatings, co2Bit);
    }
    return toShort(oxygenRatings.get(0)) * toShort(co2ScrubberRatings.get(0));
  }

  private static List<BitSet> filter(int i, List<BitSet> ratings, boolean valueToKeep) {
    if (ratings.size() == 1) {
      return ratings;
    }
    return ratings.stream().filter(b -> b.get(i) == valueToKeep).collect(Collectors.toList());
  }

  /** How often a 0 or 1 occurs in a list of bit sets */
  private static Map<Boolean, Long> getBitFrequencies(Collection<BitSet> bitSets, int bitIndex) {
    return bitSets.stream()
        .map(bitSet -> bitSet.get(bitIndex))
        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
  }

  /** Find the least common bit for a given index in a list of bit sets. Tie goes to 0 */
  private static boolean leastCommonBit(Collection<BitSet> bitSets, int bitIndex) {
    Map<Boolean, Long> frequencies = getBitFrequencies(bitSets, bitIndex);
    long falseFrequency = frequencies.getOrDefault(false, 0L);
    long trueFrequency = frequencies.getOrDefault(true, 0L);
    return !(falseFrequency <= trueFrequency);
  }

  /** Find the most common bit for a given index in a list of bit sets. Tie goes to 1 */
  private static boolean mostCommonBit(Collection<BitSet> bitSets, int bitIndex) {
    Map<Boolean, Long> frequencies = getBitFrequencies(bitSets, bitIndex);
    return frequencies.getOrDefault(true, 0L) >= frequencies.getOrDefault(false, 0L);
  }

  private static short toShort(BitSet b) {
    ByteBuffer bb = ByteBuffer.allocate(2).put(Arrays.copyOf(b.toByteArray(), 2));
    bb.order(ByteOrder.LITTLE_ENDIAN);
    return bb.getShort(0);
  }

  private static int getMaxBitSetLength(List<BitSet> bitSets) {
    return bitSets.stream().mapToInt(BitSet::length).max().getAsInt();
  }
}
