package cs3500.freecell.model.hw04;

import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.hw02.Card;
import cs3500.freecell.model.hw02.CardValue;
import cs3500.freecell.model.hw02.CascadePile;
import cs3500.freecell.model.hw02.FoundationPile;
import cs3500.freecell.model.hw02.ICard;
import cs3500.freecell.model.hw02.IPile;
import cs3500.freecell.model.hw02.OpenPile;
import cs3500.freecell.model.hw02.Suit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Abstract base class representing a model of a game of Freecell. The fields are the piles found in
 * the game: foundation, cascade, and open. Offers all operations found in the FreecellModel
 * interface.
 */
public abstract class AFreecellModel implements FreecellModel<ICard> {

  protected final IPile<ICard> foundation;
  protected final IPile<ICard> cascade;
  protected final IPile<ICard> open;

  /**
   * Constructs a simple model game of freecell. Initializes the three types of piles in the game as
   * empty.
   */
  public AFreecellModel() {
    this.foundation = new FoundationPile();
    this.cascade = new CascadePile();
    this.open = new OpenPile();
  }

  @Override
  public List<ICard> getDeck() {
    List<ICard> deck = new ArrayList<>();
    for (int i = 0; i < 13; i++) {
      for (int j = 0; j < 4; j++) {
        switch (j) {
          case 0:
            deck.add(new Card(getDeckHelp(i), Suit.CLUB));
            break;
          case 1:
            deck.add(new Card(getDeckHelp(i), Suit.DIAMOND));
            break;
          case 2:
            deck.add(new Card(getDeckHelp(i), Suit.HEART));
            break;
          case 3:
            deck.add(new Card(getDeckHelp(i), Suit.SPADE));
            break;
          default:
        }
      }
    }
    return deck;
  }

  /**
   * Helper for getDeck to produce a card rank based on an integer input 0-12, or null if not a
   * number within that range.
   *
   * @param i the integer value of the card
   * @return the rank of the card as a CardValue
   */
  private CardValue getDeckHelp(int i) {
    CardValue val;
    switch (i) {
      case 0:
        val = CardValue.ACE;
        break;
      case 1:
        val = CardValue.TWO;
        break;
      case 2:
        val = CardValue.THREE;
        break;
      case 3:
        val = CardValue.FOUR;
        break;
      case 4:
        val = CardValue.FIVE;
        break;
      case 5:
        val = CardValue.SIX;
        break;
      case 6:
        val = CardValue.SEVEN;
        break;
      case 7:
        val = CardValue.EIGHT;
        break;
      case 8:
        val = CardValue.NINE;
        break;
      case 9:
        val = CardValue.TEN;
        break;
      case 10:
        val = CardValue.JACK;
        break;
      case 11:
        val = CardValue.QUEEN;
        break;
      case 12:
        val = CardValue.KING;
        break;
      default:
        val = null;
        break;
    }
    return val;
  }

  @Override
  public void startGame(List<ICard> deck, int numCascadePiles, int numOpenPiles,
      boolean shuffle)
      throws IllegalArgumentException {

    this.cascade.getPile().clear();
    this.foundation.getPile().clear();
    this.open.getPile().clear();

    // check deck validity
    if (deck == null || deck.size() == 0 || !deckValid(deck)) {
      throw new IllegalArgumentException("invalid deck");
    }

    // check there are at least 4 cascade piles
    if (numCascadePiles < 4) {
      throw new IllegalArgumentException("not enough cascade piles");
    }

    // check that there is at least 1 open pile
    if (numOpenPiles < 1) {
      throw new IllegalArgumentException("not enough open piles");
    }

    // shuffle the deck if shuffle is true
    if (shuffle) {
      Collections.shuffle(deck);
    }

    // generates the open piles using the numOpenPiles parameter (which are empty at the start of
    // the game)
    open.genPile(numOpenPiles);

    // generates the 4 foundation piles (which are empty at the start of the game)
    foundation.genPile(4);

    // generates the cascade piles using the numCascadePiles parameter
    cascade.genPile(numCascadePiles);

    // deals the deck into the cascade piles
    for (int i = 0; i < 52; i++) {
      int pileNumber = i % numCascadePiles;
      ICard card = deck.get(i);
      cascade.getInnerPile(pileNumber).add(card);
    }
  }

  /**
   * Is the given non-empty deck valid? A deck is invalid for this game if it does not have 52
   * cards, or if there are duplicate cards, or if there are invalid cards (invalid suit or invalid
   * number), or if a card is null. (Card constructor will catch any illegal suits or numbers).
   *
   * @param deck the deck of cards
   * @return true if the deck is valid, false if not
   */
  private boolean deckValid(List<ICard> deck) {
    boolean deckValid;
    boolean noNull = true;
    ArrayList<ICard> hearts = new ArrayList<ICard>();
    ArrayList<ICard> diamonds = new ArrayList<ICard>();
    ArrayList<ICard> spades = new ArrayList<ICard>();
    ArrayList<ICard> clubs = new ArrayList<ICard>();
    ArrayList<ICard> other = new ArrayList<ICard>();

    for (ICard c : deck) {
      if (c != null) {
        switch (c.getSuit()) {
          case HEART:
            hearts.add(c);
            break;
          case DIAMOND:
            diamonds.add(c);
            break;
          case SPADE:
            spades.add(c);
            break;
          case CLUB:
            clubs.add(c);
            break;
          default:
            other.add(c);
        }
      } else {
        noNull = false;
      }
    }

    deckValid =
        suitAllValues(hearts) && suitAllValues(diamonds) && suitAllValues(spades)
            && suitAllValues(
            clubs) && other.size() == 0;

    Set<ICard> deckSet = new HashSet<>(deck);
    return deck.size() == 52 && deckSet.size() == 52 && deckValid && noNull;
  }

