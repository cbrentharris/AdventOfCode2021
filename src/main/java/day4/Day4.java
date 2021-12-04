package day4;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day4 {
  public static int winningScore(List<BingoSquare> drawnSquares, List<BingoBoard> boards) {
    for (BingoSquare square : drawnSquares) {
      for (BingoBoard board : boards) {
        boolean won = board.call(square);
        if (won) {
          return board.winningScore(square);
        }
      }
    }
    throw new IllegalStateException("No winning board: " + boards.toString());
  }

  public static int lastWinningBoardScore(List<BingoSquare> drawnSquares, List<BingoBoard> boards) {
    Set<BingoBoard> remainingBoards = new HashSet<>(boards);
    for (BingoSquare square : drawnSquares) {
      for (BingoBoard board : boards) {
        boolean won = board.call(square);
        if (won) {
          int score = board.winningScore(square);
          remainingBoards.remove(board);
          if (remainingBoards.size() == 0) {
            return score;
          }
        }
      }
    }
    throw new IllegalStateException("No winning board: " + boards.toString());
  }
}
