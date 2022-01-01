package cs3500.freecell.controller;

import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.PileType;
import cs3500.freecell.model.Utils;
import cs3500.freecell.model.hw02.ICard;
import cs3500.freecell.view.FreecellTextView;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * Represents a user input controller for a FreecellGame (single move and multi move). Has fields
 * with the game model, the readable and appendable (for user input and output), a boolean
 * representing whether the game has been quit prematurely, and the text view representation of the
 * game. Provides an operation to play the game using a user inputted deck and a specified number of
 * cascade and open piles, and a boolean representing whether the deck should be shuffled or not.
 */
public class SimpleFreecellController implements FreecellController<ICard> {

  private final FreecellModel<ICard> model;
  private final Readable rd;
  private final Appendable ap;
  private boolean quit;
  private final FreecellTextView view;

  /**
   * Constructs a simple freecell controller that can be used to play the game. The quit field is
   * initialized as false, and the user can input a model, readable, and appendable. The view field
   * is initialized using the model and the appendable.
   *
   * @param model represents the freecell game model
   * @param rd    is the readable for user input
   * @param ap    is the appendable for user output
   * @throws IllegalArgumentException when the model, readable, or appendable are null
   */
  public SimpleFreecellController(FreecellModel<ICard> model, Readable rd, Appendable ap)
      throws IllegalArgumentException {
    this.model = Utils.requireNonNull(model);
    this.rd = Utils.requireNonNull(rd);
    this.ap = Utils.requireNonNull(ap);
    this.quit = false;
    this.view = new FreecellTextView(this.model, this.ap);
  }

  @Override
  public void playGame(List deck, int numCascades, int numOpens, boolean shuffle)
      throws IllegalStateException, IllegalArgumentException {

    Utils.requireNonNull(deck);

    // start game
    try {
      model.startGame(deck, numCascades, numOpens, shuffle);
    } catch (IllegalArgumentException e) {
      write("Could not start game.");
      return;
    }

    // render the board
    try {
      this.view.renderBoard();
      write("\n");
    } catch (IOException e) {
      throw new IllegalStateException("failed to write to appendable");
    }

    // initialize scanner
    Scanner scanner = new Scanner(this.rd);

    // if the readable runs out
    if (!scanner.hasNext()) {
      throw new IllegalStateException("failed to read");
    }

    // read input for the source pile
    sourcePileInput(scanner);
  }

  /**
   * Analyzes the user input for the source pile. The only valid inputs are "O", "F", and "C" with
   * integers after, and "Q" to quit the game. Otherwise, prompts the user to input new sourcePile.
   *
   * @param scanner the scanner containing the readable
   */
  private void sourcePileInput(Scanner scanner) {
    // if quit is true, quit the game
    while (!this.quit && scanner.hasNext()) {
      String input = scanner.next().toLowerCase();

      switch (input.charAt(0)) {
        case 'q':
          write("Game quit prematurely.\n");
          this.quit = true;
          return;
        case 'o':
        case 'c':
        case 'f':
          String pileNum = input.substring(1);
          try {
            Integer.parseInt((pileNum));
            write("input card index\n");
            cardIndexInput(input, scanner);
          } catch (NumberFormatException | StringIndexOutOfBoundsException
              | IllegalStateException e) {
            write("Invalid source pile. Try again.\n");
          }
          break;
        default:
          write("Invalid source pile. Try again.\n");
          break;
      }
    }
  }

  /**
   * Analyzes the user input for the card index after a valid source pile is inputted. The only
   * valid inputs are integers, and "Q" to quit the game. Otherwise, prompts the user to input new
   * card index.
   *
   * @param sourcePile the previously entered sourcePile
   * @param scanner    the scanner containing the readable
   */
  private void cardIndexInput(String sourcePile, Scanner scanner) {
    String cardIndex = scanner.next().toLowerCase();

    if (cardIndex.equals("q")) {
      write("Game quit prematurely.\n");
      this.quit = true;
      return;
    }

    try {
      int cardIndexAsInt = Integer.parseInt(cardIndex);
      write("input destination pile\n");
      destPileInput(sourcePile, cardIndexAsInt, scanner);
    } catch (NumberFormatException | IllegalStateException e) {
      write("Invalid card index. Try again.\n");
      cardIndexInput(sourcePile, scanner);
    }
  }

