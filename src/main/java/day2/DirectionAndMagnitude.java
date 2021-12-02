package day2;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class DirectionAndMagnitude {
  private final Direction direction;
  private final int magnitude;
}
