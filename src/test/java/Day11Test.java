import static org.assertj.core.api.Assertions.*;

import day11.Day11;
import org.junit.jupiter.api.Test;

public class Day11Test extends AbstractAdventTest {
  @Test
  public void day11Part1Test() {
    assertThat(Day11.numFlashes(100, getGrid("./day11-test.txt"))).isEqualTo(1656);
  }

  @Test
  public void day11Part1() {
    assertThat(Day11.numFlashes(100, getGrid("./day11.txt"))).isEqualTo(1652);
  }

  @Test
  public void day11Part2Test() {
    assertThat(Day11.stepWhereAllFlash(getGrid("./day11-test.txt"))).isEqualTo(195);
  }

  @Test
  public void day11Part2() {
    assertThat(Day11.stepWhereAllFlash(getGrid("./day11.txt"))).isEqualTo(220);
  }
}
