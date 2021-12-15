package advent.day5;

import lombok.Data;

@Data
public class Coordinate implements Comparable<Coordinate> {
  private final int x;
  private final int y;

  @Override
  public int compareTo(Coordinate o) {
    if (x == o.getX()) {
      return y - o.getY();
    }
    return x - o.getX();
  }
}
