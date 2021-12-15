import static org.assertj.core.api.Assertions.*;

import advent.day4.BingoBoard;
import advent.day4.BingoSquare;
import advent.day4.Day4;
import advent.day4.Pair;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

public class Day4Test extends AbstractAdventTest {
  private static final int BOARD_SIZE = 5;

  @Test
  public void day4Part1() {
    Pair<List<BingoSquare>, List<BingoBoard>> squaresAndBoard = getBoardAndSquares("./day4.txt");
    assertThat(Day4.winningScore(squaresAndBoard.getFirst(), squaresAndBoard.getSecond()))
        .isEqualTo(71708);
  }

  @Test
  public void day4Part1TestInput() {
    Pair<List<BingoSquare>, List<BingoBoard>> squaresAndBoard =
        getBoardAndSquares("./day4-test.txt");
    assertThat(Day4.winningScore(squaresAndBoard.getFirst(), squaresAndBoard.getSecond()))
        .isEqualTo(4512);
  }

  @Test
  public void day4Part2() {
    Pair<List<BingoSquare>, List<BingoBoard>> squaresAndBoard = getBoardAndSquares("./day4.txt");
    assertThat(Day4.lastWinningBoardScore(squaresAndBoard.getFirst(), squaresAndBoard.getSecond()))
        .isEqualTo(34726);
  }

  @Test
  public void day4Part2TestInput() {
    Pair<List<BingoSquare>, List<BingoBoard>> squaresAndBoard =
        getBoardAndSquares("./day4-test.txt");
    assertThat(Day4.lastWinningBoardScore(squaresAndBoard.getFirst(), squaresAndBoard.getSecond()))
        .isEqualTo(1924);
  }

  private Pair<List<BingoSquare>, List<BingoBoard>> getBoardAndSquares(String fileName) {
    String[] lines = getData(fileName).split("\n");
    String firstline = lines[0];

    List<BingoSquare> calledSquares =
        Stream.of(firstline.split(","))
            .map(Integer::parseInt)
            .map(BingoSquare::new)
            .collect(Collectors.toList());

    List<List<BingoSquare>> boardSquares =
        Stream.of(lines)
            .skip(1)
            .filter(s -> !s.isEmpty())
            .map(s -> s.split("\\s+"))
            .map(
                sParts ->
                    Stream.of(sParts)
                        .filter(s -> !s.isEmpty())
                        .map(Integer::parseInt)
                        .map(BingoSquare::new)
                        .collect(Collectors.toList()))
            .collect(Collectors.toList());

    List<BingoBoard> boards = new ArrayList<>();

    for (int i = 0; i < boardSquares.size(); i += BOARD_SIZE) {
      boards.add(new BingoBoard(boardSquares.subList(i, i + BOARD_SIZE)));
    }

    Pair<List<BingoSquare>, List<BingoBoard>> squaresAndBoards = new Pair<>(calledSquares, boards);
    return squaresAndBoards;
  }
}
