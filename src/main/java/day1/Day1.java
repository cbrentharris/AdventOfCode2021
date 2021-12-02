package day1;

import java.util.List;
import java.util.Queue;
import java.util.stream.IntStream;
import lombok.NonNull;
import org.apache.commons.collections4.queue.CircularFifoQueue;

public class Day1 {
  private static final int DEPTH_WINDOW_SIZE = 3;

  public static int findInstancesWhenDepthHasPositiveChange(@NonNull List<Depth> depths) {
    if (depths.size() == 0) {
      return 0;
    }
    int currentDepth = depths.get(0).getDepthInMeters();
    int totalPositiveChanges = 0;
    for (int i = 1; i < depths.size(); i++) {
      Depth d = depths.get(i);
      if (d.getDepthInMeters() > currentDepth) {
        totalPositiveChanges++;
      }
      currentDepth = d.getDepthInMeters();
    }
    return totalPositiveChanges;
  }

  public static int findInstancesWhenDepthWindowHasPositiveChange(@NonNull List<Depth> depths) {
    Queue<Depth> circularQueue = new CircularFifoQueue<>(DEPTH_WINDOW_SIZE);
    if (depths.size() == 0) {
      return 0;
    }
    if (depths.size() < DEPTH_WINDOW_SIZE) {
      return 0;
    }
    IntStream.range(0, DEPTH_WINDOW_SIZE).mapToObj(depths::get).forEach(circularQueue::add);

    int totalDepthIncreases = 0;
    for (int i = DEPTH_WINDOW_SIZE; i < depths.size(); i++) {
      Depth d = depths.get(i);
      int currentWindowSize = getSum(circularQueue);
      circularQueue.add(d);
      int nextWindowSize = getSum(circularQueue);
      if (nextWindowSize > currentWindowSize) {
        totalDepthIncreases++;
      }
    }
    return totalDepthIncreases;
  }

  private static int getSum(Queue<Depth> depths) {
    return depths.stream().mapToInt(Depth::getDepthInMeters).sum();
  }
}
