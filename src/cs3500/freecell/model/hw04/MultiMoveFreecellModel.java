package cs3500.freecell.model.hw04;

import cs3500.freecell.model.PileType;
import cs3500.freecell.model.Utils;
import cs3500.freecell.model.hw02.ICard;
import cs3500.freecell.model.hw02.IPile;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a multi-move model of a game of FreeCell. This model allows more than 1 card to be
 * moved at a time. Has three fields representing the piles in a game of freecell: foundation,
 * cascade, and open, which are represented by IPiles of Cards. Includes operations to generate a
 * standard deck of 52 cards, determine whether the game is over (the player has won), to make moves
 * of 1 card between piles (using a source pile, source pile number, card index, destination pile,
 * and destination pile number), to start the game (using a user inputted deck, number of cascade
 * and open piles, and whether the deck should be shuffled). Also offers operations to determine the
 * number of cascade and open piles, as well as to get the cascade, foundation, and open card at a
 * specific pileIndex and cardIndex. Also offers operations to determine the number of cards in each
 * of the types of the piles at a specific pile index.
 */
public class MultiMoveFreecellModel extends AFreecellModel {

  /**
   * Constructs a multi-move model game of Freecell. Initializes the three types of piles in the
   * game as empty.
   */
  public MultiMoveFreecellModel() {
    super();
  }

  @Override
  public void move(PileType source, int pileNumber, int cardIndex, PileType destination,
      int destPileNumber) throws IllegalArgumentException, IllegalStateException {

    // check not null
    Utils.requireNonNull(source);
    Utils.requireNonNull(destination);

    // game hasn't started
    if (getNumOpenPiles() == -1 || getNumCascadePiles() == -1
        || this.foundation.getPile().size() == 0) {
      throw new IllegalStateException("game has not started");
    }

    IPile<ICard> sourcePile = getPileFromType(source);
    IPile<ICard> destPile = getPileFromType(destination);

    // if the sourcePile is foundation pile
    if (sourcePile.getPileType() == PileType.FOUNDATION) {
      throw new IllegalArgumentException("can't move from foundation pile");
    }

    // if the pile number to retrieve is greater than number of piles or is negative
    if (pileNumber >= sourcePile.getPile().size() || destPileNumber >= destPile.getPile()
        .size() || pileNumber < 0 || destPileNumber < 0) {
      throw new IllegalArgumentException("invalid pile number");
    }

    // moving between cascade piles (allows multi-card moves)
    if (source == PileType.CASCADE && destination == PileType.CASCADE) {
      int maxCardsToMove =
          (this.open.numEmptyPiles() + 1) * ((int) Math.pow(2, this.cascade.numEmptyPiles()));

      // if you attempt to move too many cards from the source pile
      if (sourcePile.getInnerPile(pileNumber).size() - cardIndex > maxCardsToMove) {
        throw new IllegalArgumentException("not enough empty piles to move this many cards");
      }

      // if the card you are moving to doesnt form a valid build
      ICard sourcePileCard = sourcePile.getInnerPile(pileNumber).get(cardIndex);
      try {
        ICard destPileCard = destPile.getLastCard(pileNumber);
        if (destPileCard.getValue() - 1 != sourcePileCard.getValue()
            || destPileCard.getColor() == sourcePileCard.getColor()) {
          throw new IllegalArgumentException("invalid build");
        }
      } catch (IllegalArgumentException e) {
        // the pile is empty
      }

      // if the cards in the pile you're trying to move are not a valid build
      List<ICard> innerSourcePile = sourcePile.getInnerPile(pileNumber);
      ArrayList<ICard> multiCardMove = new ArrayList<>();

      for (int i = cardIndex; i < innerSourcePile.size(); i++) {
        multiCardMove.add(innerSourcePile.get(i));
      }

      if (!validBuild(multiCardMove)) {
        throw new IllegalArgumentException("invalid build");
      }

      // actually moving the cards
      for (ICard c : multiCardMove) {
        destPile.addCard(c, destPileNumber);
        sourcePile.getInnerPile(pileNumber).remove(cardIndex);
      }
    } else {
      // other non cascade to cascade moves:

      // if the card index is not the last card in the specified pile
      if (cardIndex != sourcePile.getInnerPile(pileNumber).size() - 1
          || sourcePile.getInnerPile(pileNumber).size() == 0) {
        throw new IllegalArgumentException("invalid card index");
      }

      ICard cardToAdd = sourcePile.getInnerPile(pileNumber).get(cardIndex);
      destPile.addCard(cardToAdd, destPileNumber);

      sourcePile.removeLastCard(pileNumber);
    }
  }

  /**
   * Does the given list of cards form a valid build? A valid build  should be arranged in
   * alternating colors and consecutive, descending values.
   *
   * @return a boolean, true if the cards are a valid build, and false if otherwise
   */
  private boolean validBuild(List<ICard> cards) {
    boolean valid = true;
    for (int i = 0; i < cards.size() - 1; i++) {
      ICard c = cards.get(i);
      ICard c2 = cards.get(i + 1);
      if (c.getColor() == c2.getColor() || c.getValue() - 1 != c2.getValue()) {
        valid = false;
      }
    }

    return valid;
  }

  /**
   * Retrieves the correct type of pile using the given type.
   *
   * @param type the type of pile
   * @return the piles of cards in the specified type's pile
   */
  private IPile<ICard> getPileFromType(PileType type) {
    IPile<ICard> pile;
    switch (type) {
      case OPEN:
        pile = this.open;
        break;
      case CASCADE:
        pile = this.cascade;
        break;
      case FOUNDATION:
        pile = this.foundation;
        break;
      default:
        throw new IllegalArgumentException("unknown pile type");
    }
    return pile;
  }
}
