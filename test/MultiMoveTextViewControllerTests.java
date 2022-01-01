import static org.junit.Assert.assertEquals;

import cs3500.freecell.model.hw04.MultiMoveFreecellModel;
import cs3500.freecell.view.FreecellTextView;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for MultiMoveFreecellTextView methods that were added in HW3 (renderBoard and
 * renderMessage).
 */
public class MultiMoveTextViewControllerTests {

  MultiMoveFreecellModel model;

  @Before
  public void setUp() {
    model = new MultiMoveFreecellModel();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFreecellTextViewConstructorNullAppendable() {
    new FreecellTextView(model, null);
  }

  @Test(expected = IOException.class)
  public void testFreecellTextViewRenderBoardAppendableDoesNotAppend() throws IOException {
    model.startGame(model.getDeck(), 52, 4, false);
    Appendable ap = new AppendableForTest();
    new FreecellTextView(model, ap).renderBoard();
  }

  @Test(expected = IOException.class)
  public void testFreecellTextViewRenderMessageAppendableDoesNotAppend() throws IOException {
    model.startGame(model.getDeck(), 52, 4, false);
    Appendable ap = new AppendableForTest();
    new FreecellTextView(model, ap).renderMessage("invalid move");
  }

  @Test
  public void testRenderMessage() throws IOException {
    Appendable ap = new StringBuilder();
    new FreecellTextView(model, ap).renderMessage("invalid move");
    assertEquals(ap.toString(), "invalid move");
  }

  @Test
  public void testRenderEmptyBoard() throws IOException {
    Appendable ap = new StringBuilder();
    new FreecellTextView(model, ap).renderBoard();
    assertEquals(ap.toString(), "");
  }

  @Test
  public void testRenderNonEmptyBoard() throws IOException {
    model.startGame(model.getDeck(), 4, 4, false);
    Appendable ap = new StringBuilder();
    new FreecellTextView(model, ap).renderBoard();
    assertEquals(ap.toString(),
        new StringBuilder().append("F1:\n").append("F2:\n").append("F3:\n").append("F4:\n")
            .append("O1:\n").append("O2:\n").append("O3:\n").append("O4:\n")
            .append("C1: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣, K♣\n")
            .append("C2: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n")
            .append("C3: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n")
            .append("C4: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠").toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRenderMessageNullMessage() throws IOException {
    Appendable ap = new StringBuilder();
    new FreecellTextView(model, ap).renderMessage(null);
  }
}
