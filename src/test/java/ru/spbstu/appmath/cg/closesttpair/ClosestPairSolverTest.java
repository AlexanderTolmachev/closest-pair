package ru.spbstu.appmath.cg.closesttpair;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.spbstu.appmath.cg.closesttpair.impl.FastClosestPairTaskSolver;
import ru.spbstu.appmath.cg.closesttpair.impl.SimpleClosestPairTaskSolver;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * User: Alexander Tolmachev
 */
@RunWith(Parameterized.class)
public class ClosestPairSolverTest {
  private static final ClosestPairTaskSolver SIMPLE_SOLVER = new SimpleClosestPairTaskSolver();
  private static final ClosestPairTaskSolver FAST_SOLVER = new FastClosestPairTaskSolver();

  private final List<Point> points;
  private final Pair<Integer> expectedSolution;

  public ClosestPairSolverTest(List<Point> points, Pair<Integer> expectedSolution) {
    this.points = points;
    this.expectedSolution = expectedSolution;
  }

  @Parameterized.Parameters
  public static Collection<Object[]> getTestData() {
    final Object[][] testData = new Object[][]{
        {
            Arrays.asList(Point.create(-1, 0.5), Point.create(1, 1), Point.create(-3, 5), Point.create(0, 0), Point.create(0, 3)),
            Pair.create(0, 3)
        },
        {
            Arrays.asList(Point.create(0, 0), Point.create(0, 0)),
            Pair.create(0, 1)
        },
        {
            Arrays.asList(Point.create(0, 0)),
            null
        },
        {
            Arrays.asList(),
            null
        },
        {
            Arrays.asList(Point.create(0, 0), Point.create(80000000, 1), Point.create(0, 800000000), Point.create(80000000, 800000000)),
            Pair.create(2, 3)
        }
    };
    return Arrays.asList(testData);
  }

  @Test
  public void testSolutionCorrectness() {
    final Pair<Integer> simpleSolution = SIMPLE_SOLVER.solve(points);
    assertThat(simpleSolution, is(equalTo(expectedSolution)));
    final Pair<Integer> fastSolution = FAST_SOLVER.solve(points);
    assertThat(fastSolution, is(equalTo(expectedSolution)));
  }
}
