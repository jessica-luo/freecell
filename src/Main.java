import cs3500.freecell.controller.FreecellController;
import cs3500.freecell.controller.SimpleFreecellController;
import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.hw02.ICard;
import cs3500.freecell.model.hw04.MultiMoveFreecellModel;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;

/**
 * Main method class for command line arguments.
 */
public class Main {

  /**
   * Command-line arguments are passed through the args parameter, which is an array of Strings.
   * Allows the user to play the FreecellGame using the controller.
   *
   * @param args array of string from user input
   */
  public static void main(String[] args) {
    //FreecellModel<ICard> model = new SimpleFreecellModel();
    FreecellModel<ICard> model = new MultiMoveFreecellModel();
    Appendable out = System.out;
    Readable in = new InputStreamReader(System.in);

    FreecellController controller = new SimpleFreecellController(model, in, out);
    List<ICard> deck = model.getDeck();
    Collections.reverse(deck);
    controller.playGame(deck, 4, 16, false);
  }
}

