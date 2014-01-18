package ru.spbstu.appmath.cg.closesttpair;

import org.jetbrains.annotations.NotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.spbstu.appmath.cg.closesttpair.impl.FastClosestPairTaskSolver;
import ru.spbstu.appmath.cg.closesttpair.impl.SimpleClosestPairTaskSolver;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * User: Alexander Tolmachev
 */
@RunWith(Parameterized.class)
public class FastClosestPairSolverFileTest {
  private static final int INPUT_FILE_NUMBER = 10;
  private static final String INPUT_FILE_NAME_TEMPLATE = "input-%s.txt";

  private static final Parser PARSER = new Parser();
  private static final ClosestPairTaskSolver SIMPLE_SOLVER = new SimpleClosestPairTaskSolver();
  private static final ClosestPairTaskSolver FAST_SOLVER = new FastClosestPairTaskSolver();

  @NotNull
  private final List<Point> points;

  public FastClosestPairSolverFileTest(@NotNull final List<Point> points) {
    this.points = points;
  }

  @Parameterized.Parameters
  public static Collection<Object[]> getTestData() throws IOException, InvalidInputFileFormatException {
    final List<Object[]> testData = new ArrayList<>();
    for (int fileNumber = 1; fileNumber <= INPUT_FILE_NUMBER; fileNumber++) {
      final String fileName = String.format(INPUT_FILE_NAME_TEMPLATE, fileNumber);
      final InputStream inputStream = ClassLoader.getSystemResourceAsStream(fileName);
      testData.add(new Object[]{PARSER.parse(inputStream)});
    }
    return testData;
  }

  @Test
  public void testFastSolverSolutionCorrectness() {
    // Assume that simple solver solution is correct
    final Pair<Integer> goldenSolution = SIMPLE_SOLVER.solve(points);
    final Pair<Integer> fastSolverSolution = FAST_SOLVER.solve(points);
    assertThat(fastSolverSolution, is(equalTo(goldenSolution)));
  }
}
