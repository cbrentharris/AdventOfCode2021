import static org.assertj.core.api.Assertions.*;

import day9.Day9;
import io.vavr.collection.List;
import org.junit.jupiter.api.Test;

public class Day9Test extends AbstractAdventTest {
  @Test
  public void day9Part1Test() {
    assertThat(Day9.findValleyRiskPointSum(getRiskGrid("./day9-test.txt"))).isEqualTo(15);
  }

  @Test
  public void day9Part1() {
    assertThat(Day9.findValleyRiskPointSum(getRiskGrid("./day9.txt"))).isEqualTo(522);
  }

  @Test
  public void day9Part2Test() {
    assertThat(Day9.findThreeLargestBasinProduct(getRiskGrid("./day9-test.txt"))).isEqualTo(1134);
  }

  @Test
  public void day9Part2() {
    assertThat(Day9.findThreeLargestBasinProduct(getRiskGrid("./day9.txt"))).isEqualTo(916688);
  }

  public int[][] getRiskGrid(String fileName) {
    String[] lines = getData(fileName).split("\n");
    int columns = lines[0].length();
    int[][] grid = new int[lines.length][columns];
    List.of(lines)
        .zipWithIndex()
        .forEach(
            lineAndRow ->
                List.of(lineAndRow._1().split(""))
                    .map(Integer::parseInt)
                    .zipWithIndex()
                    .forEach(valAndCol -> grid[lineAndRow._2()][valAndCol._2()] = valAndCol._1()));
    return grid;
  }
}
