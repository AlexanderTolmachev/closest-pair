package ru.spbstu.appmath.cg.closesttpair;

import org.jetbrains.annotations.NotNull;

/**
 * User: Alexander Tolmachev
 */
public class Utils {
  private Utils() {
  }

  public static double getDistanceSquare(@NotNull final Point first, @NotNull final Point second) {
    final double dx = (first.getX() - second.getX());
    final double dy = (first.getY() - second.getY());
    return getSquare(dx) + getSquare(dy);
  }

  public static double getSquare(final double number) {
    return number * number;
  }
}
