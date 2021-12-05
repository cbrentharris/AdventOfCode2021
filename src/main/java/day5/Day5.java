package day5;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day5 {
  public static long overlappingStraightLines(List<Line> lines) {
    return lines.stream()
        .filter(Line::isStraightLine)
        .flatMap(l -> l.getCrossedCoordinates().stream())
        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
        .entrySet()
        .stream()
        .filter(e -> e.getValue() > 1)
        .count();
  }

  public static long overlappingLines(List<Line> lines) {
    return lines.stream()
        .flatMap(l -> l.getCrossedCoordinates().stream())
        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
        .entrySet()
        .stream()
        .filter(e -> e.getValue() > 1)
        .count();
  }
}
