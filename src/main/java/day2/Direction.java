package day2;

import java.util.stream.Stream;

public enum Direction {
  FORWARD,
  DOWN,
  UP;

  public static Direction ofDirection(String d) {
    return Stream.of(values())
        .filter(direction -> direction.name().equalsIgnoreCase(d))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Unknown direction: " + d));
  }
}
