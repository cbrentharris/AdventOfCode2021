import static org.assertj.core.api.Assertions.*;

import day7.CrabSubmarine;
import day7.Day7;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

public class Day7Test extends AbstractAdventTest {
  @Test
  public void day7Part1Test() {
    assertThat(Day7.minimumFuelCostToAlign(getCrabSubmarines("./day7-test.txt"))).isEqualTo(37);
  }

  @Test
  public void day7Part1() {
    assertThat(Day7.minimumFuelCostToAlign(getCrabSubmarines("./day7.txt"))).isEqualTo(340987);
  }

  @Test
  public void day7Part2Test() {
    assertThat(Day7.realFuelCost(getCrabSubmarines("./day7-test.txt"))).isEqualTo(168);
  }

  @Test
  public void day7Part2() {
    assertThat(Day7.realFuelCost(getCrabSubmarines("./day7.txt"))).isEqualTo(96987874);
  }

  private List<CrabSubmarine> getCrabSubmarines(String fileName) {
    return Stream.of(getData(fileName).split(","))
        .map(Integer::parseInt)
        .map(CrabSubmarine::new)
        .collect(Collectors.toList());
  }
}
