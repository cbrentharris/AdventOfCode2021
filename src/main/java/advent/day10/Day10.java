package advent.day10;

import io.vavr.collection.List;

public class Day10 {
  public static int illegalSyntaxSum(List<NavigationSubsystem> navigationSubsystems) {
    return navigationSubsystems
        .filter(NavigationSubsystem::hasIllegalCharacters)
        .map(NavigationSubsystem::firstIllegalCharacter)
        .map(NavigationSubsystem::illegalCharacterCost)
        .sum()
        .intValue();
  }

  public static long completedSyntaxMiddleScore(List<NavigationSubsystem> navigationSubsystems) {
    List<Long> scores =
        navigationSubsystems
            .filter(NavigationSubsystem::hasNoIllegalCharacters)
            .map(NavigationSubsystem::completionStringScore)
            .sorted();

    return scores.get(scores.size() / 2);
  }
}
