package ru.spbstu.appmath.cg.closesttpair.impl;

import org.jetbrains.annotations.NotNull;
import ru.spbstu.appmath.cg.closesttpair.ClosestPairTaskSolver;
import ru.spbstu.appmath.cg.closesttpair.Pair;
import ru.spbstu.appmath.cg.closesttpair.Point;

import java.util.*;

import static ru.spbstu.appmath.cg.closesttpair.Utils.getDistanceSquare;
import static ru.spbstu.appmath.cg.closesttpair.Utils.getSquare;

/**
 * Closest pair task solver with O(N*logN) time complexity.
 * <p/>
 * User: Alexander Tolmachev
 */
public class FastClosestPairTaskSolver implements ClosestPairTaskSolver {
  private final ClosestPairTaskSolver simpleSolver = new SimpleClosestPairTaskSolver();

  @Override
  public Pair<Integer> solve(@NotNull final List<Point> points) {
    if (points.size() < 2) {
      return null;
    }

    // Create array of point indices
    final List<Integer> pointIndices = new ArrayList<>(points.size());
    for (int pointIndex = 0; pointIndex < points.size(); pointIndex++) {
      pointIndices.add(pointIndex);
    }
    // Create array of indices, sorted by X coordinate
    final List<Integer> pointIndicesSortedByX = new ArrayList<>(pointIndices);
    Collections.sort(pointIndicesSortedByX, new Comparator<Integer>() {
      @Override
      public int compare(final Integer firstPointIndex, final Integer secondPointIndex) {
        final Point firstPoint = points.get(firstPointIndex);
        final Point secondPoint = points.get(secondPointIndex);
        if (firstPoint.getX() < secondPoint.getX()) {
          return -1;
        } else if (firstPoint.getX() > secondPoint.getX()) {
          return 1;
        }
        return 0;
      }
    });
    // Create array of indices, sorted by Y coordinate
    final List<Integer> pointIndicesSortedByY = new ArrayList<>(pointIndices);
    Collections.sort(pointIndicesSortedByY, new Comparator<Integer>() {
      @Override
      public int compare(final Integer firstPointIndex, final Integer secondPointIndex) {
        final Point firstPoint = points.get(firstPointIndex);
        final Point secondPoint = points.get(secondPointIndex);
        if (firstPoint.getY() < secondPoint.getY()) {
          return -1;
        } else if (firstPoint.getY() > secondPoint.getY()) {
          return 1;
        }
        return 0;
      }
    });

    Pair<Integer> solution = solveRecursively(points, pointIndicesSortedByX, pointIndicesSortedByY);
    if (solution.getFirst() > solution.getSecond()) {
      solution = Pair.create(solution.getSecond(), solution.getFirst());
    }
    return solution;
  }

