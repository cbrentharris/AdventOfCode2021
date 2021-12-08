package day8;

import static com.google.common.collect.Lists.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Decoder {
  public static int decode(EncodedSignalPatterns pattern, List<SevenSegmentNumber> numbers) {
    Map<String, String> decodings = pattern.decodeSignals();

    return Integer.parseInt(
        numbers.stream()
            .map(SevenSegmentNumber::getEncodedInput)
            .map(decodings::get)
            .collect(Collectors.joining("")));
  }
}