  /**
   * Are all 13 values present in the given list? (the thirteen values are the integers 0 to 12,
   * inclusive). The card cannot be null.
   *
   * @param suitPile the list of cards to be analyzed
   * @return true if all thirteen values are present, false if not
   */
  private boolean suitAllValues(ArrayList<ICard> suitPile) {
    ArrayList<ICard> seenCards = new ArrayList<>();
    boolean allValuesPresent = true;
    Suit suit = suitPile.get(0).getSuit();

    for (ICard c : suitPile) {
      for (ICard seen : seenCards) {
        if (c == null || c.getValue() == seen.getValue() || suit != c.getSuit()) {
          allValuesPresent = false;
        }
      }
      seenCards.add(c);
    }

    return suitPile.size() == 13 && allValuesPresent;
  }

  @Override
  public boolean isGameOver() {
    if (this.foundation.getPile().size() == 4) {
      return getNumCardsInFoundationPile(0) == 13
          && getNumCardsInFoundationPile(1) == 13
          && getNumCardsInFoundationPile(2) == 13
          && getNumCardsInFoundationPile(3) == 13;
    } else {
      return false;
    }
  }

  @Override
  public int getNumCardsInFoundationPile(int index)
      throws IllegalArgumentException, IllegalStateException {
    if (index >= 4 || index < 0) {
      throw new IllegalArgumentException("index is invalid");
    }

    // game hasn't started
    if (this.foundation.getPile().size() != 4) {
      throw new IllegalStateException("game has not started");
    }

    return this.foundation.getInnerPile(index).size();
  }

  @Override
  public int getNumCascadePiles() {
    if (this.cascade.getPile().size() == 0) {
      return -1;
    } else {
      return this.cascade.getPile().size();
    }
  }

  @Override
  public int getNumCardsInCascadePile(int index)
      throws IllegalArgumentException, IllegalStateException {
    if (getNumCascadePiles() == -1) {
      throw new IllegalStateException("game has not started");
    }
    if (index >= getNumCascadePiles() || index < 0) {
      throw new IllegalArgumentException("index is invalid");
    }

    return this.cascade.getInnerPile(index).size();
  }

  @Override
  public int getNumCardsInOpenPile(int index)
      throws IllegalArgumentException, IllegalStateException {
    if (getNumOpenPiles() == -1) {
      throw new IllegalStateException("game has not started");
    }
    if (index >= getNumOpenPiles() || index < 0) {
      throw new IllegalArgumentException("index is invalid");
    }

    return this.open.getInnerPile(index).size();
  }

  @Override
  public int getNumOpenPiles() {
    if (this.open.getPile().size() == 0) {
      return -1;
    } else {
      return this.open.getPile().size();
    }
  }

  @Override
  public ICard getFoundationCardAt(int pileIndex, int cardIndex)
      throws IllegalArgumentException, IllegalStateException {
    if (this.foundation.getPile().size() != 4) {
      throw new IllegalStateException("game has not started");
    }

    if (pileIndex >= 4 || pileIndex < 0) {
      throw new IllegalArgumentException("index is invalid");
    } else if (cardIndex >= getNumCardsInFoundationPile(pileIndex) || cardIndex < 0) {
      throw new IllegalArgumentException("index is invalid");
    }

    return this.foundation.getInnerPile(pileIndex).get(cardIndex);
  }

  @Override
  public ICard getCascadeCardAt(int pileIndex, int cardIndex)
      throws IllegalArgumentException, IllegalStateException {
    if (getNumCascadePiles() == -1) {
      throw new IllegalStateException("game has not started");
    }

    if (pileIndex >= getNumCascadePiles() || pileIndex < 0) {
      throw new IllegalArgumentException("index is invalid");
    } else if (cardIndex >= getNumCardsInCascadePile(pileIndex) || cardIndex < 0) {
      throw new IllegalArgumentException("index is invalid");
    }

    return this.cascade.getPile().get(pileIndex).get(cardIndex);
  }

  @Override
  public ICard getOpenCardAt(int pileIndex) throws
      IllegalArgumentException, IllegalStateException {
    if (getNumOpenPiles() == -1) {
      throw new IllegalStateException("game has not started");
    }

    if (pileIndex >= getNumOpenPiles() || pileIndex < 0) {
      throw new IllegalArgumentException("index is invalid");
    } else if (getNumCardsInOpenPile(pileIndex) == 0) {
      return null;
    } else {
      return this.open.getPile().get(pileIndex).get(0);
    }
  }
}
