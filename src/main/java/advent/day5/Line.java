package advent.day5;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class Line {
  @Data
  private static class Slope {
    private int xPart;
    private int yPart;

    public Slope(Coordinate start, Coordinate end) {
      int rawXPart = end.getX() - start.getX();
      int rawYPart = end.getY() - start.getY();

      int gcd = gcd(rawYPart, rawYPart);
      if (gcd == 0) {
        gcd = 1;
      }

      xPart = rawXPart / gcd;
      yPart = rawYPart / gcd;

      if (xPart == 0 && yPart != 0) {
        yPart = (yPart / gcd(yPart, yPart));
      } else if (xPart != 0 && yPart == 0) {
        xPart = (xPart / gcd(xPart, xPart));
      }
    }

    private static int gcd(int a, int b) {
      if (a == 0) {
        return Math.abs(b);
      }
      return gcd(b % a, a);
    }

    public Coordinate advance(Coordinate coordinate) {
      return new Coordinate(coordinate.getX() + xPart, coordinate.getY() + yPart);
    }
  }

  private final Coordinate start;
  private final Coordinate end;

  public List<Coordinate> getCrossedCoordinates() {
    List<Coordinate> output = new ArrayList<>();
    Coordinate current = new Coordinate(start.getX(), start.getY());
    Slope slope = new Slope(start, end);
    output.add(current);
    while (!current.equals(end)) {
      current = slope.advance(current);
      output.add(current);
    }
    return output;
  }

  public boolean isStraightLine() {
    return start.getX() == end.getX() || start.getY() == end.getY();
  }
}
