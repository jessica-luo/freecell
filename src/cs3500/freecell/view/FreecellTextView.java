package cs3500.freecell.view;

import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.Utils;
import java.io.IOException;

/**
 * Represents the String view of a FreeCell model/game. Has fields for the game model and the
 * appendable that renders the game. Includes operations to construct the freecell textview, as well
 * as to render the board, and any string message.
 */
public class FreecellTextView implements FreecellView {

  private final FreecellModel<?> model;
  private final Appendable ap;

  /**
   * Constructs the freecell view using the specified model.
   *
   * @param model the model of the freecell game
   */
  public FreecellTextView(FreecellModel<?> model) {
    Utils.requireNonNull(model);

    this.model = model;
    this.ap = System.out;
  }

  /**
   * Constructs the freecell view using the specified model and appendable.
   *
   * @param model the model of the freecell game
   * @param ap    the appendable that contains the game output
   */
  public FreecellTextView(FreecellModel<?> model, Appendable ap) {
    Utils.requireNonNull(model);
    Utils.requireNonNull(ap);

    this.model = model;
    this.ap = ap;
  }

  @Override
  public String toString() {
    StringBuilder board = new StringBuilder();
    if (this.model.getNumCascadePiles() < 4 || this.model.getNumOpenPiles() < 1) {
      return "";
    } else {
      board.append(this.foundationToString());
      board.append(this.openToString());
      board.append(this.cascadeToString());
      return board.toString();
    }
  }

  @Override
  public void renderBoard() throws IOException {
    this.ap.append(this.toString());
  }

  @Override
  public void renderMessage(String message) throws IOException {
    Utils.requireNonNull(message);

    this.ap.append(message);
  }

  /**
   * Turns the foundation piles into a string format.
   *
   * @return the foundation piles represented as a String
   */
  private String foundationToString() {
    StringBuilder pileString2 = new StringBuilder();
    for (int i = 0; i < 4; i++) {
      StringBuilder cards2 = new StringBuilder();
      String cards = "";
      for (int j = 0; j < model.getNumCardsInFoundationPile(i); j++) {
        cards2.append(" ");
        cards2.append(model.getFoundationCardAt(i, j).toString());
        cards2.append(",");
        cards = cards2.toString();
      }
      int cardLen = cards.length() - 1;
      if (cardLen >= 0) {
        pileString2.append("F");
        pileString2.append(i + 1);
        pileString2.append(":");
        pileString2.append(cards, 0, cardLen);
        pileString2.append("\n");
      } else {
        pileString2.append("F");
        pileString2.append(i + 1);
        pileString2.append(":");
        pileString2.append(cards);
        pileString2.append("\n");
      }
    }

    return pileString2.toString();
  }

  /**
   * Turns the open piles into a string format.
   *
   * @return the open piles represented as a String
   */
  private String openToString() {
    StringBuilder pileString2 = new StringBuilder();
    for (int i = 0; i < model.getNumOpenPiles(); i++) {
      StringBuilder cards2 = new StringBuilder();
      String cards = "";
      for (int j = 0; j < model.getNumCardsInOpenPile(i); j++) {
        cards2.append(" ");
        cards2.append(model.getOpenCardAt(i).toString());
        cards2.append(",");
        cards = cards2.toString();
      }
      int cardLen = cards.length() - 1;
      if (cardLen >= 0) {
        pileString2.append("O");
        pileString2.append(i + 1);
        pileString2.append(":");
        pileString2.append(cards, 0, cardLen);
        pileString2.append("\n");
      } else {
        pileString2.append("O");
        pileString2.append(i + 1);
        pileString2.append(":");
        pileString2.append(cards);
        pileString2.append("\n");
      }
    }

    return pileString2.toString();
  }

  /**
   * Turns the cascade piles into a string format.
   *
   * @return the cascade piles represented as a String
   */
  private String cascadeToString() {
    StringBuilder pileString2 = new StringBuilder();
    for (int i = 0; i < model.getNumCascadePiles(); i++) {
      String cards = "";
      for (int j = 0; j < model.getNumCardsInCascadePile(i); j++) {
        cards = cards.concat(" " + model.getCascadeCardAt(i, j).toString() + ",");
      }
      int cardLen = cards.length() - 1;
      if (cardLen >= 0) {
        pileString2.append("C");
        pileString2.append(i + 1);
        pileString2.append(":");
        pileString2.append(cards, 0, cardLen);
        pileString2.append("\n");
      } else {
        pileString2.append("C");
        pileString2.append(i + 1);
        pileString2.append(":");
        pileString2.append(cards);
        pileString2.append("\n");
      }
    }

    String cascades = pileString2.substring(0, pileString2.toString().length() - 1);

    return cascades;
  }
}
