package advent.day14;

import io.vavr.Tuple2;
import lombok.Data;

@Data
public class InsertionRule {
  private final Tuple2<Character, Character> polymerPair;
  private final Character output;

  public InsertionRule(String input) {
    String[] parts = input.split(" -> ");
    polymerPair = new Tuple2<>(parts[0].charAt(0), parts[0].charAt(1));
    output = parts[1].charAt(0);
  }
}
