package ru.spbstu.appmath.cg.closesttpair.impl;


import org.jetbrains.annotations.NotNull;
import ru.spbstu.appmath.cg.closesttpair.ClosestPairTaskSolver;
import ru.spbstu.appmath.cg.closesttpair.Pair;
import ru.spbstu.appmath.cg.closesttpair.Point;
import ru.spbstu.appmath.cg.closesttpair.Utils;

import java.util.List;

/**
 * Simple closest pair task solver with O(N^2) time complexity.
 * <p/>
 * User: Alexander Tolmachev
 */
public class SimpleClosestPairTaskSolver implements ClosestPairTaskSolver {
  @Override
  public Pair<Integer> solve(@NotNull final List<Point> points) {
    if (points.size() < 2) {
      return null;
    }

    Pair<Integer> closestPairIndices = null;
    double minDistanceSquare = Double.MAX_VALUE;
    for (int first = 0; first < points.size(); first++) {
      for (int second = 0; second < points.size(); second++) {
        if (first == second) {
          continue;
        }
        final double distanceSquare = Utils.getDistanceSquare(points.get(first), points.get(second));
        if (distanceSquare < minDistanceSquare) {
          minDistanceSquare = distanceSquare;
          closestPairIndices = Pair.create(first, second);
        }
      }
    }
    assert closestPairIndices != null;
    return closestPairIndices;
  }
}
