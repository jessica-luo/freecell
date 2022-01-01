package cs3500.freecell.model.hw02;

import cs3500.freecell.model.PileType;

/**
 * Represents piles that are cascade piles in the game of FreeCell.
 */
public class CascadePile extends APile {

  private final PileType type;

  /**
   * Constructs a cascade pile with an empty pile and a PileType of Cascade.
   */
  public CascadePile() {
    super();
    this.type = PileType.CASCADE;
  }

  @Override
  public void addCard(ICard card, int pileNum) {
    if (pileNum < 0 || pileNum >= this.getPile().size()) {
      throw new IllegalArgumentException("pile number not found");
    }

    if (this.getInnerPile(pileNum).size() == 0
        || (this.getLastCard(pileNum).getValue() == card.getValue() + 1
        && this.getLastCard(pileNum).getColor() != card.getColor())) {
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
