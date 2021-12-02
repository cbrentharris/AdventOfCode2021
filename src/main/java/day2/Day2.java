package day2;

import java.util.List;

public class Day2 {
  public static int finalDepth(List<DirectionAndMagnitude> input) {
    int x = 0;
    int z = 0;
    for (DirectionAndMagnitude d : input) {
      switch (d.getDirection()) {
        case UP:
          z = z - d.getMagnitude();
          break;
        case DOWN:
          z = z + d.getMagnitude();
          break;
        case FORWARD:
          x = x + d.getMagnitude();
          break;
        default:
          throw new IllegalStateException("Invalid direction: " + d.getDirection());
      }
    }
    return x * z;
  }

  public static int finalDepthWithAim(List<DirectionAndMagnitude> input) {
    int x = 0;
    int z = 0;
    int aim = 0;
    for (DirectionAndMagnitude d : input) {
      switch (d.getDirection()) {
        case UP:
          aim = aim - d.getMagnitude();
          break;
        case DOWN:
          aim = aim + d.getMagnitude();
          break;
        case FORWARD:
          x = x + d.getMagnitude();
          z = z + (d.getMagnitude() * aim);
          break;
        default:
          throw new IllegalStateException("Invalid direction: " + d.getDirection());
      }
    }
    return x * z;
  }
}
