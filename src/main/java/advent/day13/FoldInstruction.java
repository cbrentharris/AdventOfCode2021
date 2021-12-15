package advent.day13;

import lombok.Value;

@Value
public class FoldInstruction {
  public enum Axis {
    X,
    Y;
  }

  Axis axis;
  int coordinate;

  public FoldInstruction(String input) {
    String[] instructionParts = input.split(" along ")[1].split("=");
    axis = Axis.valueOf(instructionParts[0].toUpperCase());
    coordinate = Integer.parseInt(instructionParts[1]);
  }
}
