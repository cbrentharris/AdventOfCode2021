import static org.assertj.core.api.Assertions.*;

import advent.day16.Day16;
import io.vavr.collection.List;
import org.junit.jupiter.api.Test;

public class Day16Test extends AbstractAdventTest {
  @Test
  public void day16Part1TestExplicit() {
    assertThat(Day16.addVersionNumbers("D2FE28")).isEqualTo(6);
    assertThat(Day16.addVersionNumbers("620080001611562C8802118E34")).isEqualTo(12);
    assertThat(Day16.addVersionNumbers("C0015000016115A2E0802F182340")).isEqualTo(23);
    assertThat(Day16.addVersionNumbers("8A004A801A8002F478")).isEqualTo(16);
  }

  @Test
  public void day16Part1Test() {
    List<String> hexStrings = List.of(getData("./day16-test.txt").split("\n"));
    assertThat(hexStrings.map(Day16::addVersionNumbers)).containsAll(List.of(16, 12, 23, 31));
  }

  @Test
  public void day16Part1() {
    assertThat(Day16.addVersionNumbers(getData("./day16.txt"))).isEqualTo(971);
  }

  @Test
  public void day16Part2TestExplicit() {
    assertThat(Day16.evaluate("C200B40A82")).isEqualTo(3);
    assertThat(Day16.evaluate("04005AC33890")).isEqualTo(54);
    assertThat(Day16.evaluate("9C0141080250320F1802104A08")).isEqualTo(1);
    assertThat(Day16.evaluate("CE00C43D881120")).isEqualTo(9);
    assertThat(Day16.evaluate("880086C3E88112")).isEqualTo(7);
    assertThat(Day16.evaluate("D8005AC2A8F0")).isEqualTo(1);
    assertThat(Day16.evaluate("F600BC2D8F")).isEqualTo(0);
    assertThat(Day16.evaluate("9C005AC2F8F0")).isEqualTo(0);
    assertThat(Day16.evaluate("38006F45291200")).isEqualTo(1);
  }

  @Test
  public void day16Part2() {
    assertThat(Day16.evaluate(getData("./day16.txt"))).isEqualTo(831996589851L);
  }
}
