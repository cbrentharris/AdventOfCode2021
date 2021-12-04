package day4;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class Pair<V1, V2> {
  private V1 first;
  private V2 second;
}
