package day10;

import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import io.vavr.control.Either;
import java.util.Stack;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NavigationSubsystem {
  private static final Map<Character, Character> OPEN_TO_CLOSE =
      HashMap.of('(', ')', '{', '}', '[', ']', '<', '>');
  private static final Map<Character, Integer> ILLEGAL_TOKEN_COST =
      HashMap.of(')', 3, ']', 57, '}', 1197, '>', 25137);
  private static final Map<Character, Long> COMPLETION_TOKEN_SCORE =
      HashMap.of(')', 1L, ']', 2L, '}', 3L, '>', 4L);

  private final String syntaxTokens;

  private Either<Character, Stack<Character>> firstIllegalSyntaxCharacterOrUnmatchedElements() {
    Stack<Character> syntaxStack = new Stack<>();
    for (Character c : syntaxTokens.toCharArray()) {
      if (isClosingCharacter(c)) {
        if (syntaxStack.isEmpty()) {
          return Either.left(c);
        }

        Character precedingToken = syntaxStack.pop();
        if (dontMatch(precedingToken, c)) {
          return Either.left(c);
        }
      } else {
        syntaxStack.push(c);
      }
    }

    return Either.right(syntaxStack);
  }

  public Character firstIllegalCharacter() {
    return firstIllegalSyntaxCharacterOrUnmatchedElements().getLeft();
  }

  private Stack<Character> incompleteSyntaxStack() {
    return firstIllegalSyntaxCharacterOrUnmatchedElements().right().get();
  }

  private List<Character> completionTokens() {
    return List.ofAll(incompleteSyntaxStack()).reverse().flatMap(OPEN_TO_CLOSE::get);
  }

  public boolean hasIllegalCharacters() {
    return firstIllegalSyntaxCharacterOrUnmatchedElements().isLeft();
  }

  public boolean hasNoIllegalCharacters() {
    return firstIllegalSyntaxCharacterOrUnmatchedElements().isRight();
  }

  private boolean dontMatch(Character openToken, Character closeToken) {
    return OPEN_TO_CLOSE.getOrElse(openToken, 'f') != closeToken;
  }

  private boolean isClosingCharacter(Character c) {
    return OPEN_TO_CLOSE.containsValue(c);
  }

  public static int illegalCharacterCost(Character c) {
    return ILLEGAL_TOKEN_COST
        .get(c)
        .getOrElseThrow(() -> new IllegalArgumentException("Invalid illegal character:" + c));
  }

  public long completionStringScore() {
    List<Character> completionTokens = completionTokens();
    return completionTokens.foldLeft(
        0L,
        (runningTotal, token) -> runningTotal * 5L + COMPLETION_TOKEN_SCORE.getOrElse(token, 0L));
  }
}
