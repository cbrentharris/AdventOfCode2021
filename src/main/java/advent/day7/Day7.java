package advent.day7;

import java.util.List;
import java.util.stream.IntStream;

public class Day7 {
  public static int minimumFuelCostToAlign(List<CrabSubmarine> crabSubmarines) {
    int median =
        positionStream(crabSubmarines)
            .sorted()
            .skip(crabSubmarines.size() / 2)
            .findFirst()
            .getAsInt();
    return positionStream(crabSubmarines).map(startPos -> getFuelCost(startPos, median)).sum();
  }

  public static int realFuelCost(List<CrabSubmarine> crabSubmarines) {
    double avg = positionStream(crabSubmarines).average().getAsDouble();
    int highEnd = (int) Math.ceil(avg);
    int lowEnd = (int) Math.floor(avg);
    int lowSum =
        positionStream(crabSubmarines).map(startPos -> getRealFuelCost(startPos, lowEnd)).sum();
    int highSum =
        positionStream(crabSubmarines).map(startPos -> getRealFuelCost(startPos, highEnd)).sum();
    return Math.min(lowSum, highSum);
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
