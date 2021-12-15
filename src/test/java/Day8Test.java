import static org.assertj.core.api.Assertions.*;

import advent.day4.Pair;
import advent.day8.Day8;
import advent.day8.EncodedSignalPatterns;
import advent.day8.SevenSegmentNumber;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

public class Day8Test extends AbstractAdventTest {
  @Test
  public void day8Part1Test() {
    assertThat(Day8.countOfOnesFoursSevensAndEights(getPatternsAndNumbers("./day8-test.txt")))
        .isEqualTo(26);
  }

  @Test
  public void day8Part1() {
    assertThat(Day8.countOfOnesFoursSevensAndEights(getPatternsAndNumbers("./day8.txt")))
        .isEqualTo(239);
  }

  @Test
  public void day8Part2Test() {
    assertThat(Day8.sumOfDecodedNumbers(getPatternsAndNumbers("./day8-test.txt"))).isEqualTo(61229);
  }

  @Test
  public void day8Part2() {
    assertThat(Day8.sumOfDecodedNumbers(getPatternsAndNumbers("./day8.txt"))).isEqualTo(946346);
  }

  private List<Pair<EncodedSignalPatterns, List<SevenSegmentNumber>>> getPatternsAndNumbers(
      String fileName) {
    return Stream.of(getData(fileName).split("\n"))
        .map(
            line -> {
              String[] parts = line.split(" \\| ");
              EncodedSignalPatterns pattern = new EncodedSignalPatterns(parts[0]);
              List<SevenSegmentNumber> numbers =
                  Stream.of(parts[1].split(" "))
                      .map(SevenSegmentNumber::new)
                      .collect(Collectors.toList());
              return new Pair<>(pattern, numbers);
            })
        .collect(Collectors.toList());
  }
}
