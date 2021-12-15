package advent.day14;

import io.vavr.Tuple2;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import io.vavr.collection.Vector;
import io.vavr.control.Option;
import lombok.Data;
import org.apache.commons.lang3.ArrayUtils;

@Data
public class PolymerChain {
  Map<Tuple2<Character, Character>, Long> pairCounts;

  public PolymerChain(String input) {
    Vector<Character> polymers = Vector.of(ArrayUtils.toObject(input.toCharArray()));
    pairCounts =
        polymers
            .dropRight(1)
            .zipWithIndex()
            .map(
                polymerAndIndex -> {
                  int insertionIndex = polymerAndIndex._2 + 1;
                  Character current = polymerAndIndex._1;
                  Character next = polymers.get(insertionIndex);
                  return new Tuple2<>(current, next);
                })
            .groupBy(t -> t)
            .mapValues(l -> (long) l.size());
  }

  public void apply(List<InsertionRule> ruleSet) {

    InsertionRules rules = new InsertionRules(ruleSet);

    pairCounts =
        pairCounts
            .toList()
            .flatMap(
                t -> {
                  Option<Character> maybePrepend = rules.apply(t._1);
                  return maybePrepend
                      .map(
                          prepend -> {
                            Character current = t._1._1;
                            Character next = t._1._2;
                            return List.of(
                                new Tuple2<>(new Tuple2<>(current, prepend), t._2),
                                new Tuple2<>(new Tuple2<>(prepend, next), t._2));
                          })
                      .getOrElse(List.of(t));
                })
            .groupBy(t -> t._1)
            .mapValues(l -> l.map(Tuple2::_2).sum().longValue());
  }

  public Map<Character, Long> getFrequencyCount() {
    return pairCounts
        .toList()
        .flatMap(t -> List.of(new Tuple2<>(t._1._1, t._2), new Tuple2<>(t._1._2, t._2)))
        .groupBy(t -> t._1)
        .mapValues(l -> l.map(Tuple2::_2).sum().longValue())
        .mapValues(v -> ((v - 1) / 2) + 1);
  }
}
