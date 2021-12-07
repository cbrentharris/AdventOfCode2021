package day7;

import java.util.List;
import java.util.stream.IntStream;

public class Day7 {
  public static int minimumFuelCostToAlign(List<CrabSubmarine> crabSubmarines) {
    return positionStream(crabSubmarines)
        .map(
            endPos ->
                positionStream(crabSubmarines).map(startPos -> getFuelCost(startPos, endPos)).sum())
        .min()
        .getAsInt();
  }

  public static int realFuelCost(List<CrabSubmarine> crabSubmarines) {
    int minPos = positionStream(crabSubmarines).min().getAsInt();
    int maxPos = positionStream(crabSubmarines).max().getAsInt();
    return IntStream.range(minPos, maxPos + 1)
        .map(
            startPos ->
                positionStream(crabSubmarines)
                    .map(endPos -> getRealFuelCost(startPos, endPos))
                    .sum())
        .min()
        .getAsInt();
  }

  private static int getFuelCost(int startPos, int endPos) {
    return Math.abs(startPos - endPos);
  }

  private static int getRealFuelCost(int startPos, int endPos) {
    int n = getFuelCost(startPos, endPos);
    return (n * (n + 1)) / 2;
  }

  private static IntStream positionStream(List<CrabSubmarine> crabSubmarines) {
    return crabSubmarines.stream().mapToInt(CrabSubmarine::getXPosition);
  }
}
