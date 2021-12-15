import static org.assertj.core.api.Assertions.*;

import day15.Day15;
import org.junit.jupiter.api.Test;

public class Day15Test extends AbstractAdventTest {
  @Test
  public void day15Part1Test() {
    assertThat(Day15.shortestPathSum(getGrid("./day15-test.txt"))).isEqualTo(40);
  }

  @Test
  public void day15Part1() {
    assertThat(Day15.shortestPathSum(getGrid("./day15.txt"))).isEqualTo(487);
  }

  @Test
  public void day15Part2Test() {
    assertThat(Day15.shortestPathSum(expandGrid(getGrid("./day15-test.txt")))).isEqualTo(315);
  }

  @Test
  public void day15Part2() {
    assertThat(Day15.shortestPathSum(expandGrid(getGrid("./day15.txt")))).isEqualTo(2821);
  }

  private int[][] expandGrid(int[][] grid) {
    int[][] largerGrid = new int[grid.length * 5][];
    for (int i = 0; i < 5; i++) {
      for (int row = 0; row < grid.length; row++) {
        int[] newRow = new int[grid[0].length * 5];
        int newRowIndex = row + i * grid.length;
        for (int j = 0; j < 5; j++) {
          for (int col = 0; col < grid[0].length; col++) {
            int val = grid[row][col];
            int offset = i + j;
            int newVal = val + offset;
            int newCol = col + j * grid[0].length;
            newRow[newCol] = newVal > 9 ? newVal % 9 : newVal;
          }
        }
        largerGrid[newRowIndex] = newRow;
      }
    }
    return largerGrid;
  }
}
