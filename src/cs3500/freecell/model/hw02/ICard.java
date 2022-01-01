package cs3500.freecell.model.hw02;

/**
 * Represents cards in for cards games. Includes operations to observe the suit, observe the value
 * of the card, as well as an operation that determines the color of the card based on the suit, and
 * an operation that represents the card as a String.
 */
public interface ICard {

  /**
   * Observes the numeric value of this card.
   *
   * @return the numeric value of this card.
   */
  int getValue();

  /**
   * Observes the suit of this card.
   *
   * @return the suit of this card
   */
  Suit getSuit();

  /**
   * Determines the color of this card based on the suit (spades and clubs are black, diamonds and
   * hearts are red).
   *
   * @return the color of the card
   */
  CardColor getColor();

  /**
   * Renders the card as a String.
   *
   * @return the string representation of the card
   */
  String toString();
}
