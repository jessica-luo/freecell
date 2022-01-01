package cs3500.freecell.model.hw02;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract base class for implementations of IPile of ICards. The only field is the pile.
 */
public abstract class APile implements IPile<ICard> {

  private final List<List<ICard>> pile;

  /**
   * Constructs an IPile with an empty list of list of ICards.
   */
  public APile() {
    this.pile = new ArrayList<>();
  }

  @Override
  public void removeLastCard(int pileNumber) {
    int lastIndex = this.getInnerPile(pileNumber).size() - 1;
    if (lastIndex < 0 || lastIndex >= this.getInnerPile(pileNumber).size()) {
      throw new IllegalArgumentException("illegal pile number");
    }
    ICard lastCard = this.getInnerPile(pileNumber).get(lastIndex);

    this.getInnerPile(pileNumber).remove(lastCard);
  }

  @Override
  public void genPile(int numPiles) {
    if (numPiles < 0) {
      throw new IllegalArgumentException("invalid number of piles");
    }
    for (int i = 0; i < numPiles; i++) {
      pile.add(new ArrayList<>());
    }
  }

  @Override
  public List<List<ICard>> getPile() {
    return this.pile;
  }

  @Override
  public List<ICard> getInnerPile(int index) {
    if (index < 0 || index >= this.pile.size()) {
      throw new IllegalArgumentException("illegal index");
    }
    return this.pile.get(index);
  }

  @Override
  public ICard getLastCard(int pileNum) {
    int pileSize = this.getInnerPile(pileNum).size();
    if (pileSize == 0) {
      throw new IllegalArgumentException("pile is empty");
    } else {
      return this.getInnerPile(pileNum).get(pileSize - 1);
    }
  }

  @Override
  public int numEmptyPiles() {
    int numEmpty = 0;
    for (List<ICard> p : this.getPile()) {
      if (p.size() == 0) {
        numEmpty++;
      }
    }
    return numEmpty;
  }
}
