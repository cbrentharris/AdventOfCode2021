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
}
