package ru.spbstu.appmath.cg.closesttpair;

import org.jetbrains.annotations.NotNull;
import ru.spbstu.appmath.cg.closesttpair.impl.FastClosestPairTaskSolver;
import ru.spbstu.appmath.cg.closesttpair.impl.SimpleClosestPairTaskSolver;

import java.io.IOException;
import java.util.List;

/**
 * User: Alexander Tolmachev
 */
public class ClosestPair {
  private static final String SIMPLE_MODE = "simple";
  private static final String FAST_MODE = "fast";

  public static void main(String[] args) {
    if (args.length != 2) {
      System.out.println("Invalid argument number!");
      printHelp();
      return;
    }

    final String mode = args[0];
    final ClosestPairTaskSolver solver = getSolverByMode(mode);
    if (solver == null) {
      System.out.println("Unsupported mode");
      printHelp();
      return;
    }

    final String inputFilename = args[1];
    final Parser parser = new Parser();
    final List<Point> points;
    System.out.println("Parsing input file...");
    try {
      points = parser.parse(inputFilename);
    } catch (IOException e) {
      System.out.println(String.format("Unable to read file %s: %s", inputFilename, e.getMessage()));
      return;
    } catch (InvalidInputFileFormatException e) {
      System.out.println(String.format("Invalid input file format: %s", e.getMessage()));
      return;
    }

    System.out.println("Solving task...");
    final long startTime = System.currentTimeMillis();
    final Pair<Integer> solution = solver.solve(points);
    final long finishTime = System.currentTimeMillis();
    if (solution == null) {
      System.out.println("No solution found");
    } else {
      System.out.println(String.format("Indices of closest pair points: %s", solution));
    }
    System.out.println(String.format("Time: %s ms", finishTime - startTime));
  }

  private static ClosestPairTaskSolver getSolverByMode(@NotNull final String mode) {
    switch (mode) {
      case SIMPLE_MODE:
        return new SimpleClosestPairTaskSolver();
      case FAST_MODE:
        return new FastClosestPairTaskSolver();
      default:
        return null;
    }
  }

  private static void printHelp() {
    System.out.println("Usage: java -jar closest-pair.jar <mode> <input-file>");
    System.out.println(String.format("Supported modes: %s, %s", SIMPLE_MODE, FAST_MODE));
  }
}
