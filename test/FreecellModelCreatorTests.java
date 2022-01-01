import static org.junit.Assert.assertEquals;

import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.FreecellModelCreator;
import cs3500.freecell.model.FreecellModelCreator.GameType;
import cs3500.freecell.model.hw02.ICard;
import cs3500.freecell.model.hw02.SimpleFreecellModel;
import cs3500.freecell.model.hw04.MultiMoveFreecellModel;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the FreecellModelCreator class.
 */
public class FreecellModelCreatorTests {

  FreecellModelCreator creator;
  FreecellModel<ICard> simple;
  FreecellModel<ICard> multi;

  @Before
  public void setUp() throws IllegalArgumentException {
    creator = new FreecellModelCreator();
    simple = new SimpleFreecellModel();
    multi = new MultiMoveFreecellModel();
  }

  @Test
  public void createSimple() {
    FreecellModel<ICard> model = creator.create(GameType.SINGLEMOVE);
    assertEquals(model.getClass(), simple.getClass());
  }

  @Test
  public void createMulti() {
    FreecellModel<ICard> model = creator.create(GameType.MULTIMOVE);
    assertEquals(model.getClass(), multi.getClass());
  }

  @Test(expected = NullPointerException.class)
  public void createNull() {
    creator.create(null);
  }
}
