package day13;

import io.vavr.Tuple2;
import io.vavr.collection.List;
import io.vavr.collection.Stream;

public class Day13 {
  private static final Character HASHTAG = '#';

  public static int visibleDots(
      int directionCount, Tuple2<Character[][], List<FoldInstruction>> gridAndDirections) {
    Character[][] grid = gridAndDirections._1;
    List<FoldInstruction> instructions = gridAndDirections._2;

    int directionsFollowed = 0;
    while (directionsFollowed < directionCount) {
      grid = followInstruction(grid, instructions.get(directionsFollowed));
      directionsFollowed++;
    }
    return Stream.of(grid).map(row -> Stream.of(row).count(HASHTAG::equals)).sum().intValue();
  }

  private static Character[][] followInstruction(
      Character[][] grid, FoldInstruction foldInstruction) {
    int newColumnEnding = grid[0].length;
    int newRowEnding = grid.length;
    int oldColumnEnding = newColumnEnding;
    int oldRowEnding = newRowEnding;
    Character[][] newGrid;

    if (foldInstruction.getAxis() == FoldInstruction.Axis.X) {
      newColumnEnding = foldInstruction.getCoordinate();
    } else {
      newRowEnding = foldInstruction.getCoordinate();
    }

    newGrid = new Character[newRowEnding][];
    for (int rowIndex = 0; rowIndex < newRowEnding; rowIndex++) {
      Character[] existingRow = grid[rowIndex];
      Character[] newRow = new Character[newColumnEnding];
      System.arraycopy(existingRow, 0, newRow, 0, newColumnEnding);
      newGrid[rowIndex] = newRow;
    }

    for (int rowIndex = 0; rowIndex < newRowEnding; rowIndex++) {
      for (int colIndex = 0; colIndex < newColumnEnding; colIndex++) {
        if (grid[rowIndex][colIndex] != HASHTAG) {
          // Transpose LEFT
          if (foldInstruction.getAxis() == FoldInstruction.Axis.X) {
            int destColIndex = oldColumnEnding - colIndex - 1;
            if ((grid[rowIndex][destColIndex] == HASHTAG)) {
              newGrid[rowIndex][colIndex] = HASHTAG;
            }
          } else {

            // Transpose UP
            int destRowIndex = oldRowEnding - rowIndex - 1;
            if ((grid[destRowIndex][colIndex] == HASHTAG)) {
              newGrid[rowIndex][colIndex] = HASHTAG;
            }
          }
        }
      }
    }
    return newGrid;
  }

  public static void printInstructions(
      Tuple2<Character[][], List<FoldInstruction>> gridAndInstructions) {
    Character[][] grid = gridAndInstructions._1;
    List<FoldInstruction> instructions = gridAndInstructions._2;

    for (FoldInstruction instruction : instructions) {
      grid = followInstruction(grid, instruction);
    }

    StringBuffer sb = new StringBuffer();
    for (Character[] characters : grid) {
      for (Character c : characters) {
        if (c == null) {
          sb.append(" ");
        } else {
          sb.append("■");
        }
      }
      sb.append("\n");
    }
    System.out.println(sb);
  }
}
