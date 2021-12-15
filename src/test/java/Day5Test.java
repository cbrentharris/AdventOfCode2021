import static org.assertj.core.api.Assertions.*;

import advent.day5.Coordinate;
import advent.day5.Day5;
import advent.day5.Line;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

public class Day5Test extends AbstractAdventTest {
  @Test
  public void day5Part1Test() {
    assertThat(Day5.overlappingStraightLines(getLines("./day5-test.txt"))).isEqualTo(5);
  }

  @Test
  public void day5Part1() {
    assertThat(Day5.overlappingStraightLines(getLines("./day5.txt"))).isEqualTo(4745);
  }

  @Test
  public void day5Part2Test() {
    assertThat(Day5.overlappingLines(getLines("./day5-test.txt"))).isEqualTo(12);
  }

  @Test
  public void day5Part2() {
    assertThat(Day5.overlappingLines(getLines("./day5.txt"))).isEqualTo(18442);
  }

  private List<Line> getLines(String fileName) {
    return Stream.of(getData(fileName).split("\n"))
        .map(
            rawLine -> {
              String[] parts = rawLine.split(" -> ");
              String[] rawStart = parts[0].split(",");
              String[] rawEnd = parts[1].split(",");
              Coordinate start =
                  new Coordinate(Integer.parseInt(rawStart[0]), Integer.parseInt(rawStart[1]));
              Coordinate end =
                  new Coordinate(Integer.parseInt(rawEnd[0]), Integer.parseInt(rawEnd[1]));
              return new Line(start, end);
            })
        .collect(Collectors.toList());
  }
}
