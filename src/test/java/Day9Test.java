import static org.assertj.core.api.Assertions.*;

import day9.Day9;
import org.junit.jupiter.api.Test;

public class Day9Test extends AbstractAdventTest {
  @Test
  public void day9Part1Test() {
    assertThat(Day9.findValleyRiskPointSum(getGrid("./day9-test.txt"))).isEqualTo(15);
  }

  @Test
  public void day9Part1() {
    assertThat(Day9.findValleyRiskPointSum(getGrid("./day9.txt"))).isEqualTo(522);
  }

  @Test
  public void day9Part2Test() {
    assertThat(Day9.findThreeLargestBasinProduct(getGrid("./day9-test.txt"))).isEqualTo(1134);
  }

  @Test
  public void day9Part2() {
    assertThat(Day9.findThreeLargestBasinProduct(getGrid("./day9.txt"))).isEqualTo(916688);
  }
}
