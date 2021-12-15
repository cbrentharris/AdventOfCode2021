package advent.day4;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class Pair<V1, V2> {
  private final V1 first;
  private final V2 second;
}
