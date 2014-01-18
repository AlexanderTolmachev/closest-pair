package ru.spbstu.appmath.cg.closesttpair;

/**
 * User: Alexander Tolmachev
 */
public class Pair<T> {
  private final T first;
  private final T second;

  public static <T> Pair<T> create(T first, T second) {
    return new Pair<T>(first, second);
  }

  private Pair(T first, T second) {
    this.first = first;
    this.second = second;
  }

  public T getFirst() {
    return first;
  }

  public T getSecond() {
    return second;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o == null || o.getClass() != getClass()) {
      return false;
    }
    final Pair other = (Pair) o;
    return first.equals(other.first) && second.equals(other.second);
  }

  @Override
  public int hashCode() {
    int result = 17;
    result = 31 * result + first.hashCode();
    result = 31 * result + second.hashCode();
    return result;
  }

  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder();
    return builder.append("<").append(first).append(", ").append(second).append(">").toString();
  }
}
