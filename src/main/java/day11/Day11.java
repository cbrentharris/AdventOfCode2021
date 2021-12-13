package day11;

import io.vavr.collection.List;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.Value;

public class Day11 {
  @Value
  @RequiredArgsConstructor
  private static class Point {
    int row;
    int col;
  }

  public static int numFlashes(int steps, int[][] grid) {
    int currentStep = 0;
    int totalFlashes = 0;
    while (currentStep < steps) {
      totalFlashes += advanceStep(grid);
      currentStep++;
    }
    return totalFlashes;
  }

  public static int stepWhereAllFlash(int[][] grid) {
    int flashesFromStep = 0;
    int currentStep = 0;
    while (flashesFromStep != grid.length * grid[0].length) {
      flashesFromStep = advanceStep(grid);
      currentStep++;
    }
    return currentStep;
  }

  private static int advanceStep(int[][] grid) {
    Set<Point> alreadyFlashed = new HashSet<>();
    Queue<Point> flashesToProcess = new ArrayDeque<>();
    for (int row = 0; row < grid.length; row++) {
      for (int col = 0; col < grid[row].length; col++) {
        int val = grid[row][col];
        if (val == 9) {
          // time to flash
          flashesToProcess.add(new Point(row, col));
          grid[row][col] = 0;
        } else {
          grid[row][col] = ++val;
        }
      }
    }

    while (!flashesToProcess.isEmpty()) {
      Point p = flashesToProcess.poll();
      findAdjacencies(p.getRow(), p.getCol(), grid)
          .filter(a -> !alreadyFlashed.contains(a))
          .forEach(
              a -> {
                int row = a.getRow();
                int col = a.getCol();
                int val = grid[row][col];
                if (val == 9) {
                  // time to flash
                  flashesToProcess.add(new Point(row, col));
                  grid[row][col] = 0;
                } else if (grid[row][col] != 0) {
                  grid[row][col] = ++val;
                }
              });
      alreadyFlashed.add(p);
    }
    return alreadyFlashed.size();
  }

  private static List<Point> findAdjacencies(int row, int col, int[][] grid) {
    List<Integer> possibleRows =
        List.of(row - 1, row, row + 1).filter(r -> r < grid.length).filter(r -> r >= 0);
    List<Integer> possibleCols =
        List.of(col - 1, col, col + 1).filter(c -> c < grid[0].length).filter(c -> c >= 0);
    return possibleRows
        .flatMap(r -> possibleCols.map(c -> new Point(r, c)))
        .filter(pair -> !(pair.getRow() == row && pair.getCol() == col));
  }
}
