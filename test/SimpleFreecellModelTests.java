import static org.junit.Assert.assertEquals;

import cs3500.freecell.model.PileType;
import cs3500.freecell.model.hw02.Card;
import cs3500.freecell.model.hw02.CardValue;
import cs3500.freecell.model.hw02.ICard;
import cs3500.freecell.model.hw02.SimpleFreecellModel;
import cs3500.freecell.model.hw02.Suit;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

/**
 * Tests for the SimpleFreecellModel class.
 */
public class SimpleFreecellModelTests {

  ICard card1 = new Card(CardValue.ACE, Suit.CLUB);
  List<ICard> deck = new SimpleFreecellModel().getDeck();
  List<ICard> deck2 = new ArrayList<>();
  List<ICard> deck3 = new SimpleFreecellModel().getDeck();
  SimpleFreecellModel game = new SimpleFreecellModel();

  @Test
  public void testGetDeck() {
    assertEquals(new SimpleFreecellModel().getDeck().toString(),
        new StringBuilder()
            .append("[A♣, A♦, A♥, A♠, 2♣, 2♦, 2♥, 2♠, 3♣, 3♦, 3♥, 3♠, 4♣, 4♦, 4♥, 4♠, 5♣, 5♦, ")
            .append("5♥, 5♠, 6♣, 6♦, 6♥, 6♠, 7♣, 7♦, 7♥, 7♠, 8♣, 8♦, 8♥, 8♠, 9♣, 9♦, 9♥, ")
            .append("9♠, 10♣, 10♦, 10♥, 10♠, J♣, J♦, J♥, J♠, Q♣, Q♦, Q♥, Q♠, K♣, K♦, K♥, K♠]")
            .toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartGameTooManyCardsInDeck() {
    deck.add(card1);
    new SimpleFreecellModel().startGame(deck, 8, 4, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartGameNullCardInDeck() {
    deck.add(null);
    new SimpleFreecellModel().startGame(deck, 8, 4, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartGameNot52CardsDeck() {
    deck.remove(0);
    new SimpleFreecellModel().startGame(deck, 8, 4, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartGame52CardsInDeckButDuplicateCard() {
    deck.remove(50);
    deck.add(card1);
    new SimpleFreecellModel().startGame(deck, 8, 4, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartGameEmptyDeck() {
    new SimpleFreecellModel().startGame(deck2, 8, 4, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartGameDeckIsNull() {
    new SimpleFreecellModel().startGame(null, 8, 4, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartGameLessThan4Cascade() {
    new SimpleFreecellModel().startGame(deck, 3, 4, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartGameLessThan1Open() {
    new SimpleFreecellModel().startGame(deck, 8, 0, false);
  }

  //deck3 is the unshuffled deck, deck is being shuffled after the game starts. make sure
  //the decks aren't the same after shuffling.
  @Test
  public void testStartGameDeckShuffleTrue() {
    assertEquals(deck.toString(), deck3.toString());
    new SimpleFreecellModel().startGame(deck, 8, 4, true);
    assertEquals(deck.toString().equals(deck3.toString()), false);
  }

  //deck3 is the unshuffled deck, deck is being shuffled after the game starts. make sure
  //the decks are the same after game starts
  @Test
  public void testStartGameDeckShuffleFalse() {
    assertEquals(deck.toString(), deck3.toString());
    new SimpleFreecellModel().startGame(deck, 8, 4, false);
    assertEquals(deck.toString(), deck3.toString());
  }

  @Test
  public void testStartGameCascadePileCleared() {
    game.startGame(deck, 8, 4, false);
    assertEquals(game.getNumCascadePiles(), 8);
    game.startGame(deck, 4, 4, false);
    assertEquals(game.getNumCascadePiles(), 4);
  }

  @Test
  public void testStartGameOpenPileCleared() {
    game.startGame(deck, 8, 4, false);
    assertEquals(game.getNumOpenPiles(), 4);
    game.startGame(deck, 4, 2, false);
    assertEquals(game.getNumOpenPiles(), 2);
  }

  @Test
  public void testStartGamePileGenerationCascade() {
    assertEquals(game.getNumCascadePiles(), -1);
    game.startGame(deck, 8, 4, false);
    assertEquals(game.getNumCascadePiles(), 8);
  }

  @Test
  public void testStartGamePileGenerationCascadeInnerPileEmpty() {
    game.startGame(deck, 8, 1, false);
    assertEquals(game.getNumOpenPiles(), 1);
    assertEquals(game.getNumCardsInOpenPile(0), 0);
  }

  @Test
  public void testStartGamePileGenerationOpen() {
    assertEquals(game.getNumOpenPiles(), -1);
    game.startGame(deck, 8, 4, false);
    assertEquals(game.getNumOpenPiles(), 4);
  }

  // tests that values are dealt round robin to the cascade piles
  @Test
  public void testStartGameDeckDealing() {
    game.startGame(deck, 8, 4, false);
    assertEquals(game.getCascadeCardAt(0, 0), deck.get(0));
    assertEquals(game.getCascadeCardAt(0, 1), deck.get(8));
    assertEquals(game.getCascadeCardAt(0, 2), deck.get(16));
    assertEquals(game.getCascadeCardAt(0, 3), deck.get(24));
    assertEquals(game.getCascadeCardAt(0, 4), deck.get(32));
    assertEquals(game.getCascadeCardAt(0, 5), deck.get(40));
    assertEquals(game.getCascadeCardAt(0, 6), deck.get(48));

    assertEquals(game.getCascadeCardAt(1, 0), deck.get(1));
    assertEquals(game.getCascadeCardAt(1, 1), deck.get(9));
    assertEquals(game.getCascadeCardAt(1, 2), deck.get(17));
    assertEquals(game.getCascadeCardAt(1, 3), deck.get(25));
    assertEquals(game.getCascadeCardAt(1, 4), deck.get(33));
    assertEquals(game.getCascadeCardAt(1, 5), deck.get(41));
    assertEquals(game.getCascadeCardAt(1, 6), deck.get(49));

    assertEquals(game.getCascadeCardAt(2, 0), deck.get(2));
    assertEquals(game.getCascadeCardAt(2, 1), deck.get(10));
    assertEquals(game.getCascadeCardAt(2, 2), deck.get(18));
    assertEquals(game.getCascadeCardAt(2, 3), deck.get(26));
    assertEquals(game.getCascadeCardAt(2, 4), deck.get(34));
    assertEquals(game.getCascadeCardAt(2, 5), deck.get(42));
    assertEquals(game.getCascadeCardAt(2, 6), deck.get(50));

    assertEquals(game.getCascadeCardAt(3, 0), deck.get(3));
    assertEquals(game.getCascadeCardAt(3, 1), deck.get(11));
    assertEquals(game.getCascadeCardAt(3, 2), deck.get(19));
    assertEquals(game.getCascadeCardAt(3, 3), deck.get(27));
    assertEquals(game.getCascadeCardAt(3, 4), deck.get(35));
    assertEquals(game.getCascadeCardAt(3, 5), deck.get(43));
    assertEquals(game.getCascadeCardAt(3, 6), deck.get(51));

    assertEquals(game.getCascadeCardAt(4, 0), deck.get(4));
    assertEquals(game.getCascadeCardAt(4, 1), deck.get(12));
    assertEquals(game.getCascadeCardAt(4, 2), deck.get(20));
    assertEquals(game.getCascadeCardAt(4, 3), deck.get(28));
    assertEquals(game.getCascadeCardAt(4, 4), deck.get(36));
    assertEquals(game.getCascadeCardAt(4, 5), deck.get(44));

    assertEquals(game.getCascadeCardAt(5, 0), deck.get(5));
    assertEquals(game.getCascadeCardAt(5, 1), deck.get(13));
    assertEquals(game.getCascadeCardAt(5, 2), deck.get(21));
    assertEquals(game.getCascadeCardAt(5, 3), deck.get(29));
    assertEquals(game.getCascadeCardAt(5, 4), deck.get(37));
    assertEquals(game.getCascadeCardAt(5, 5), deck.get(45));

    assertEquals(game.getCascadeCardAt(6, 0), deck.get(6));
    assertEquals(game.getCascadeCardAt(6, 1), deck.get(14));
    assertEquals(game.getCascadeCardAt(6, 2), deck.get(22));
    assertEquals(game.getCascadeCardAt(6, 3), deck.get(30));
    assertEquals(game.getCascadeCardAt(6, 4), deck.get(38));
    assertEquals(game.getCascadeCardAt(6, 5), deck.get(46));

    assertEquals(game.getCascadeCardAt(7, 0), deck.get(7));
    assertEquals(game.getCascadeCardAt(7, 1), deck.get(15));
    assertEquals(game.getCascadeCardAt(7, 2), deck.get(23));
    assertEquals(game.getCascadeCardAt(7, 3), deck.get(31));
    assertEquals(game.getCascadeCardAt(7, 4), deck.get(39));
    assertEquals(game.getCascadeCardAt(7, 5), deck.get(47));
  }

  @Test
  public void testIsGameOverFalse() {
    game.startGame(deck, 8, 4, false);
    assertEquals(game.isGameOver(), false);
  }

  @Test
  public void testIsGameOverTrue() {
    game.startGame(deck, 52, 1, false);
    game.move(PileType.CASCADE, 0, 0, PileType.FOUNDATION, 0);
    game.move(PileType.CASCADE, 4, 0, PileType.FOUNDATION, 0);
    game.move(PileType.CASCADE, 8, 0, PileType.FOUNDATION, 0);
    game.move(PileType.CASCADE, 12, 0, PileType.FOUNDATION, 0);
    game.move(PileType.CASCADE, 16, 0, PileType.FOUNDATION, 0);
    game.move(PileType.CASCADE, 20, 0, PileType.FOUNDATION, 0);
    game.move(PileType.CASCADE, 24, 0, PileType.FOUNDATION, 0);
    game.move(PileType.CASCADE, 28, 0, PileType.FOUNDATION, 0);
    game.move(PileType.CASCADE, 32, 0, PileType.FOUNDATION, 0);
    game.move(PileType.CASCADE, 36, 0, PileType.FOUNDATION, 0);
    game.move(PileType.CASCADE, 40, 0, PileType.FOUNDATION, 0);
    game.move(PileType.CASCADE, 44, 0, PileType.FOUNDATION, 0);
    game.move(PileType.CASCADE, 48, 0, PileType.FOUNDATION, 0);

    game.move(PileType.CASCADE, 1, 0, PileType.FOUNDATION, 1);
    game.move(PileType.CASCADE, 5, 0, PileType.FOUNDATION, 1);
    game.move(PileType.CASCADE, 9, 0, PileType.FOUNDATION, 1);
    game.move(PileType.CASCADE, 13, 0, PileType.FOUNDATION, 1);
    game.move(PileType.CASCADE, 17, 0, PileType.FOUNDATION, 1);
    game.move(PileType.CASCADE, 21, 0, PileType.FOUNDATION, 1);
    game.move(PileType.CASCADE, 25, 0, PileType.FOUNDATION, 1);
    game.move(PileType.CASCADE, 29, 0, PileType.FOUNDATION, 1);
    game.move(PileType.CASCADE, 33, 0, PileType.FOUNDATION, 1);
    game.move(PileType.CASCADE, 37, 0, PileType.FOUNDATION, 1);
    game.move(PileType.CASCADE, 41, 0, PileType.FOUNDATION, 1);
    game.move(PileType.CASCADE, 45, 0, PileType.FOUNDATION, 1);
    game.move(PileType.CASCADE, 49, 0, PileType.FOUNDATION, 1);

    game.move(PileType.CASCADE, 2, 0, PileType.FOUNDATION, 2);
    game.move(PileType.CASCADE, 6, 0, PileType.FOUNDATION, 2);
    game.move(PileType.CASCADE, 10, 0, PileType.FOUNDATION, 2);
    game.move(PileType.CASCADE, 14, 0, PileType.FOUNDATION, 2);
    game.move(PileType.CASCADE, 18, 0, PileType.FOUNDATION, 2);
    game.move(PileType.CASCADE, 22, 0, PileType.FOUNDATION, 2);
    game.move(PileType.CASCADE, 26, 0, PileType.FOUNDATION, 2);
    game.move(PileType.CASCADE, 30, 0, PileType.FOUNDATION, 2);
    game.move(PileType.CASCADE, 34, 0, PileType.FOUNDATION, 2);
    game.move(PileType.CASCADE, 38, 0, PileType.FOUNDATION, 2);
    game.move(PileType.CASCADE, 42, 0, PileType.FOUNDATION, 2);
    game.move(PileType.CASCADE, 46, 0, PileType.FOUNDATION, 2);
    game.move(PileType.CASCADE, 50, 0, PileType.FOUNDATION, 2);

    game.move(PileType.CASCADE, 3, 0, PileType.FOUNDATION, 3);
    game.move(PileType.CASCADE, 7, 0, PileType.FOUNDATION, 3);
    game.move(PileType.CASCADE, 11, 0, PileType.FOUNDATION, 3);
    game.move(PileType.CASCADE, 15, 0, PileType.FOUNDATION, 3);
    game.move(PileType.CASCADE, 19, 0, PileType.FOUNDATION, 3);
    game.move(PileType.CASCADE, 23, 0, PileType.FOUNDATION, 3);
    game.move(PileType.CASCADE, 27, 0, PileType.FOUNDATION, 3);
    game.move(PileType.CASCADE, 31, 0, PileType.FOUNDATION, 3);
    game.move(PileType.CASCADE, 35, 0, PileType.FOUNDATION, 3);
    game.move(PileType.CASCADE, 39, 0, PileType.FOUNDATION, 3);
    game.move(PileType.CASCADE, 43, 0, PileType.FOUNDATION, 3);
    game.move(PileType.CASCADE, 47, 0, PileType.FOUNDATION, 3);
    game.move(PileType.CASCADE, 51, 0, PileType.FOUNDATION, 3);

    assertEquals(game.isGameOver(), true);
  }

  @Test
  public void testGetNumCardsInFoundationPile() {
    game.startGame(deck, 52, 1, false);
    game.move(PileType.CASCADE, 0, 0, PileType.FOUNDATION, 0);
    assertEquals(game.getNumCardsInFoundationPile(0), 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetNumCardsInFoundationPileNegativeIndex() {
    game.startGame(deck, 52, 1, false);
    game.move(PileType.CASCADE, 0, 0, PileType.FOUNDATION, 0);
    game.getNumCardsInFoundationPile(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetNumCardsInFoundationPileTooLargeIndex() {
    game.startGame(deck, 52, 1, false);
    game.getNumCardsInFoundationPile(5);
  }

  @Test(expected = IllegalStateException.class)
  public void testGetNumCardsInFoundationPileGameNotStarted() {
    game.getNumCardsInFoundationPile(0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetNumCardsInCascadePileNegativeIndex() {
    game.startGame(deck, 52, 1, false);
    game.getNumCardsInCascadePile(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetNumCardsInCascadePileTooLargeIndex() {
    game.startGame(deck, 9, 1, false);
    game.getNumCardsInCascadePile(12);
  }

  @Test(expected = IllegalStateException.class)
  public void testGetNumCardsInCascadePileGameNotStarted() {
    game.getNumCardsInCascadePile(0);
  }

  @Test
  public void testGetNumCardsInCascadePile() {
    game.startGame(deck, 52, 1, false);
    assertEquals(game.getNumCardsInCascadePile(0), 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetNumCardsInOpenPileNegativeIndex() {
    game.startGame(deck, 52, 1, false);
    game.getNumCardsInOpenPile(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetNumCardsInOpenPileTooLargeIndex() {
    game.startGame(deck, 9, 1, false);
    game.getNumCardsInOpenPile(12);
  }

  @Test(expected = IllegalStateException.class)
  public void testGetNumCardsInOpenPileGameNotStarted() {
    game.getNumCardsInOpenPile(0);
  }

  @Test
  public void testGetNumCardsInOpenPile() {
    game.startGame(deck, 52, 1, false);
    assertEquals(game.getNumCardsInOpenPile(0), 0);
    game.move(PileType.CASCADE, 0, 0, PileType.OPEN, 0);
    assertEquals(game.getNumCardsInOpenPile(0), 1);
  }

  @Test
  public void testGetNumCascadePiles() {
    game.startGame(deck, 52, 1, false);
    assertEquals(game.getNumCascadePiles(), 52);
  }

  @Test
  public void testGetNumCascadePilesNoPiles() {
    assertEquals(game.getNumCascadePiles(), -1);
  }

  @Test
  public void testGetNumOpenPiles() {
    game.startGame(deck, 52, 1, false);
    assertEquals(game.getNumOpenPiles(), 1);
  }

  @Test
  public void testGetNumOpenPilesNoPiles() {
    assertEquals(game.getNumOpenPiles(), -1);
  }

  @Test
  public void testGetFoundationCardAt() {
    game.startGame(deck, 52, 1, false);
    game.move(PileType.CASCADE, 0, 0, PileType.FOUNDATION, 0);
    assertEquals(game.getFoundationCardAt(0, 0).toString(), card1.toString());
  }

  @Test(expected = IllegalStateException.class)
  public void testGetFoundationCardAtGameNotStarted() {
    game.getFoundationCardAt(0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetFoundationCardAtPileIndexTooLarge() {
    game.startGame(deck, 52, 1, false);
    game.getFoundationCardAt(5, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetFoundationCardAtPileIndexNegative() {
    game.startGame(deck, 52, 1, false);
    game.getFoundationCardAt(-5, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetFoundationCardAtCardIndexTooLarge() {
    game.startGame(deck, 52, 1, false);
    game.getFoundationCardAt(0, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetFoundationCardAtCardIndexNegative() {
    game.startGame(deck, 52, 1, false);
    game.getFoundationCardAt(0, -3);
  }

  @Test
  public void testGetCascadeCardAt() {
    game.startGame(deck, 52, 1, false);
    assertEquals(game.getCascadeCardAt(0, 0).toString(), card1.toString());
  }

  @Test(expected = IllegalStateException.class)
  public void testGetCascadeCardAtGameNotStarted() {
    game.getCascadeCardAt(0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCascadeCardAtPileIndexTooLarge() {
    game.startGame(deck, 52, 1, false);
    game.getCascadeCardAt(55, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCascadeCardAtPileIndexNegative() {
    game.startGame(deck, 52, 1, false);
    game.getCascadeCardAt(-5, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCascadeCardAtCardIndexTooLarge() {
    game.startGame(deck, 52, 1, false);
    game.getCascadeCardAt(0, 8);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCascadeCardAtCardIndexNegative() {
    game.startGame(deck, 52, 1, false);
    game.getCascadeCardAt(0, -3);
  }


  @Test
  public void testGetOpenCardAt() {
    game.startGame(deck, 52, 1, false);
    game.move(PileType.CASCADE, 0, 0, PileType.OPEN, 0);
    assertEquals(game.getOpenCardAt(0).toString(), card1.toString());
  }

  @Test(expected = IllegalStateException.class)
  public void testGetOpenCardAtGameNotStarted() {
    game.getOpenCardAt(0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetOpenCardAtPileIndexTooLarge() {
    game.startGame(deck, 52, 1, false);
    game.getOpenCardAt(1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetOpenCardAtPileIndexNegative() {
    game.startGame(deck, 52, 1, false);
    game.getOpenCardAt(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetOpenCardAtCardIndexTooLarge() {
    game.startGame(deck, 52, 1, false);
    game.getOpenCardAt(1);
  }

  @Test
  public void testGetOpenCardAtPileEmpty() {
    game.startGame(deck, 52, 1, false);
    assertEquals(game.getOpenCardAt(0), null);
  }

  @Test(expected = IllegalStateException.class)
  public void testMoveGameNotStarted() {
    game.move(PileType.CASCADE, 0, 0, PileType.OPEN, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveFromFoundation() {
    game.startGame(deck, 52, 1, false);
    game.move(PileType.FOUNDATION, 0, 0, PileType.OPEN, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveSourcePileNumberTooBig() {
    game.startGame(deck, 52, 1, false);
    game.move(PileType.CASCADE, 53, 0, PileType.OPEN, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveDestPileNumberTooBig() {
    game.startGame(deck, 52, 1, false);
    game.move(PileType.CASCADE, 3, 0, PileType.OPEN, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveDestPileNumberNegative() {
    game.startGame(deck, 52, 1, false);
    game.move(PileType.CASCADE, 3, 0, PileType.OPEN, -2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveSourcePileNumberNegative() {
    game.startGame(deck, 52, 1, false);
    game.move(PileType.CASCADE, -2, 0, PileType.OPEN, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveCardIndexNotLastCardInPile() {
    game.startGame(deck, 8, 1, false);
    game.move(PileType.CASCADE, 2, 0, PileType.OPEN, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveCardIndexisZeroAndSourcePileLengthZero() {
    game.startGame(deck, 8, 4, false);
    game.move(PileType.OPEN, 2, 0, PileType.OPEN, 0);
  }

  @Test
  public void testMoveToFoundationFromCascadeWithCardRemovedFromSource() {
    game.startGame(deck, 52, 1, false);
    assertEquals(game.getCascadeCardAt(0, 0).toString(), card1.toString());
    assertEquals(game.getNumCardsInCascadePile(0), 1);
    game.move(PileType.CASCADE, 0, 0, PileType.FOUNDATION, 0);
    assertEquals(game.getFoundationCardAt(0, 0).toString(), card1.toString());
    assertEquals(game.getNumCardsInCascadePile(0), 0);
  }

  @Test
  public void testMoveToFoundationFromOpenWithCardRemovedFromSource() {
    game.startGame(deck, 52, 1, false);
    assertEquals(game.getCascadeCardAt(0, 0).toString(), card1.toString());
    assertEquals(game.getNumCardsInCascadePile(0), 1);
    game.move(PileType.CASCADE, 0, 0, PileType.OPEN, 0);
    assertEquals(game.getOpenCardAt(0).toString(), card1.toString());
    assertEquals(game.getNumCardsInCascadePile(0), 0);
    assertEquals(game.getNumCardsInOpenPile(0), 1);
    game.move(PileType.OPEN, 0, 0, PileType.FOUNDATION, 0);
    assertEquals(game.getNumCardsInOpenPile(0), 0);
    assertEquals(game.getFoundationCardAt(0, 0).toString(), card1.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveCardToEmptyFoundationNotAce() {
    game.startGame(deck, 52, 4, false);
    game.move(PileType.CASCADE, 6, 0, PileType.FOUNDATION, 0);
  }

  @Test
  public void testMoveToFoundationInOrder() {
    game.startGame(deck, 52, 1, false);
    assertEquals(game.getCascadeCardAt(0, 0).toString(), card1.toString());
    game.move(PileType.CASCADE, 0, 0, PileType.OPEN, 0);
    assertEquals(game.getOpenCardAt(0).toString(), card1.toString());
    game.move(PileType.OPEN, 0, 0, PileType.FOUNDATION, 0);
    assertEquals(game.getFoundationCardAt(0, 0).toString(), card1.toString());
    game.move(PileType.CASCADE, 4, 0, PileType.FOUNDATION, 0);
    assertEquals(game.getFoundationCardAt(0, 1).toString(), "2♣");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveToFoundationInOrderNotCorrectSuit() {
    game.startGame(deck, 52, 1, false);
    game.move(PileType.CASCADE, 0, 0, PileType.OPEN, 0);
    game.move(PileType.OPEN, 0, 0, PileType.FOUNDATION, 0);
    game.move(PileType.CASCADE, 5, 0, PileType.FOUNDATION, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveToFoundationNotInOrder() {
    game.startGame(deck, 52, 1, false);
    game.move(PileType.CASCADE, 0, 0, PileType.OPEN, 0);
    game.move(PileType.OPEN, 0, 0, PileType.FOUNDATION, 0);
    game.move(PileType.CASCADE, 8, 0, PileType.FOUNDATION, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveCascadeToOpenButOpenIsFull() {
    game.startGame(deck, 52, 1, false);
    game.move(PileType.CASCADE, 0, 0, PileType.OPEN, 0);
    game.move(PileType.CASCADE, 1, 0, PileType.OPEN, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveOpenToCascadeButWrongValue() {
    game.startGame(deck, 52, 1, false);
    game.move(PileType.CASCADE, 0, 0, PileType.OPEN, 0);
    game.move(PileType.OPEN, 0, 0, PileType.CASCADE, 50);
  }

  @Test
  public void testMoveCascadeToOpen() {
    game.startGame(deck, 52, 1, false);
    assertEquals(game.getCascadeCardAt(0, 0).toString(), card1.toString());
    assertEquals(game.getNumCardsInCascadePile(0), 1);
    game.move(PileType.CASCADE, 0, 0, PileType.OPEN, 0);
    assertEquals(game.getOpenCardAt(0).toString(), card1.toString());
    assertEquals(game.getNumCardsInCascadePile(0), 0);
    assertEquals(game.getNumCardsInOpenPile(0), 1);
  }

  @Test
  public void testMoveOpenToCascade() {
    game.startGame(deck, 52, 1, false);
    assertEquals(game.getCascadeCardAt(0, 0).toString(), card1.toString());
    assertEquals(game.getNumCardsInCascadePile(0), 1);
    game.move(PileType.CASCADE, 0, 0, PileType.OPEN, 0);
    assertEquals(game.getOpenCardAt(0).toString(), card1.toString());
    assertEquals(game.getNumCardsInCascadePile(0), 0);
    assertEquals(game.getNumCardsInOpenPile(0), 1);
    game.move(PileType.OPEN, 0, 0, PileType.CASCADE, 5);
    assertEquals(game.getNumCardsInCascadePile(5), 2);
  }
}
