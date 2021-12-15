import static org.assertj.core.api.Assertions.*;

import advent.day14.Day14;
import advent.day14.InsertionRule;
import advent.day14.PolymerChain;
import io.vavr.Tuple2;
import io.vavr.collection.List;
import org.junit.jupiter.api.Test;

public class Day14Test extends AbstractAdventTest {
  @Test
  public void day14Part1Test() {
    Tuple2<PolymerChain, List<InsertionRule>> input = getInput("./day14-test.txt");
    assertThat(Day14.polymerInsertionMostCommonLeastCommon(input._1, input._2, 10)).isEqualTo(1588);
  }

  @Test
  public void day14Part1() {
    Tuple2<PolymerChain, List<InsertionRule>> input = getInput("./day14.txt");
    assertThat(Day14.polymerInsertionMostCommonLeastCommon(input._1, input._2, 10)).isEqualTo(2768);
  }

  @Test
  public void day14Part2Test() {
    Tuple2<PolymerChain, List<InsertionRule>> input = getInput("./day14-test.txt");
    assertThat(Day14.polymerInsertionMostCommonLeastCommon(input._1, input._2, 40))
        .isEqualTo(2188189693529L);
  }

  @Test
  public void day14Part2() {
    Tuple2<PolymerChain, List<InsertionRule>> input = getInput("./day14.txt");
    assertThat(Day14.polymerInsertionMostCommonLeastCommon(input._1, input._2, 40))
        .isEqualTo(2914365137499L);
  }

  private Tuple2<PolymerChain, List<InsertionRule>> getInput(String fileName) {
    List<String> lines = List.of(getData(fileName).split("\n"));
    PolymerChain startChain = new PolymerChain(lines.head());
    List<InsertionRule> rules = lines.tail().drop(1).map(InsertionRule::new);
    return new Tuple2<>(startChain, rules);
  }
}
