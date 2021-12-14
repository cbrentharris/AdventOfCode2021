package day14;

import io.vavr.Tuple2;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import io.vavr.control.Option;

public class InsertionRules {
  Map<Tuple2<Character, Character>, Character> rules;

  public InsertionRules(List<InsertionRule> input) {
    rules = input.toMap(InsertionRule::getPolymerPair, InsertionRule::getOutput);
  }

  public Option<Character> apply(Tuple2<Character, Character> input) {
    return rules.get(input);
  }
}
