package day3;

import java.util.BitSet;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day3 {

  public static int powerConsuption(List<BitSet> bitSets) {
    if (bitSets.size() == 0) {
      return 0;
    }

    int numBits = 12;
    BitSet gammaBits = new BitSet(numBits);
    BitSet epsilonBits = new BitSet(numBits);
    for (int i = 0; i < numBits; i++) {
      Map<Boolean, Long> frequencies = getBitFrequencies(bitSets, i);

      boolean largestBit = frequencies.getOrDefault(true, 0L) > frequencies.getOrDefault(false, 0L);

      gammaBits.set(i, largestBit);
      epsilonBits.set(i, !largestBit);
    }
    int gamma = toInt(gammaBits, numBits);
    int epsilon = toInt(epsilonBits, numBits);
    return gamma * epsilon;
  }

  public static int lifeSupport(List<BitSet> bitSets, int numBits) {
    Set<BitSet> oxygenRatings = new HashSet<>(bitSets);
    Set<BitSet> co2ScrubberRatings = new HashSet<>(bitSets);
    for (int i = 0; i < numBits; i++) {
      boolean oxygenBit = mostCommonBit(oxygenRatings, i);
      boolean co2Bit = leastCommonBit(co2ScrubberRatings, i);
      oxygenRatings = filter(i, oxygenRatings, oxygenBit);
      co2ScrubberRatings = filter(i, co2ScrubberRatings, co2Bit);
    }
    int oxygenScrubberRating = toInt(oxygenRatings.iterator().next(), numBits);
    int co2ScrubberRating = toInt(co2ScrubberRatings.iterator().next(), numBits);
    return oxygenScrubberRating * co2ScrubberRating;
  }

  private static Set<BitSet> filter(int i, Set<BitSet> ratings, boolean valueToKeep) {
    if (ratings.size() == 1) {
      return ratings;
    }
    return ratings.stream().filter(b -> b.get(i) == valueToKeep).collect(Collectors.toSet());
  }

  /** Manual conversion of an bit set to an int, unsigned with a max number of bits */
  private static int toInt(BitSet b, int numBits) {
    int res = 0;
    for (int i = numBits - 1; i >= 0; i--) {
      if (b.get(i)) {
        res = res + ((Double) Math.pow(2, numBits - 1 - i)).intValue();
      }
    }
    return res;
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
}
