package day4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Data;

public class BingoBoard {

  @Data
  private static class BingoSquareIndex {
    private final List<BingoSquare> rows;
    private final List<BingoSquare> columns;
    private int runningTotal;
  }

  private final List<List<BingoSquare>> squares;
  private final Map<Integer, BingoSquareIndex> squareIndex;

  public BingoBoard(List<List<BingoSquare>> squares) {
    this.squares = squares;
    squareIndex = new HashMap<>();
    for (int col = 0; col < squares.get(0).size(); col++) {
      int finalCol = col;
      List<BingoSquare> cols = new ArrayList<>();
      squares.forEach(
          rows -> {
            BingoSquare bingoCol = rows.get(finalCol);
            cols.add(bingoCol);
            BingoSquareIndex index = new BingoSquareIndex(rows, cols);
            squareIndex.put(bingoCol.getNumber(), index);
          });
    }
  }

  public boolean call(BingoSquare square) {
    if (!squareIndex.containsKey(square.getNumber())) {
      return false;
    }

    BingoSquareIndex index = squareIndex.get(square.getNumber());
    List<BingoSquare> rows = index.getRows();
    List<BingoSquare> cols = index.getColumns();
    rows.stream().filter(s -> s.getNumber() == square.getNumber()).forEach(s -> s.setCalled(true));
    cols.stream().filter(s -> s.getNumber() == square.getNumber()).forEach(s -> s.setCalled(true));

    return rows.stream().allMatch(BingoSquare::isCalled)
        || cols.stream().allMatch(BingoSquare::isCalled);
  }

  public int winningScore(BingoSquare square) {
    return unmarkedSquareSum() * square.getNumber();
  }

  private int unmarkedSquareSum() {
    return squares.stream()
        .flatMap(List::stream)
        .filter(s -> !s.isCalled())
        .mapToInt(BingoSquare::getNumber)
        .sum();
  }
}
