package advent.day6;

import java.util.List;

public class Day6 {
  public static long numFish(List<Fish> initialFish, int daysToReproduce) {
    return initialFish.stream().mapToLong(f -> f.descendants(daysToReproduce) + 1).sum();
  }
}
