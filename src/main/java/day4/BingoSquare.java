package day4;

import java.util.Objects;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class BingoSquare {
  private final int number;
  private boolean called;

  @Override
  public int hashCode() {
    return Objects.hash(number);
  }
}
