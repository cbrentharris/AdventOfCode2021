package day9;

import io.vavr.Tuple2;
import io.vavr.collection.List;
import io.vavr.collection.Stream;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

public class Day9 {
  public static int findValleyRiskPointSum(int[][] grid) {
    return Stream.range(0, grid.length)
        .map(
            rowIndex ->
                Stream.range(0, grid[rowIndex].length)
                    .filter(colIndex -> isValley(rowIndex, colIndex, grid))
                    .map(colIndex -> riskScore(grid[rowIndex][colIndex]))
                    .sum())
        .sum()
        .intValue();
  }

  public static int findThreeLargestBasinProduct(int[][] grid) {
    return Stream.range(0, grid.length)
        .flatMap(
            rowIndex ->
                Stream.range(0, grid[rowIndex].length)
                    .filter(colIndex -> isValley(rowIndex, colIndex, grid))
                    .map(colIndex -> materializeBasin(rowIndex, colIndex, grid)))
        .map(List::size)
        .sortBy(size -> -size)
        .take(3)
        .foldLeft(1, (a, b) -> a * b);
  }

  private static List<Tuple2<Integer, Integer>> materializeBasin(
      Integer rowIndex, Integer colIndex, int[][] grid) {
    Queue<Tuple2<Integer, Integer>> pointQueue = new ArrayDeque<>();
    Set<Tuple2<Integer, Integer>> basin = new HashSet<>();
    pointQueue.add(new Tuple2<>(rowIndex, colIndex));
    basin.add(new Tuple2<>(rowIndex, colIndex));
    while (!pointQueue.isEmpty()) {
      Tuple2<Integer, Integer> point = pointQueue.poll();
      findAdjacencies(point._1(), point._2(), grid)
          .filter(p -> grid[p._1()][p._2()] != 9)
          .filter(p -> grid[p._1()][p._2()] > grid[point._1()][point._2()])
          .filter(p -> !basin.contains(p))
          .forEach(
              p -> {
                basin.add(p);
                pointQueue.add(p);
              });
    }
    return List.ofAll(basin);
  }

  private static int riskScore(int i) {
    return i + 1;
  }

  private static boolean isValley(int row, int col, int[][] grid) {
    List<Tuple2<Integer, Integer>> adjacencies = findAdjacencies(row, col, grid);
    return adjacencies.forAll(a -> grid[row][col] < grid[a._1()][a._2()]);
  }

  private static List<Tuple2<Integer, Integer>> findAdjacencies(int row, int col, int[][] grid) {
    List<Integer> possibleRows =
        List.of(row - 1, row, row + 1).filter(r -> r < grid.length).filter(r -> r >= 0);
    List<Integer> possibleCols =
        List.of(col - 1, col, col + 1).filter(c -> c < grid[0].length).filter(c -> c >= 0);
    return possibleRows
        .flatMap(r -> possibleCols.map(c -> new Tuple2<>(r, c)))
        .filter(pair -> !(pair._1() == row && pair._2() == col))
        .filter(p -> isNotDiagonal(p, row, col));
  }

  private static boolean isNotDiagonal(Tuple2<Integer, Integer> p, int row, int col) {
    return p._1() == row || p._2() == col;
  }
}
