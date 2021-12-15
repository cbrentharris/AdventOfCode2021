import static org.assertj.core.api.Assertions.*;

import advent.day2.Day2;
import advent.day2.Direction;
import advent.day2.DirectionAndMagnitude;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

public class Day2Test extends AbstractAdventTest {

  @Test
  public void testDay2Part1() {
    assertThat(Day2.finalDepth(getDirectionsAndMagnitudes())).isEqualTo(1507611);
  }

  @Test
  public void testDay2Part2() {
    assertThat(Day2.finalDepthWithAim(getDirectionsAndMagnitudes())).isEqualTo(1880593125);
  }

  private List<DirectionAndMagnitude> getDirectionsAndMagnitudes() {
    return Stream.of(getData("./day2.txt").split("\n"))
        .map(
            s -> {
              String[] parts = s.split(" ");
              Direction d = Direction.ofDirection(parts[0]);
              int m = Integer.parseInt(parts[1]);
              return new DirectionAndMagnitude(d, m);
            })
        .collect(Collectors.toList());
  }
}
