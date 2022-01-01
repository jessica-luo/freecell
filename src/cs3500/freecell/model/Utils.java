package cs3500.freecell.model;

/**
 * Public utility class. Contains an operation which requires an object of type T to be non-null.
 */
public class Utils {

  /**
   * Determines whether the given object of type T is null.
   *
   * @param obj determining whether the object is null
   * @param <T> type of the object
   * @return the object, if not null
   * @throws IllegalArgumentException if the object is null
   */
  public static <T> T requireNonNull(T obj) {
    if (obj == null) {
      throw new IllegalArgumentException("null input");
    }
    return obj;
  }
}