  /**
   * Analyzes the user input for the destination pile after a valid source pile and card index are
   * inputted. The only valid inputs are "O", "F", and "C" with integers after, and "Q" to quit the
   * game. Otherwise, prompts the user to input new destination pile.
   *
   * @param sourcePile the previously entered sourcePile
   * @param cardIndex  the previously entered card index
   * @param scanner    the scanner containing the readable
   */
  private void destPileInput(String sourcePile, int cardIndex, Scanner scanner) {
    String destPile = scanner.next().toLowerCase();

    switch (destPile.charAt(0)) {
      case 'q':
        write("Game quit prematurely.\n");
        this.quit = true;
        return;
      case 'o':
      case 'c':
      case 'f':
        try {
          String pileNum = destPile.substring(1);
          Integer.parseInt((pileNum));
          tryMove(sourcePile, destPile, cardIndex);
          return;
        } catch (StringIndexOutOfBoundsException | NumberFormatException
            | IllegalStateException e) {
          write("Invalid destination pile. Try again.\n");
          destPileInput(sourcePile, cardIndex, scanner);
        }
        break;
      default:
        write("Invalid destination pile. Try again.\n");
        destPileInput(sourcePile, cardIndex, scanner);
        break;
    }

  }

  /**
   * Uses the valid user inputs for source pile, card index, and destination pile to attempt a move
   * in freecell. If the move is invalid, the user is prompted to enter a new source pile to begin a
   * new move.
   *
   * @param sourcePile the previously entered sourcePile
   * @param destPile   the previously entered destination pile
   * @param cardIndex  the previously entered card index
   */
  private void tryMove(String sourcePile, String destPile, int cardIndex) {
    try {
      PileType sourcePileType = pileTypeDeterminer(sourcePile.substring(0, 1));
      int sourcePileNum = Integer.parseInt(sourcePile.substring(1)) - 1;
      PileType destPileType = pileTypeDeterminer(destPile.substring(0, 1));
      int destPileNum = Integer.parseInt(destPile.substring(1)) - 1;
      model.move(sourcePileType, sourcePileNum, cardIndex - 1, destPileType, destPileNum);
      write("Moving " + sourcePile + " card number " + cardIndex + " to "
          + destPile + "\n"); // add more of a description here

      // text view of game
      FreecellTextView view = new FreecellTextView(model, this.ap);
      //view.toString();
      view.renderBoard();
      write("\n");
      // if the game is over
      if (model.isGameOver()) {
        write("Game over.\n");
        this.quit = true;
        return;
      }
    } catch (IndexOutOfBoundsException | IllegalArgumentException | IOException
        | IllegalStateException e) {
      write("Invalid move. Try again.\n");
    }
  }

  /**
   * Determines the PileType using user inputted letters "o", "f", and "c", or returns a null value
   * if not one of the three.
   *
   * @param pile the string representation of a pile
   * @return the PileType of the string representation
   */
  private PileType pileTypeDeterminer(String pile) {
    PileType pileType;
    switch (pile) {
      case "o":
        pileType = PileType.OPEN;
        break;
      case "f":
        pileType = PileType.FOUNDATION;
        break;
      case "c":
        pileType = PileType.CASCADE;
        break;
      default:
        pileType = null;
        break;
    }
    return pileType;
  }

  /**
   * Renders the string message given to the method to the view by appending it to the appendable.
   *
   * @param message the string message to be rendered
   */
  private void write(String message) {
    Utils.requireNonNull(message);

    try {
      view.renderMessage(message);
    } catch (IOException e) {
      throw new IllegalStateException("couldn't write to appendable");
    }
  }
}