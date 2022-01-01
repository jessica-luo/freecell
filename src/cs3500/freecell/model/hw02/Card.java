package cs3500.freecell.model.hw02;

import cs3500.freecell.model.Utils;

/**
 * Represents a card in a standard deck of 52 playing cards. Has a numeric value corresponding to
 * the card value (two through ten or ace, jack, queen, king) and a suit. Includes operations to
 * observe these fields as well as an operation that determines the color of the card based on the
 * suit, and an operation that represents the card as a String. Also offers operations to determine
 * whether a card is equal to another object, and to generate a hashcode for this card.
 */
public class Card implements ICard {

  private final CardValue value;
  private final Suit suit;

  /**
   * Constructs a Card object using the inputted rank and suit.
   *
   * @param value the rank of the card
   * @param suit  the suit of the card
   */
  public Card(CardValue value, Suit suit) {
    Utils.requireNonNull(suit);
    Utils.requireNonNull(value);
    this.value = value;
    this.suit = suit;
  }

  /**
   * Indicates whether some other object is "equal to" this card (extensional equality). Two cards
   * are equal if the other object is a card and has the same value and suit as this card.
   *
   * @param p the other object being compared to this object
   * @return a boolean, true if the object is equal and false if otherwise
   */
  @Override
  public boolean equals(Object p) {
    return this.toString().equals(p.toString()) && p instanceof Card;
  }

  /**
   * Creates a hash code value for this card.
   *
   * @return the hash code value of this card
   */
  @Override
  public int hashCode() {
    int suitHashCode;
    switch (this.suit) {
      case CLUB:
        suitHashCode = 100;
        break;
      case DIAMOND:
        suitHashCode = 200;
        break;
      case HEART:
        suitHashCode = 300;
        break;
      case SPADE:
        suitHashCode = 400;
        break;
      default:
        suitHashCode = 0;
        break;
    }
    return this.getValue() + suitHashCode;
  }

  @Override
  public int getValue() {
    int intValue;
    switch (value) {
      case ACE:
        intValue = 0;
        break;
      case TWO:
        intValue = 1;
        break;
      case THREE:
        intValue = 2;
        break;
      case FOUR:
        intValue = 3;
        break;
      case FIVE:
        intValue = 4;
        break;
      case SIX:
        intValue = 5;
        break;
      case SEVEN:
        intValue = 6;
        break;
      case EIGHT:
        intValue = 7;
        break;
      case NINE:
        intValue = 8;
        break;
      case TEN:
        intValue = 9;
        break;
      case JACK:
        intValue = 10;
        break;
      case QUEEN:
        intValue = 11;
        break;
      case KING:
        intValue = 12;
        break;
      default:
        throw new IllegalArgumentException();
    }
    return intValue;
  }

  @Override
  public Suit getSuit() {
    return this.suit;
  }

  @Override
  public CardColor getColor() {
    if (this.suit == Suit.CLUB || this.suit == Suit.SPADE) {
      return CardColor.BLACK;
    } else {
      return CardColor.RED;
    }
  }

  @Override
  public String toString() {
    String suitString = "";
    switch (suit) {
      case CLUB:
        suitString = "♣";
        break;
      case DIAMOND:
        suitString = "♦";
        break;
      case HEART:
        suitString = "♥";
        break;
      case SPADE:
        suitString = "♠";
        break;
      default:
        // no other suits possible
    }

    String cardVal = "";
    switch (this.getValue()) {
      case 0:
        cardVal = "A";
        break;
      case 1:
        cardVal = "2";
        break;
      case 2:
        cardVal = "3";
        break;
      case 3:
        cardVal = "4";
        break;
      case 4:
        cardVal = "5";
        break;
      case 5:
        cardVal = "6";
        break;
      case 6:
        cardVal = "7";
        break;
      case 7:
        cardVal = "8";
        break;
      case 8:
        cardVal = "9";
        break;
      case 9:
        cardVal = "10";
        break;
      case 10:
        cardVal = "J";
        break;
      case 11:
        cardVal = "Q";
        break;
      case 12:
        cardVal = "K";
        break;
      default:
        throw new IllegalArgumentException();
    }
    return cardVal + suitString;
  }
}