  /**
   * Solves task recursively, using Divide & Conquer approach.
   */
  private Pair<Integer> solveRecursively(@NotNull final List<Point> points,
                                         @NotNull final List<Integer> indicesSortedByX,
                                         @NotNull final List<Integer> indicesSortedByY) {
    assert indicesSortedByX.size() == indicesSortedByY.size();
    assert indicesSortedByX.size() >= 2;

    // Process recursion end cases
    if (indicesSortedByX.size() == 2) {
      final int fistIndex = indicesSortedByX.get(0);
      final int secondIndex = indicesSortedByX.get(1);
      return Pair.create(fistIndex, secondIndex);
    }
    if (indicesSortedByX.size() == 3) {
      final Point firstPoint = points.get(indicesSortedByX.get(0));
      final Point secondPoint = points.get(indicesSortedByX.get(1));
      final Point thirdPoint = points.get(indicesSortedByX.get(2));
      final List<Point> pointsTriple = Arrays.asList(firstPoint, secondPoint, thirdPoint);
      final Pair<Integer> solution = simpleSolver.solve(pointsTriple);
      return Pair.create(indicesSortedByX.get(solution.getFirst()), indicesSortedByX.get(solution.getSecond()));
    }

    // Divide task into two smaller ones
    final int indicesMedianIndex = indicesSortedByX.size() / 2;
    final List<Integer> indicesSortedByXFirstHalf = indicesSortedByX.subList(0, indicesMedianIndex);
    final List<Integer> indicesSortedByXSecondHalf = indicesSortedByX.subList(indicesMedianIndex, indicesSortedByX.size());
    final List<Integer> indicesSortedByYFirstHalf = new ArrayList<>(indicesSortedByXFirstHalf.size());
    final List<Integer> indicesSortedByYSecondHalf = new ArrayList<>(indicesSortedByXSecondHalf.size());
    // Split array of indices sorted by Y coordinate with linear scanning
    final Set<Integer> firstHalfIndices = new HashSet<>(indicesSortedByXFirstHalf);
    for (final int pointIndex : indicesSortedByY) {
      if (firstHalfIndices.contains(pointIndex)) {
        indicesSortedByYFirstHalf.add(pointIndex);
      } else {
        indicesSortedByYSecondHalf.add(pointIndex);
      }
    }

    // Solve sub-tasks recursively
    final Pair<Integer> firstHalfSolution = solveRecursively(points, indicesSortedByXFirstHalf, indicesSortedByYFirstHalf);
    final Pair<Integer> secondHalfSolution = solveRecursively(points, indicesSortedByXSecondHalf, indicesSortedByYSecondHalf);

    // Obtain solutions of the original task from solutions of sub-tasks
    final double firstDistanceSquare = getDistanceSquare(points.get(firstHalfSolution.getFirst()), points.get(firstHalfSolution.getSecond()));
    final double secondDistanceSquare = getDistanceSquare(points.get(secondHalfSolution.getFirst()), points.get(secondHalfSolution.getSecond()));
    final double deltaSquare = Math.min(firstDistanceSquare, secondDistanceSquare); // squared half-size of vertical stripe
    // Calculate preliminary solution
    double minDistanceSquare = deltaSquare;
    Pair<Integer> solution;
    if (firstDistanceSquare < secondDistanceSquare) {
      solution = firstHalfSolution;
    } else {
      solution = secondHalfSolution;
    }
    // Calculate vertical stripe bounds
    final Point lastPointInFirstHalf = points.get(indicesSortedByXFirstHalf.get(indicesSortedByXFirstHalf.size() - 1));
    final Point firstPointInSecond = points.get(indicesSortedByXSecondHalf.get(0));
    final double medianX = (lastPointInFirstHalf.getX() + firstPointInSecond.getX()) / 2.0;
    int leftBoundIndex = 0;
    while (leftBoundIndex < indicesSortedByXFirstHalf.size() && getSquare(medianX - points.get(indicesSortedByXFirstHalf.get(leftBoundIndex)).getX()) > deltaSquare) {
      leftBoundIndex++;
    }
    int rightBoundIndex = indicesSortedByXSecondHalf.size() - 1;
    while (rightBoundIndex >= 0 && getSquare(points.get(indicesSortedByXSecondHalf.get(rightBoundIndex)).getX() - medianX) > deltaSquare) {
      rightBoundIndex--;
    }
    // If at least one half of vertical stripe contains no points, then there are no points closer then current solution
    if (leftBoundIndex >= indicesSortedByXFirstHalf.size() || rightBoundIndex < 0) {
      return solution;
    }
    // Calculate X coordinates of vertical stripe bounds
    final double leftBoundX = points.get(indicesSortedByXFirstHalf.get(leftBoundIndex)).getX();
    final double rightBoundX = points.get(indicesSortedByXSecondHalf.get(rightBoundIndex)).getX();
    // Bounds for second point of pair, actually bounds of delta x 2*delta rectangle
    int secondIndexLowBound = 0;
    int secondIndexHighBound;
    // Iterate over all points in left half of vertical stripe in ascending order of Y coordinate
    for (final int firstPointIndex : indicesSortedByYFirstHalf) {
      final Point firstPoint = points.get(firstPointIndex);
      // If point is not in the vertical stripe at all, skip it
      if (firstPoint.getX() < leftBoundX) {
        continue;
      }
      // Update bounds for second point (delta x 2*delta rectangle)
      while (secondIndexLowBound < indicesSortedByYSecondHalf.size() &&
          points.get(indicesSortedByYSecondHalf.get(secondIndexLowBound)).getY() < firstPoint.getY() &&
          getSquare(firstPoint.getY() - points.get(indicesSortedByYSecondHalf.get(secondIndexLowBound)).getY()) > deltaSquare) {
        secondIndexLowBound++;
      }
      secondIndexHighBound = secondIndexLowBound;
      while (secondIndexHighBound < indicesSortedByYSecondHalf.size() &&
          getSquare(firstPoint.getY() - points.get(indicesSortedByYSecondHalf.get(secondIndexHighBound)).getY()) <= deltaSquare) {
        secondIndexHighBound++;
      }
      // Iterate over all points in right half of vertical stripe containing in delta x 2*delta rectangle; it contains at most 6 points
      for (int secondIndex = secondIndexLowBound; secondIndex < secondIndexHighBound; secondIndex++) {
        // If point is not in the vertical stripe at all, skip comparisons
        if (points.get(indicesSortedByYSecondHalf.get(secondIndex)).getX() <= rightBoundX) {
          final Point secondPoint = points.get(indicesSortedByYSecondHalf.get(secondIndex));
          final double distanceSquare = getDistanceSquare(firstPoint, secondPoint);
          // Update solution if it's better than the current one
          if (distanceSquare < minDistanceSquare) {
            minDistanceSquare = distanceSquare;
            solution = Pair.create(firstPointIndex, indicesSortedByYSecondHalf.get(secondIndex));
          }
        }
      }
    }

    return solution;
  }
}
