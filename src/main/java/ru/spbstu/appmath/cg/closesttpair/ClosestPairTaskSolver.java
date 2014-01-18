package ru.spbstu.appmath.cg.closesttpair;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * User: Alexander Tolmachev
 */
public interface ClosestPairTaskSolver {
  public Pair<Integer> solve(@NotNull final List<Point> points);
}
