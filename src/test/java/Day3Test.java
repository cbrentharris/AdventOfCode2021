import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

public class Day3Test extends AbstractAdventTest {

  @Test
  public void testDay3Part1() {
    assertThat(getString()).hasSize(1);
  }

  @Test
  public void testDay3Part2() {
    assertThat(getString()).hasSize(1);
  }

  private List<String> getString() {
    return Stream.of(getData("./day3.txt").split("\n")).collect(Collectors.toList());
  }
}
