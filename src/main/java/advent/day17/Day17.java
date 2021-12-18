package advent.day17;

import static java.util.Optional.*;

import advent.common.Pair;
import advent.common.Point;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day17 {
  public static int getHighestYPointReachingTargetArea(Pair<Integer, Integer> yWidth) {
    return IntStream.range(0, Math.abs(yWidth.getFirst()) + 1)
        .filter(yV -> canReachBoxY(yWidth.getFirst(), yWidth.getSecond(), yV, 0, 0))
        .map(Day17::maxHeight)
        .max()
        .getAsInt();
  }

  public static int countOfVelocitiesThatReachTarget(
      Pair<Integer, Integer> xWidth, Pair<Integer, Integer> yWidth) {

    List<Integer> viableYs =
        IntStream.range(Math.min(0, yWidth.getFirst()), Math.abs(yWidth.getFirst()) + 1)
            .filter(v -> canReachBoxY(yWidth.getFirst(), yWidth.getSecond(), v, 0, 0))
            .boxed()
            .collect(Collectors.toList());

    List<Integer> viableXs =
        IntStream.range(0, xWidth.getSecond() + 1)
            .filter(v -> canReachBoxX(xWidth.getFirst(), xWidth.getSecond(), v, 0))
            .boxed()
            .collect(Collectors.toList());

    Map<Integer, List<Pair<Integer, Integer>>> stepToVelocitiesY =
        viableYs.stream()
            .flatMap(
                y ->
                    stepsReachingBox(yWidth.getFirst(), yWidth.getSecond(), y).stream()
                        .map(s -> new Pair<>(s, y)))
            .collect(Collectors.groupingBy(Pair::getFirst));

    Map<Integer, java.util.List<Pair<Integer, Integer>>> stepToVelocitiesX =
        viableXs.stream()
            .flatMap(
                x ->
                    remainingVelocitiesReachingBoxX(xWidth.getFirst(), xWidth.getSecond(), x, 0)
                        .stream()
                        .map(v -> new Pair<>(Math.abs(x - v), x)))
            .collect(Collectors.groupingBy(Pair::getFirst));

    Map<Integer, Integer> velocitesToMaxes =
        viableXs.stream().collect(Collectors.toMap(x -> x, Day17::maxDistance));

    java.util.List<Point> b =
        stepToVelocitiesY.entrySet().stream()
            .flatMap(
                e -> {
                  int step = e.getKey();
                  List<Integer> ys =
                      e.getValue().stream().map(Pair::getSecond).collect(Collectors.toList());
                  Set<Integer> xsAtStep =
                      ofNullable(stepToVelocitiesX.get(step))
                          .orElseGet(Collections::emptyList)
                          .stream()
                          .map(Pair::getSecond)
                          .collect(Collectors.toSet());
                  Set<Integer> xsStopped =
                      viableXs.stream()
                          .filter(xi -> xi < step)
                          .filter(xi -> velocitesToMaxes.get(xi) <= xWidth.getSecond())
                          .collect(Collectors.toSet());
                  return Lists.cartesianProduct(
                      new ArrayList<>(Sets.union(xsAtStep, xsStopped)), ys)
                      .stream();
                })
            .map(l -> new Point(l.get(0), l.get(1)))
            .distinct()
            .collect(Collectors.toList());

    return b.size();
  }

  private static boolean canReachBoxX(int xStart, int xEnd, int xV, int x) {
    if (x >= xStart && x <= xEnd) {
      return true;
    }
    if (xV == 0) {
      return false;
    }
    if (xV > 0) {
      return canReachBoxX(xStart, xEnd, xV - 1, x + xV);
    } else {
      return canReachBoxX(xStart, xEnd, xV + 1, x - xV);
    }
  }

  private static Set<Integer> remainingVelocitiesReachingBoxX(int xStart, int xEnd, int xV, int x) {
    Set<Integer> velocities = new HashSet<>();
    if (x >= xStart && x <= xEnd) {
      velocities.add(xV);
    }
    if (xV == 0) {
      return velocities;
    }
    if (xV > 0) {
      return Sets.union(velocities, remainingVelocitiesReachingBoxX(xStart, xEnd, xV - 1, x + xV));
    } else {
      return Sets.union(velocities, remainingVelocitiesReachingBoxX(xStart, xEnd, xV + 1, x - xV));
    }
  }

  private static int maxHeight(int yV) {
    int currentYV = yV;
    int gravity = 1;
    while (yV > gravity) {
      currentYV = currentYV - gravity + yV;
      gravity++;
    }
    return currentYV;
  }

  private static int maxDistance(int x) {
    return ((x + 1) * x) / 2;
  }

  private static boolean canReachBoxY(int yEnd, int yStart, int yV, int gravity, int y) {
    if (withinBounds(yStart, yEnd, y)) {
      return true;
    }

    if (!(y >= gravity || y >= yEnd)) {
      return false;
    }

    return canReachBoxY(yEnd, yStart, yV, gravity + 1, y - gravity + yV);
  }

  private static Set<Integer> stepsReachingBox(int yEnd, int yStart, int yV) {
    int gravity = 1;
    int currentYV = yV;
    if (yV < yEnd && yV <= 0) {
      return Collections.emptySet();
    }
    Set<Integer> steps = new HashSet<>();
    while (currentYV >= gravity || currentYV >= yEnd) {
      if (withinBounds(yStart, yEnd, currentYV)) {
        steps.add(gravity);
      }
      currentYV = currentYV - gravity + yV;
      gravity++;
    }

    return steps;
  }

  private static boolean withinBounds(int yStart, int yEnd, int yV) {
    return yEnd <= yV && yStart >= yV;
  }
}
