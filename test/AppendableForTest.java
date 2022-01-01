import java.io.IOException;

/**
 * Appendable for testing. Throws an IO Exception if any string / character sequence greater than 0
 * in length is appended to it.
 */
public class AppendableForTest implements Appendable {

  /**
   * Constructs an appendable.
   */
  public AppendableForTest() {
    // no fields to initialize
  }

  /**
   * Appends given string to this appendable if it is greater than 0 characters long.
   *
   * @param csq character sequence to be appended
   * @return this appendable
   * @throws IOException if character sequence is greater than 0 characters long
   */
  @Override
  public Appendable append(CharSequence csq) throws IOException {
    if (csq.length() > 0) {
      throw new IOException();
    } else {
      return this;
    }
  }

  @Override
  public Appendable append(CharSequence csq, int start, int end) {
    return null;
  }

  @Override
  public Appendable append(char c) {
    return null;
  }
}
