package day8;

import day4.Pair;
import io.vavr.control.Try;
import java.util.List;

public class Day8 {
  public static long countOfOnesFoursSevensAndEights(
      List<Pair<EncodedSignalPatterns, List<SevenSegmentNumber>>> patternsAndNumbers) {
    return patternsAndNumbers.stream()
        .map(Pair::getSecond)
        .flatMap(List::stream)
        .map(number -> Try.of(number::guessNumber))
        .filter(Try::isSuccess)
        .map(Try::get)
        .count();
  }

  public static long sumOfDecodedNumbers(
      List<Pair<EncodedSignalPatterns, List<SevenSegmentNumber>>> patternsAndNumbers) {
    return patternsAndNumbers.stream()
        .mapToInt(p -> Decoder.decode(p.getFirst(), p.getSecond()))
        .sum();
  }
}
