package day8;

import day4.Pair;
import io.vavr.control.Try;
import java.util.List;

public class Day8 {
  public static long countOfOnesFoursSevensAndEights(
      List<Pair<SegmentPattern, List<SevenSegmentNumber>>> patternsAndNumbers) {
    return patternsAndNumbers.stream()
        .map(Pair::getSecond)
        .flatMap(List::stream)
        .map(number -> Try.of(number::actualNumber))
        .filter(Try::isSuccess)
        .map(Try::get)
        .count();
  }

  public static long sumOfDecodedNumbers(
      List<Pair<SegmentPattern, List<SevenSegmentNumber>>> patternsAndNumbers) {
    return patternsAndNumbers.stream()
        .mapToInt(p -> Decoder.decode(p.getFirst(), p.getSecond()))
        .sum();
  }
}
