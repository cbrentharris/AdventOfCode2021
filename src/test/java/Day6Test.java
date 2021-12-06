import static org.assertj.core.api.Assertions.*;

import day6.Day6;
import day6.Fish;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class Day6Test extends AbstractAdventTest {
  @ParameterizedTest
  @MethodSource("testInputCases")
  public void day6Part1Test2(int numDays, int expectedFish) {
    assertThat(Day6.numFish(loadFish("./day6-test.txt"), numDays)).isEqualTo(expectedFish);
  }

  @Test
  public void day6Part1Test() {
    assertThat(Day6.numFish(loadFish("./day6-test.txt"), 80)).isEqualTo(5934);
  }

  @Test
  public void day6Part1() {
    assertThat(Day6.numFish(loadFish("./day6.txt"), 80)).isEqualTo(360761);
  }

  @Test
  public void day6Part2Test() {
    assertThat(Day6.numFish(loadFish("./day6-test.txt"), 256)).isEqualTo(26984457539L);
  }

  @Test
  public void day6Part2() {
    assertThat(Day6.numFish(loadFish("./day6.txt"), 256)).isEqualTo(1632779838045L);
  }

  public List<Fish> loadFish(String fileName) {
    return Stream.of(getData(fileName).split(","))
        .map(Integer::parseInt)
        .map(Fish::new)
        .collect(Collectors.toList());
  }

  private static Stream<Arguments> testInputCases() {
    return Stream.of(
        Arguments.of(1, 5),
        Arguments.of(2, 6),
        Arguments.of(3, 7),
        Arguments.of(4, 9),
        Arguments.of(5, 10),
        Arguments.of(6, 10),
        Arguments.of(7, 10),
        Arguments.of(8, 10),
        Arguments.of(9, 11),
        Arguments.of(10, 12),
        Arguments.of(11, 15));
  }
}
