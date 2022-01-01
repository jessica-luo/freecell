package cs3500.freecell.model.hw02;

import cs3500.freecell.model.PileType;

/**
 * Represents piles that are open piles in the game of FreeCell.
 */
public class OpenPile extends APile {

  private final PileType type;

  /**
   * Constructs a open pile with an empty pile and a PileType of Open.
   */
  public OpenPile() {
    super();
    this.type = PileType.OPEN;
  }

  @Override
  public void addCard(ICard card, int pileNum) {
    if (pileNum < 0 || pileNum >= this.getPile().size()) {
      throw new IllegalArgumentException("pile number not found");
    }

    if (this.getInnerPile(pileNum).size() > 0) {
      throw new IllegalArgumentException("move not possible");
    } else {
      this.getInnerPile(pileNum).add(card);
    }
  }

  @Override
  public PileType getPileType() {
    return this.type;
  }
}
