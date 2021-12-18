package advent.day6;

import advent.common.Pair;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Fish {
  private static final Map<Pair<Integer, Integer>, Long> DAYS_TO_DESCENDANTS =
      new ConcurrentHashMap<>();
  private static final int INITIAL_REPRODUCTION_DAYS = 9;
  private static final int SUBSEQUENT_REPRODUCTION_DAYS = 7;
  private final int daysUntilReproduction;

  public long descendants(int numDays) {
    return descendants(numDays, daysUntilReproduction + 1);
  }

  public long descendants(int daysRemaining, int initialRate) {
    Pair<Integer, Integer> key = new Pair<>(daysRemaining, initialRate);
    boolean alreadyComputed = DAYS_TO_DESCENDANTS.containsKey(key);
    if (alreadyComputed) {
      return DAYS_TO_DESCENDANTS.get(key);
    }
    if (daysRemaining < initialRate) {
      return 0L;
    }

    int directDescendants = ((daysRemaining - initialRate) / SUBSEQUENT_REPRODUCTION_DAYS) + 1;
    long grandChildren = 0;

    for (int i = 0; i < directDescendants; i++) {
      int dayChildIsBorn = daysRemaining - initialRate - SUBSEQUENT_REPRODUCTION_DAYS * i;
      grandChildren += descendants(dayChildIsBorn, INITIAL_REPRODUCTION_DAYS);
    }

    long descendants = directDescendants + grandChildren;
    DAYS_TO_DESCENDANTS.put(key, descendants);
    return descendants;
  }
}
