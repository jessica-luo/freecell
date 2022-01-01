package cs3500.freecell.model.hw02;

import cs3500.freecell.model.PileType;
import java.util.List;

/**
 * Represents multiple piles of objects (T). Offers operations to add an object, remove the last
 * object in a specific pile, observe the last object in a specific pile, generate inner piles,
 * determine the pile type, observe the pile, and observe a specific inner pile.
 *
 * @param <T> the objects in the inner piles (in this project, cards in Freecell)
 */
public interface IPile<T> {

  /**
   * Adds the given object to the specified pile number.
   *
   * @param object  the object to be added
   * @param pileNum the index of the inner pile
   */
  void addCard(T object, int pileNum);

  /**
   * Removes the last card of the specified pile.
   *
   * @param pileNumber the index of the specified pile
   * @throws IllegalArgumentException when the pile is empty
   */
  void removeLastCard(int pileNumber);

  /**
   * Observes the last card of the specified pile.
   *
   * @param pileNum the index of the specified pile
   * @return the last card in the pile
   * @throws IllegalArgumentException when the pile is empty
   */
  T getLastCard(int pileNum);

  /**
   * Initializes the specified number of inner piles.
   *
   * @param numPiles the number of inner piles
   * @throws IllegalArgumentException when numPiles is less than 0
   */
  void genPile(int numPiles);

  /**
   * Observes the PileType of the pile.
   *
   * @return the pile type of the pile (foundation, open, or cascade)
   */
  PileType getPileType();

  /**
   * Observes the whole pile of piles.
   *
   * @return the list of lists of objects representing the pile.
   */
  List<List<ICard>> getPile();

  /**
   * Returns the inner pile of objects at the specified index.
   *
   * @param index the index of the inner pile
   * @return the inner pile
   * @throws IllegalArgumentException when the index is less than or equal to the size of the inner
   *                                  pile
   */
  List<ICard> getInnerPile(int index);

  /**
   * Returns the number of empty piles in this pile.
   *
   * @return an integer representing the number of empty piles
   */
  int numEmptyPiles();
}
