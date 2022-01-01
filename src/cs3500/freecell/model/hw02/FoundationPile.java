package cs3500.freecell.model.hw02;

import cs3500.freecell.model.PileType;

/**
 * Represents piles that are foundation piles in the game of FreeCell.
 */
public class FoundationPile extends APile {

  private final PileType type;

  /**
   * Constructs a foundation pile with an empty pile and a PileType of Foundation.
   */
  public FoundationPile() {
    super();
    this.type = PileType.FOUNDATION;
  }

  @Override
  public void addCard(ICard card, int pileNum) {
    //
    if (pileNum < 0 || pileNum >= this.getPile().size()) {
      throw new IllegalArgumentException("pile number not found");
    }
    // if the size of the pile is 0
    if ((this.getInnerPile(pileNum).size() == 0 && card.getValue() == 0)) {
      this.getInnerPile(pileNum).add(card);
    } else if (this.getInnerPile(pileNum).size() > 0
        && this.getLastCard(pileNum).getValue() == card.getValue() - 1
        && this.getLastCard(pileNum).getSuit() == card.getSuit()) {
      this.getInnerPile(pileNum).add(card);
    } else {
      throw new IllegalArgumentException("invalid move");
    }
  }

  @Override
  public PileType getPileType() {
    return this.type;
  }
}
