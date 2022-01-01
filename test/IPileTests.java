import cs3500.freecell.model.PileType;
import cs3500.freecell.model.hw02.Card;
import cs3500.freecell.model.hw02.CardValue;
import cs3500.freecell.model.hw02.CascadePile;
import cs3500.freecell.model.hw02.FoundationPile;
import cs3500.freecell.model.hw02.ICard;
import cs3500.freecell.model.hw02.IPile;
import cs3500.freecell.model.hw02.OpenPile;
import cs3500.freecell.model.hw02.Suit;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for IPile and all classes that implement IPile.
 */
public class IPileTests {

  ICard card1 = new Card(CardValue.ACE, Suit.CLUB);
  ICard card2 = new Card(CardValue.TWO, Suit.CLUB);
  ICard card3 = new Card(CardValue.THREE, Suit.CLUB);
  ICard card4 = new Card(CardValue.TWO, Suit.HEART);
  ICard card5 = new Card(CardValue.THREE, Suit.HEART);
  IPile cascadePile = new CascadePile();
  IPile foundationPile = new FoundationPile();
  IPile openPile = new OpenPile();

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveCardWhenPileNumberIsOutOfBounds() {
    new OpenPile().removeLastCard(1);
  }

  @Test
  public void testRemoveLastCardCascadePile() {
    cascadePile.genPile(5);
    cascadePile.addCard(card1, 0);
    assertEquals(cascadePile.getInnerPile(0).size(), 1);
    cascadePile.removeLastCard(0);
    assertEquals(cascadePile.getInnerPile(0).size(), 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveCardWhenPileNumberIsNegative() {
    new FoundationPile().removeLastCard(-1);
  }

  @Test
  public void testRemoveLastCardOpenPile() {
    openPile.genPile(5);
    openPile.addCard(card1, 0);
    assertEquals(openPile.getInnerPile(0).size(), 1);
    openPile.removeLastCard(0);
    assertEquals(openPile.getInnerPile(0).size(), 0);
  }

  @Test
  public void testRemoveLastCardFoundationPile() {
    foundationPile.genPile(5);
    foundationPile.addCard(card1, 1);
    foundationPile.addCard(card2, 1);
    foundationPile.addCard(card3, 1);
    assertEquals(foundationPile.getInnerPile(1).size(), 3);
    foundationPile.removeLastCard(1);
    assertEquals(foundationPile.getInnerPile(1).size(), 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGenPilesNegativePiles() {
    new FoundationPile().genPile(-1);
  }

  @Test
  public void testGenPilesCascade() {
    assertEquals(cascadePile.getPile().size(), 0);
    cascadePile.genPile(5);
    assertEquals(cascadePile.getPile().size(), 5);
  }

  @Test
  public void testGenPilesFoundation() {
    assertEquals(foundationPile.getPile().size(), 0);
    foundationPile.genPile(4);
    assertEquals(foundationPile.getPile().size(), 4);
  }

  @Test
  public void testGenPilesOpen() {
    assertEquals(openPile.getPile().size(), 0);
    openPile.genPile(1);
    assertEquals(openPile.getPile().size(), 1);
  }

  @Test
  public void testGetPileCascade() {
    assertEquals(cascadePile.getPile().toString(), "[]");
    cascadePile.genPile(4);
    assertEquals(cascadePile.getPile().toString(), "[[], [], [], []]");
  }

  @Test
  public void testGetPileOpen() {
    assertEquals(openPile.getPile().toString(), "[]");
    openPile.genPile(1);
    assertEquals(openPile.getPile().toString(), "[[]]");
  }

  @Test
  public void testGetPileFoundation() {
    assertEquals(foundationPile.getPile().toString(), "[]");
    foundationPile.genPile(4);
    assertEquals(foundationPile.getPile().toString(), "[[], [], [], []]");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetInnerPileIndexOutOfBound() {
    cascadePile.getInnerPile(2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetInnerNegativeInput() {
    cascadePile.getInnerPile(-2);
  }

  @Test
  public void testGetInnerPileFoundation() {
    foundationPile.genPile(4);
    assertEquals(foundationPile.getInnerPile(0).toString(), "[]");
  }

  @Test
  public void testGetInnerPileCascadeNonEmptyInnerPile() {
    cascadePile.genPile(4);
    cascadePile.addCard(card4, 0);
    cascadePile.addCard(card1, 0);
    cascadePile.addCard(card2, 1);
    assertEquals(cascadePile.getInnerPile(0).toString(), "[2♥, A♣]");
  }

  @Test
  public void testGetInnerPileOpenNonEmptyInnerPile() {
    openPile.genPile(4);
    openPile.addCard(card1, 0);
    assertEquals(openPile.getInnerPile(0).toString(), "[A♣]");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetLastCardEmptyPile() {
    cascadePile.getLastCard(2);
  }

  @Test
  public void testGetLastCardOpenNonEmpty() {
    openPile.genPile(4);
    openPile.addCard(card1, 0);
    assertEquals(openPile.getLastCard(0).toString(), "A♣");
  }

  @Test
  public void testGetLastCardFoundationNonEmptyMultiple() {
    foundationPile.genPile(4);
    foundationPile.addCard(card1, 0);
    foundationPile.addCard(card2, 0);
    assertEquals(foundationPile.getLastCard(0).toString(), "2♣");
  }

  @Test
  public void testGetPileTypeFoundation() {
    assertEquals(foundationPile.getPileType(), PileType.FOUNDATION);
  }

  @Test
  public void testGetPileTypeCascade() {
    assertEquals(cascadePile.getPileType(), PileType.CASCADE);
  }

  @Test
  public void testGetPileTypeOpen() {
    assertEquals(openPile.getPileType(), PileType.OPEN);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddCardFoundationNegativePileNum() {
    foundationPile.addCard(card1, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddCardFoundationPileNumTooLarge() {
    foundationPile.addCard(card1, 2);
  }

  @Test
  public void testAddCardFoundationPileEmptyAceAndTwo() {
    foundationPile.genPile(4);
    foundationPile.addCard(card1, 0);
    assertEquals(foundationPile.getInnerPile(0).toString(), "[A♣]");
    foundationPile.addCard(card2, 0);
    assertEquals(foundationPile.getInnerPile(0).toString(), "[A♣, 2♣]");
  }

  // invalid move (non ace to empty foundation pile)
  @Test(expected = IllegalArgumentException.class)
  public void testAddCardFoundationPileEmptyNonAce() {
    foundationPile.genPile(4);
    foundationPile.addCard(card2, 0);
  }

  // invalid move (add 3 to foundation pile with ace)
  @Test(expected = IllegalArgumentException.class)
  public void testAddCardFoundationPileAdd3ToAce() {
    foundationPile.genPile(4);
    foundationPile.addCard(card1, 0);
    foundationPile.addCard(card3, 0);
  }

  // invalid move (add 2 of heart to foundation pile with ace of clubs)
  @Test(expected = IllegalArgumentException.class)
  public void testAddCardFoundationPileCorrectValWrongSuit() {
    foundationPile.genPile(4);
    foundationPile.addCard(card1, 0);
    foundationPile.addCard(card5, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddCardCascadeNegativePileNum() {
    cascadePile.genPile(4);
    cascadePile.addCard(card1, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddCardCascadePileNumTooBig() {
    cascadePile.addCard(card1, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddCardCascadeInvalidMove() {
    cascadePile.genPile(4);
    cascadePile.addCard(card1, 2);
    cascadePile.addCard(card3, 2);
  }

  // add two of hearts to empty pile then an ace of clubs
  @Test
  public void testAddCardCascadeValidMoves() {
    cascadePile.genPile(4);
    cascadePile.addCard(card4, 2);
    cascadePile.addCard(card1, 2);
    assertEquals(cascadePile.getInnerPile(2).toString(), "[2♥, A♣]");
  }

  // colors are the same
  @Test(expected = IllegalArgumentException.class)
  public void testAddCardCascadeInvalidMoveColorsSame() {
    cascadePile.genPile(4);
    cascadePile.addCard(card2, 2);
    cascadePile.addCard(card1, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddCardOpenNegativePileNum() {
    openPile.genPile(4);
    openPile.addCard(card2, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddCardOpenTooBigPileNum() {
    openPile.genPile(4);
    openPile.addCard(card2, 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddCardOpenAlreadyOccupied() {
    openPile.genPile(4);
    openPile.addCard(card2, 0);
    openPile.addCard(card3, 0);
  }

  @Test
  public void testAddCardOpenUnoccupied() {
    openPile.genPile(4);
    openPile.addCard(card2, 0);
    assertEquals(openPile.getInnerPile(0).toString(), "[2♣]");
  }
}