package day15;

import day11.Day11;
import io.vavr.collection.List;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;

public class Day15 {

  @Data
  @AllArgsConstructor
  private static class PointAndCost {
    Day11.Point p;
    int cost;
  }

  public static int shortestPathSum(int[][] grid) {
    return dijkstras(
        grid, new Day11.Point(0, 0), new Day11.Point(grid.length - 1, grid[0].length - 1));
  }

  private static int dijkstras(int[][] grid, Day11.Point start, Day11.Point end) {
    Map<Day11.Point, Integer> distance = new HashMap<>();
    PriorityQueue<PointAndCost> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a.cost));
    distance.put(start, 0);
    pq.offer(new PointAndCost(start, 0));
    for (int row = 0; row < grid.length; row++) {
      for (int col = 0; col < grid[0].length; col++) {
        Day11.Point p = new Day11.Point(row, col);
        if (p.equals(start)) {
          continue;
        }
        distance.put(p, Integer.MAX_VALUE);
        PointAndCost pointAndCost = new PointAndCost(p, Integer.MAX_VALUE);
        pq.offer(pointAndCost);
      }
    }

    Set<Day11.Point> processed = new HashSet<>();
    while (!pq.isEmpty()) {
      PointAndCost pointAndCost = pq.poll();
      if (processed.contains(pointAndCost.p)) {
        continue;
      }
      processed.add(pointAndCost.p);
      List<Day11.Point> adjacencies =
          findAdjacencies(pointAndCost.getP().getRow(), pointAndCost.getP().getCol(), grid);

      adjacencies.forEach(
          a -> {
            int alternateCost = distance.get(pointAndCost.getP()) + grid[a.getRow()][a.getCol()];
            if (alternateCost < distance.get(a)) {
              distance.put(a, alternateCost);
              pq.offer(new PointAndCost(a, alternateCost));
            }
          });
    }

    return distance.get(end);
  }

  private static List<Day11.Point> findAdjacencies(int row, int col, int[][] grid) {
    List<Integer> possibleRows =
        List.of(row - 1, row, row + 1).filter(r -> r < grid.length).filter(r -> r >= 0);
    List<Integer> possibleCols =
        List.of(col - 1, col, col + 1).filter(c -> c < grid[0].length).filter(c -> c >= 0);
    return possibleRows
        .flatMap(r -> possibleCols.map(c -> new Day11.Point(r, c)))
        .filter(pair -> !(pair.getRow() == row && pair.getCol() == col))
        .filter(p -> isNotDiagonal(p, row, col));
  }

  private static boolean isNotDiagonal(Day11.Point p, int row, int col) {
    return p.getRow() == row || p.getCol() == col;
  }
}
