import static org.assertj.core.api.Assertions.*;

import advent.day13.Day13;
import advent.day13.FoldInstruction;
import io.vavr.Tuple2;
import io.vavr.collection.List;
import org.junit.jupiter.api.Test;

public class Day13Test extends AbstractAdventTest {
  @Test
  public void day13Part1Test() {
    assertThat(Day13.visibleDots(1, getGridAndInstructions("./day13-test.txt"))).isEqualTo(17);
    assertThat(Day13.visibleDots(2, getGridAndInstructions("./day13-test.txt"))).isEqualTo(16);
  }

  @Test
  public void day13Part1() {
    assertThat(Day13.visibleDots(1, getGridAndInstructions("./day13.txt"))).isEqualTo(724);
  }

  @Test
  public void day13Part2() {
    Day13.printInstructions(getGridAndInstructions("./day13.txt"));
  }

  private Tuple2<Character[][], List<FoldInstruction>> getGridAndInstructions(String fileName) {
    List<String> lines = List.of(getData(fileName).split("\n"));
    List<String> gridLines = lines.takeUntil(String::isEmpty);
    List<FoldInstruction> instructions =
        lines.reverse().takeUntil(String::isEmpty).map(FoldInstruction::new).reverse();
    // this is zero based
    int rowCount =
        gridLines.map(s -> s.split(",")[1]).map(Integer::parseInt).max().getOrElse(0) + 1;
    int colCount =
        gridLines.map(s -> s.split(",")[0]).map(Integer::parseInt).max().getOrElse(0) + 1;
    Character[][] grid = new Character[rowCount][colCount];
    gridLines
        .map(s -> s.split(","))
        .forEach(
            parts -> {
              int colIndex = Integer.parseInt(parts[0]);
              int rowIndex = Integer.parseInt(parts[1]);
              grid[rowIndex][colIndex] = '#';
            });
    return new Tuple2<>(grid, instructions);
  }
}
