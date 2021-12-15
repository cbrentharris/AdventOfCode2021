package advent.day15;

import advent.common.Point;
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
    Point point;
    int cost;
  }

  public static int shortestPathSum(int[][] grid) {
    return dijkstras(grid, new Point(0, 0), new Point(grid.length - 1, grid[0].length - 1));
  }

  private static int dijkstras(int[][] grid, Point start, Point end) {
    Map<Point, Integer> distance = new HashMap<>();
    PriorityQueue<PointAndCost> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a.cost));
    distance.put(start, 0);
    pq.offer(new PointAndCost(start, 0));
    for (int row = 0; row < grid.length; row++) {
      for (int col = 0; col < grid[0].length; col++) {
        Point p = new Point(row, col);
        if (p.equals(start)) {
          continue;
        }
        distance.put(p, Integer.MAX_VALUE);
        PointAndCost pointAndCost = new PointAndCost(p, Integer.MAX_VALUE);
        pq.offer(pointAndCost);
      }
    }

    Set<Point> processed = new HashSet<>();
    while (!pq.isEmpty()) {
      PointAndCost pointAndCost = pq.poll();
      if (processed.contains(pointAndCost.getPoint())) {
        continue;
      }
      processed.add(pointAndCost.getPoint());
      List<Point> adjacencies = pointAndCost.getPoint().findAdjacencies(grid);

      adjacencies.forEach(
          a -> {
            int alternateCost =
                distance.get(pointAndCost.getPoint()) + grid[a.getRow()][a.getCol()];
            if (alternateCost < distance.get(a)) {
              distance.put(a, alternateCost);
              pq.offer(new PointAndCost(a, alternateCost));
            }
          });
    }

    return distance.get(end);
  }
}
