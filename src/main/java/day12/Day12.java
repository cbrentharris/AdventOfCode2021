package day12;

public class Day12 {
  public static int numPaths(CaveGraph graph) {
    return graph.distinctPaths();
  }

  public static int numPathsPart2(CaveGraph graph) {
    return graph.distinctPathsV2();
  }
}
