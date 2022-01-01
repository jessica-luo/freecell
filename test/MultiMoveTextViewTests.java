import static org.junit.Assert.assertEquals;

import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.PileType;
import cs3500.freecell.model.hw04.MultiMoveFreecellModel;
import cs3500.freecell.view.FreecellTextView;
import org.junit.Test;

/**
 * Tests for MultiMoveFreecellTextView.
 */
public class MultiMoveTextViewTests {

  FreecellModel model = new MultiMoveFreecellModel();
  FreecellTextView textView = new FreecellTextView(model);

  @Test(expected = IllegalArgumentException.class)
  public void testFreecellTextViewConstructorNullModel() {
    new FreecellTextView(null);
  }

  @Test
  public void toStringModelEmpty() {
    assertEquals(textView.toString(),
        "");
  }

  @Test
  public void toStringModelNonEmptyNotShuffled() {
    model.startGame(model.getDeck(), 8, 4, false);
    assertEquals(new FreecellTextView(model).toString(),
        new StringBuilder().append("F1:\n").append("F2:\n").append("F3:\n").append("F4:\n")
            .append("O1:\n").append("O2:\n").append("O3:\n").append("O4:\n")
            .append("C1: A♣, 3♣, 5♣, 7♣, 9♣, J♣, K♣\n").append("C2: A♦, 3♦, 5♦, 7♦, 9♦, J♦, K♦\n")
            .append("C3: A♥, 3♥, 5♥, 7♥, 9♥, J♥, K♥\n").append("C4: A♠, 3♠, 5♠, 7♠, 9♠, J♠, K♠\n")
            .append("C5: 2♣, 4♣, 6♣, 8♣, 10♣, Q♣\n").append("C6: 2♦, 4♦, 6♦, 8♦, 10♦, Q♦\n")
            .append("C7: 2♥, 4♥, 6♥, 8♥, 10♥, Q♥\n").append("C8: 2♠, 4♠, 6♠, 8♠, 10♠, Q♠")
            .toString());
  }

  @Test
  public void toViewMoveToOpenPile() {
    model.startGame(model.getDeck(), 8, 4, false);
    model.move(PileType.CASCADE, 0, 6, PileType.OPEN, 0);
    assertEquals(new FreecellTextView(model).toString(),
        new StringBuilder().append("F1:\n").append("F2:\n").append("F3:\n").append("F4:\n")
            .append("O1: K♣\n").append("O2:\n").append("O3:\n").append("O4:\n")
            .append("C1: A♣, 3♣, 5♣, 7♣, 9♣, J♣\n").append("C2: A♦, 3♦, 5♦, 7♦, 9♦, J♦, K♦\n")
            .append("C3: A♥, 3♥, 5♥, 7♥, 9♥, J♥, K♥\n").append("C4: A♠, 3♠, 5♠, 7♠, 9♠, J♠, K♠\n")
            .append("C5: 2♣, 4♣, 6♣, 8♣, 10♣, Q♣\n").append("C6: 2♦, 4♦, 6♦, 8♦, 10♦, Q♦\n")
            .append("C7: 2♥, 4♥, 6♥, 8♥, 10♥, Q♥\n").append("C8: 2♠, 4♠, 6♠, 8♠, 10♠, Q♠")
            .toString());
  }

  // the same game again but the deck is shuffled
  @Test
  public void toStringModelNonEmptyShuffled() {
    model.startGame(model.getDeck(), 8, 4, true);
    assertEquals(new FreecellTextView(model).toString().equals(
        new StringBuilder().append("F1:\n").append("F2:\n").append("F3:\n").append("F4:\n")
            .append("O1:\n").append("O2:\n").append("O3:\n").append("O4:\n")
            .append("C1: A♣, 3♣, 5♣, 7♣, 9♣, J♣, K♣\n").append("C2: A♦, 3♦, 5♦, 7♦, 9♦, J♦, K♦\n")
            .append("C3: A♥, 3♥, 5♥, 7♥, 9♥, J♥, K♥\n").append("C4: A♠, 3♠, 5♠, 7♠, 9♠, J♠, K♠\n")
            .append("C5: 2♣, 4♣, 6♣, 8♣, 10♣, Q♣\n").append("C6: 2♦, 4♦, 6♦, 8♦, 10♦, Q♦\n")
            .append("C7: 2♥, 4♥, 6♥, 8♥, 10♥, Q♥\n").append("C8: 2♠, 4♠, 6♠, 8♠, 10♠, Q♠\n")
            .toString()), false);
  }

  @Test
  public void toStringModelNonEmptyNotShuffled4Cascade2Open() {
    model.startGame(model.getDeck(), 4, 2, false);
    assertEquals(new FreecellTextView(model).toString(),
        new StringBuilder().append("F1:\n").append("F2:\n").append("F3:\n").append("F4:\n")
            .append("O1:\n").append("O2:\n")
            .append("C1: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣, K♣\n")
            .append("C2: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n")
            .append("C3: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n")
            .append("C4: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠").toString());
  }
}
