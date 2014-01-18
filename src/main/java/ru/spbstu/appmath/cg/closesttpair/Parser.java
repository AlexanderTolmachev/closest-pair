package ru.spbstu.appmath.cg.closesttpair;


import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Alexander Tolmachev
 */
public class Parser {
  public List<Point> parse(@NotNull final String filename) throws IOException, InvalidInputFileFormatException {
    return parse(new FileInputStream(filename));
  }

  public List<Point> parse(@NotNull final InputStream inputStream) throws IOException, InvalidInputFileFormatException {
    final List<Point> points = new ArrayList<>();
    try (final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
      String line;
      while ((line = reader.readLine()) != null) {
        if (line.isEmpty()) {
          continue;
        }
        final String[] parts = line.split(" ");
        if (parts.length != 2) {
          throw new InvalidInputFileFormatException();
        }
        try {
          final double x = Double.parseDouble(parts[0]);
          final double y = Double.parseDouble(parts[1]);
          points.add(Point.create(x, y));
        } catch (NumberFormatException e) {
          throw new InvalidInputFileFormatException(e);
        }
      }
    }
    return points;
  }
}
