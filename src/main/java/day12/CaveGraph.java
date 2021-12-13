package day12;

import io.vavr.Tuple2;
import io.vavr.collection.*;

import java.util.Stack;

public class CaveGraph {
  private interface ValidEdgeTraversalStrategy {
    boolean isValidTraversal(String node, Map<String, Integer> traversals);
  }

  private static class SingleSmallCaveStrategy implements ValidEdgeTraversalStrategy {
    @Override
    public boolean isValidTraversal(String node, Map<String, Integer> traversals) {
      return !node.equals(START)
          && (node.toUpperCase().equals(node) || !traversals.containsKey(node));
    }
  }

  private static class MultipleFirstSmallCaveStrategy implements ValidEdgeTraversalStrategy {
    @Override
    public boolean isValidTraversal(String node, Map<String, Integer> traversals) {
      boolean smallCaveExistsTwice =
          traversals.filter((k, v) -> k.equals(k.toLowerCase())).filter((k, v) -> v > 1).size() > 0;

      return !node.equals(START)
          && (node.toUpperCase().equals(node)
              || !traversals.containsKey(node)
              || !smallCaveExistsTwice);
    }
  }

  Map<String, Set<String>> adjacencyList;
  private static final String START = "start";
  private static final String END = "end";

  public CaveGraph(List<String> input) {
    List<String[]> parts = input.map(i -> i.split("-"));

    Map<String, Set<String>> leftSide =
        parts.groupBy(a -> a[0].strip()).mapValues(l -> l.map(a -> a[1].strip()).toSet());
    Map<String, Set<String>> rightSide =
        parts.groupBy(a -> a[1].strip()).mapValues(l -> l.map(a -> a[0].strip()).toSet());
    adjacencyList = leftSide.merge(rightSide, Set::union);
  }

  public int distinctPaths() {
    return dfs(new SingleSmallCaveStrategy());
  }

  public int distinctPathsV2() {
    return dfs(new MultipleFirstSmallCaveStrategy());
  }

  private int dfs(ValidEdgeTraversalStrategy strategy) {

    Stack<Tuple2<String, Map<String, Integer>>> nodesAndPaths = new Stack<>();
    nodesAndPaths.add(new Tuple2<>(START, HashMap.of(START, 1)));
    int paths = 0;

    while (!nodesAndPaths.isEmpty()) {
      Tuple2<String, Map<String, Integer>> nodeAndPath = nodesAndPaths.pop();
      String currentNode = nodeAndPath._1;
      Map<String, Integer> currentPath = nodeAndPath._2;

      if (currentNode.equals(END)) {
        paths++;
        continue;
      }

      Set<String> adjacencies =
          adjacencyList
              .getOrElse(currentNode, HashSet.empty())
              .filter(a -> strategy.isValidTraversal(a, currentPath));

      if (adjacencies.isEmpty()) {
        continue;
      }
      adjacencies.forEach(
          a ->
              nodesAndPaths.add(
                  new Tuple2<>(a, currentPath.put(a, currentPath.getOrElse(a, 0) + 1))));
    }

    return paths;
  }
}
