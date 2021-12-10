import static org.assertj.core.api.Assertions.*;

import day10.Day10;
import day10.NavigationSubsystem;
import io.vavr.collection.List;
import org.junit.jupiter.api.Test;

public class Day10Test extends AbstractAdventTest {
  @Test
  public void day10Part1Test() {
    assertThat(Day10.illegalSyntaxSum(getNavigationSubsystemData("./day10-test.txt")))
        .isEqualTo(26397);
  }

  @Test
  public void day10Part1() {
    assertThat(Day10.illegalSyntaxSum(getNavigationSubsystemData("./day10.txt"))).isEqualTo(369105);
  }

  @Test
  public void day10Part2Test() {
    assertThat(Day10.completedSyntaxMiddleScore(getNavigationSubsystemData("./day10-test.txt")))
        .isEqualTo(288957);
  }

  @Test
  public void day10Part2() {
    assertThat(Day10.completedSyntaxMiddleScore(getNavigationSubsystemData("./day10.txt")))
        .isEqualTo(3999363569L);
  }

  private List<NavigationSubsystem> getNavigationSubsystemData(String fileName) {
    return List.of(getData(fileName).split("\n")).map(NavigationSubsystem::new);
  }
}
