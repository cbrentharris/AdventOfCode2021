package day8;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class SevenSegmentNumber {
  private final String input;
  private int[] encoding;

  public int actualNumber() {
    int size = input.length();
    switch (size) {
      case 2:
        return 1;
      case 4:
        return 4;
      case 3:
        return 7;
      case 7:
        return 8;
      default:
        throw new IllegalStateException("Can't yet deduce number");
    }
  }
}
