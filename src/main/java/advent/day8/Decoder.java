package advent.day8;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Decoder {
  public static int decode(EncodedSignalPatterns pattern, List<SevenSegmentNumber> numbers) {
    Map<String, String> decodings = pattern.decodeSignals();

    return Integer.parseInt(
        numbers.stream()
            .map(SevenSegmentNumber::getEncodedInput)
            .map(EncodedSignalPatterns::sort)
            .map(decodings::get)
            .collect(Collectors.joining("")));
  }
}
