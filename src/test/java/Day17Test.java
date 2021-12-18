import static org.assertj.core.api.Assertions.*;

import advent.common.Pair;
import advent.day17.Day17;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.jupiter.api.Test;

public class Day17Test extends AbstractAdventTest {
  private static final Pattern X_WIDTH_PATTERN = Pattern.compile("x=(-?[0-9]+)..(-?[0-9]+)");
  private static final Pattern Y_WIDTH_PATTERN = Pattern.compile("y=(-?[0-9]+)..(-?[0-9]+)");

  @Test
  public void day17Part1Test() {
    String fileName = "./day17-test.txt";
    assertThat(Day17.getHighestYPointReachingTargetArea(getYWidth(fileName))).isEqualTo(45);
  }

  @Test
  public void day17Part1() {
    String fileName = "./day17.txt";
    assertThat(Day17.getHighestYPointReachingTargetArea(getYWidth(fileName))).isEqualTo(5151);
  }

  @Test
  public void day17Part2Test() {
    String fileName = "./day17-test.txt";
    assertThat(Day17.countOfVelocitiesThatReachTarget(getXWidth(fileName), getYWidth(fileName)))
        .isEqualTo(112);
  }

  @Test
  public void day17Part2() {
    String fileName = "./day17.txt";
    assertThat(Day17.countOfVelocitiesThatReachTarget(getXWidth(fileName), getYWidth(fileName)))
        .isEqualTo(968);
  }

  private Pair<Integer, Integer> getXWidth(String fileName) {
    String data = getData(fileName);
    Matcher matcher = X_WIDTH_PATTERN.matcher(data);
    matcher.find();
    Integer left = Integer.parseInt(matcher.group(1));
    Integer right = Integer.parseInt(matcher.group(2));
    return new Pair<>(left, right);
  }

  private Pair<Integer, Integer> getYWidth(String fileName) {
    String data = getData(fileName);
    Matcher matcher = Y_WIDTH_PATTERN.matcher(data);
    matcher.find();
    Integer left = Integer.parseInt(matcher.group(1));
    Integer right = Integer.parseInt(matcher.group(2));
    return new Pair<>(left, right);
  }
}
