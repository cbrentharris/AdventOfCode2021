package day14;

import io.vavr.collection.List;
import io.vavr.collection.Map;
import java.util.stream.IntStream;

public class Day14 {
  public static long polymerInsertionMostCommonLeastCommon(
      PolymerChain startChain, List<InsertionRule> rules, int applicationCount) {
    IntStream.range(0, applicationCount).forEach(i -> startChain.apply(rules));
    Map<Character, Long> frequencies = startChain.getFrequencyCount();
    return (frequencies.maxBy(t -> t._2).map(t -> t._2).get()
        - frequencies.minBy(t -> t._2).map(t -> t._2).get());
  }
}
