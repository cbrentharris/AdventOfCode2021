import static org.assertj.core.api.Assertions.assertThat;

import day12.CaveGraph;
import day12.Day12;
import io.vavr.collection.List;
import org.junit.jupiter.api.Test;

public class Day12Test extends AbstractAdventTest {
  @Test
  public void day12Part1Test() {
    assertThat(Day12.numPaths(getGraph("day12-test.txt"))).isEqualTo(10);
  }

  @Test
  public void day12Part1() {
    assertThat(Day12.numPaths(getGraph("day12.txt"))).isEqualTo(4754);
  }

  @Test
  public void day12Part2Test() {
    assertThat(Day12.numPathsPart2(getGraph("day12-test.txt"))).isEqualTo(36);
  }

  @Test
  public void day12Part2() {
    assertThat(Day12.numPathsPart2(getGraph("day12.txt"))).isEqualTo(143562);
  }

  private CaveGraph getGraph(String fileName) {
    return new CaveGraph(List.of(getData(fileName).split("\n")));
  }
}
