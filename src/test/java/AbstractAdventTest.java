import io.vavr.collection.List;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Objects;
import org.apache.commons.io.IOUtils;

public class AbstractAdventTest {
  public String getData(String fileName) {
    try {
      return IOUtils.toString(
          Objects.requireNonNull(getClass().getResource(fileName)), Charset.defaultCharset());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public int[][] getGrid(String fileName) {
    String[] lines = getData(fileName).split("\n");
    int columns = lines[0].length();
    int[][] grid = new int[lines.length][columns];
    List.of(lines)
        .zipWithIndex()
        .forEach(
            lineAndRow ->
                List.of(lineAndRow._1().split(""))
                    .map(Integer::parseInt)
                    .zipWithIndex()
                    .forEach(valAndCol -> grid[lineAndRow._2()][valAndCol._2()] = valAndCol._1()));
    return grid;
  }
}
