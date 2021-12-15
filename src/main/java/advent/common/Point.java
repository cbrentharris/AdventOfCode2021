package advent.common;

import io.vavr.collection.List;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
public class Point {
  int row;
  int col;

  public List<Point> findAdjacencies(int[][] grid) {
    return findDiagonalAdjacencies(grid).filter(this::isNotDiagonal);
  }

  public List<Point> findDiagonalAdjacencies(int[][] grid) {
    List<Integer> possibleRows =
        List.of(row - 1, row, row + 1).filter(r -> r < grid.length).filter(r -> r >= 0);
    List<Integer> possibleCols =
        List.of(col - 1, col, col + 1).filter(c -> c < grid[0].length).filter(c -> c >= 0);
    return possibleRows
        .flatMap(r -> possibleCols.map(c -> new Point(r, c)))
        .filter(pair -> !(pair.getRow() == row && pair.getCol() == col));
  }

  private boolean isNotDiagonal(Point p) {
    return p.getRow() == row || p.getCol() == col;
  }
}
