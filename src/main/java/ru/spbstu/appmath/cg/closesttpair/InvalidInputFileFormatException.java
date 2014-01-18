package ru.spbstu.appmath.cg.closesttpair;

/**
 * User: Alexander Tolmachev
 */
public class InvalidInputFileFormatException extends Exception {
  public InvalidInputFileFormatException() {
    super();
  }

  public InvalidInputFileFormatException(String message) {
    super(message);
  }

  public InvalidInputFileFormatException(String message, Throwable cause) {
    super(message, cause);
  }

  public InvalidInputFileFormatException(Throwable cause) {
    super(cause);
  }
}
