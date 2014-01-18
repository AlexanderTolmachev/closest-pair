package ru.spbstu.appmath.cg.closesttpair;

/**
 * User: Alexander Tolmachev
 */
public class Point {
  private final double x;
  private final double y;

  public static Point create(double x, double y) {
    return new Point(x, y);
  }

  private Point(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public double getX() {
    return x;
  }

  public double getY() {
    return y;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o == null || o.getClass() != getClass()) {
      return false;
    }

    final Point other = (Point) o;
    return Double.compare(x, other.x) == 0 && Double.compare(y, other.y) == 0;
  }

  @Override
  public int hashCode() {
    final long xBits = Double.doubleToLongBits(x);
    final long yBits = Double.doubleToLongBits(y);
    int result = 17;
    result = 31 * result + (int) (xBits ^ (xBits >>> 32));
    result = 31 * result + (int) (yBits ^ (yBits >>> 32));
    return result;
  }

  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder();
    return builder.append("(").append(x).append(", ").append(y).append(")").toString();
  }
}


