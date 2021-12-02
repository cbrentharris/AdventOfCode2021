import static org.assertj.core.api.Assertions.*;

import day1.Day1;
import day1.Depth;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day1Test extends AbstractAdventTest {

  @Test
  public void testDay1Part1() {
    Assertions.assertThat(Day1.findInstancesWhenDepthHasPositiveChange(getDepths()))
        .isEqualTo(1532);
  }

  @Test
  public void testDay1Part2() {
    assertThat(Day1.findInstancesWhenDepthWindowHasPositiveChange(getDepths())).isEqualTo(1571);
  }

  private List<Depth> getDepths() {
    return Stream.of(getData("./day1.txt").split("\n"))
        .map(Integer::parseInt)
        .map(Depth::new)
        .collect(Collectors.toList());
  }
}
