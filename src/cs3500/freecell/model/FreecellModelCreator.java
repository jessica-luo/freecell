package cs3500.freecell.model;

import cs3500.freecell.model.hw02.SimpleFreecellModel;
import cs3500.freecell.model.hw04.MultiMoveFreecellModel;

/**
 * Factory method class that creates a freecell game based on the specified gametype, which is an
 * enum defined in this class. The only operation is create, which initializes either a single-move
 * or multi-move game of freecell.
 */
public class FreecellModelCreator {

  /**
   * Constructs a FreecellModelCreator.
   */
  public FreecellModelCreator() {
    // there are no fields to initialize
  }

  /**
   * Represents the game type of a freecell game: either single card moves or multi-card moves
   * allowed between cascade piles.
   */
  public enum GameType {
    SINGLEMOVE, MULTIMOVE
  }

  /**
   * Returns a game of freecell, either single move or multi-move, based on the specified type.
   *
   * @param type the type of freecell game, either single or multi move
   * @return a FreecellModel game
   */
  public static FreecellModel create(GameType type) {
    switch (type) {
      case SINGLEMOVE:
        return new SimpleFreecellModel();
      case MULTIMOVE:
        return new MultiMoveFreecellModel();
      default:
        throw new IllegalArgumentException("illegal game type");
    }
  }
}
