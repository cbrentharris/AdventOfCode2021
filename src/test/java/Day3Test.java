import static org.assertj.core.api.Assertions.*;

import day3.Day3;
import java.util.BitSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

public class Day3Test extends AbstractAdventTest {

  @Test
  public void testDay3Part1() {
    assertThat(Day3.powerConsuption(getBitSets())).isEqualTo(3912944);
  }

  @Test
  public void testDay3Part2() {
    assertThat(Day3.lifeSupport(getBitSets(), 12)).isEqualTo(4996233);
  }

  @Test
  public void testDay3Part2TestInput() {
    assertThat(Day3.lifeSupport(getBitSets("./day3-test.txt"), 5)).isEqualTo(230);
  }

  private List<BitSet> getBitSets() {
    return getBitSets("./day3.txt");
  }

  private List<BitSet> getBitSets(String fileName) {
    return Stream.of(getData(fileName).split("\n"))
        .map(
            s -> {
              String[] parts = s.split("");
              BitSet bitSet = new BitSet(parts.length);
              for (int i = 0; i < parts.length; i++) {
                if (parts[i].equals("1")) {
                  bitSet.set(i, true);
                } else {
                  bitSet.set(i, false);
                }
              }
              return bitSet;
            })
        .collect(Collectors.toList());
  }
}
