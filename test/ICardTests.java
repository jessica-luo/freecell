import cs3500.freecell.model.hw02.Card;
import cs3500.freecell.model.hw02.CardColor;
import cs3500.freecell.model.hw02.CardValue;
import cs3500.freecell.model.hw02.Suit;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for ICard and all classes that implement ICard.
 */
public class ICardTests {

  @Test
  public void toStringClub() {
    assertEquals(new Card(CardValue.KING, Suit.CLUB).toString(),
        "K♣");
  }

  @Test
  public void toStringDiamond() {
    assertEquals(new Card(CardValue.KING, Suit.DIAMOND).toString(),
        "K♦");
  }

  @Test
  public void toStringHeart() {
    assertEquals(new Card(CardValue.KING, Suit.HEART).toString(),
        "K♥");
  }

  @Test
  public void toStringSpade() {
    assertEquals(new Card(CardValue.KING, Suit.SPADE).toString(),
        "K♠");
  }

  @Test
  public void toStringKing() {
    assertEquals(new Card(CardValue.KING, Suit.SPADE).toString(),
        "K♠");
  }

  @Test
  public void toStringQueen() {
    assertEquals(new Card(CardValue.QUEEN, Suit.SPADE).toString(),
        "Q♠");
  }

  @Test
  public void toStringJack() {
    assertEquals(new Card(CardValue.JACK, Suit.SPADE).toString(),
        "J♠");
  }

  @Test
  public void toStringTen() {
    assertEquals(new Card(CardValue.TEN, Suit.SPADE).toString(),
        "10♠");
  }

  @Test
  public void toStringNine() {
    assertEquals(new Card(CardValue.NINE, Suit.SPADE).toString(),
        "9♠");
  }

  @Test
  public void toStringEight() {
    assertEquals(new Card(CardValue.EIGHT, Suit.SPADE).toString(),
        "8♠");
  }

  @Test
  public void toStringSeven() {
    assertEquals(new Card(CardValue.SEVEN, Suit.SPADE).toString(),
        "7♠");
  }

  @Test
  public void toStringSix() {
    assertEquals(new Card(CardValue.SIX, Suit.SPADE).toString(),
        "6♠");
  }

  @Test
  public void toStringFive() {
    assertEquals(new Card(CardValue.FIVE, Suit.SPADE).toString(),
        "5♠");
  }

  @Test
  public void toStringFour() {
    assertEquals(new Card(CardValue.FOUR, Suit.SPADE).toString(),
        "4♠");
  }

  @Test
  public void toStringThree() {
    assertEquals(new Card(CardValue.THREE, Suit.SPADE).toString(),
        "3♠");
  }

  @Test
  public void toStringTwo() {
    assertEquals(new Card(CardValue.TWO, Suit.SPADE).toString(),
        "2♠");
  }

  @Test
  public void toStringAce() {
    assertEquals(new Card(CardValue.ACE, Suit.SPADE).toString(),
        "A♠");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullSuitConstructorException() {
    new Card(CardValue.ACE, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullIntValueConstructorException() {
    new Card(null, Suit.CLUB);
  }


  @Test
  public void testConstructorInitialization() {
    assertEquals(new Card(CardValue.ACE, Suit.SPADE).getSuit(), Suit.SPADE);
    assertEquals(new Card(CardValue.ACE, Suit.SPADE).getValue(), 0);
  }

  @Test
  public void getValueAce() {
    assertEquals(new Card(CardValue.ACE, Suit.SPADE).getValue(),
        0);
  }

  @Test
  public void getValueTwo() {
    assertEquals(new Card(CardValue.TWO, Suit.SPADE).getValue(),
        1);
  }

  @Test
  public void getValueThree() {
    assertEquals(new Card(CardValue.THREE, Suit.SPADE).getValue(),
        2);
  }

  @Test
  public void getValueFour() {
    assertEquals(new Card(CardValue.FOUR, Suit.SPADE).getValue(),
        3);
  }

  @Test
  public void getValueFive() {
    assertEquals(new Card(CardValue.FIVE, Suit.SPADE).getValue(),
        4);
  }

  @Test
  public void getValueSix() {
    assertEquals(new Card(CardValue.SIX, Suit.SPADE).getValue(),
        5);
  }

  @Test
  public void getValueSeven() {
    assertEquals(new Card(CardValue.SEVEN, Suit.SPADE).getValue(),
        6);
  }

  @Test
  public void getValueEight() {
    assertEquals(new Card(CardValue.EIGHT, Suit.SPADE).getValue(),
        7);
  }

  @Test
  public void getValueNine() {
    assertEquals(new Card(CardValue.NINE, Suit.SPADE).getValue(),
        8);
  }

  @Test
  public void getValueTen() {
    assertEquals(new Card(CardValue.TEN, Suit.SPADE).getValue(),
        9);
  }

  @Test
  public void getValueJack() {
    assertEquals(new Card(CardValue.JACK, Suit.SPADE).getValue(),
        10);
  }

  @Test
  public void getValueQueen() {
    assertEquals(new Card(CardValue.QUEEN, Suit.SPADE).getValue(),
        11);
  }

  @Test
  public void getValueKing() {
    assertEquals(new Card(CardValue.KING, Suit.SPADE).getValue(),
        12);
  }

  @Test
  public void getSuitSpade() {
    assertEquals(new Card(CardValue.KING, Suit.SPADE).getSuit(),
        Suit.SPADE);
  }

  @Test
  public void getSuitClub() {
    assertEquals(new Card(CardValue.KING, Suit.CLUB).getSuit(),
        Suit.CLUB);
  }

  @Test
  public void getSuitHeart() {
    assertEquals(new Card(CardValue.KING, Suit.HEART).getSuit(),
        Suit.HEART);
  }

  @Test
  public void getSuitDiamond() {
    assertEquals(new Card(CardValue.KING, Suit.DIAMOND).getSuit(),
        Suit.DIAMOND);
  }

  @Test
  public void getColorDiamond() {
    assertEquals(new Card(CardValue.KING, Suit.DIAMOND).getColor(),
        CardColor.RED);
  }

  @Test
  public void getColorHeart() {
    assertEquals(new Card(CardValue.KING, Suit.HEART).getColor(),
        CardColor.RED);
  }

  @Test
  public void getColorSpade() {
    assertEquals(new Card(CardValue.KING, Suit.SPADE).getColor(),
        CardColor.BLACK);
  }

  @Test
  public void getColorClub() {
    assertEquals(new Card(CardValue.KING, Suit.CLUB).getColor(),
        CardColor.BLACK);
  }

  @Test
  public void testEqualsSameCard() {
    assertEquals(new Card(CardValue.KING, Suit.CLUB).equals(new Card(CardValue.KING, Suit.CLUB)),
        true);
  }

  @Test
  public void testEqualsDifferentValSameSuit() {
    assertEquals(new Card(CardValue.QUEEN, Suit.CLUB).equals(new Card(CardValue.KING, Suit.CLUB)),
        false);
  }

  @Test
  public void testEqualsDifferentSuitSameVal() {
    assertEquals(new Card(CardValue.KING, Suit.HEART).equals(new Card(CardValue.KING, Suit.CLUB)),
        false);
  }

  @Test
  public void testEqualsDifferentSuitAndVal() {
    assertEquals(new Card(CardValue.KING, Suit.HEART).equals(new Card(CardValue.QUEEN, Suit.CLUB)),
        false);
  }

  @Test
  public void testHashCodeHeartAndAce() {
    assertEquals(new Card(CardValue.ACE, Suit.HEART).hashCode(),
        300);
  }

  @Test
  public void testHashCodeSpadeAndTwo() {
    assertEquals(new Card(CardValue.TWO, Suit.SPADE).hashCode(),
        401);
  }

  @Test
  public void testHashCodeClubAndThree() {
    assertEquals(new Card(CardValue.THREE, Suit.CLUB).hashCode(),
        102);
  }

  @Test
  public void testHashCodeDiamondAndFour() {
    assertEquals(new Card(CardValue.FOUR, Suit.DIAMOND).hashCode(),
        203);
  }

  @Test
  public void testHashCodeFive() {
    assertEquals(new Card(CardValue.FIVE, Suit.DIAMOND).hashCode(),
        204);
  }

  @Test
  public void testHashCodeSix() {
    assertEquals(new Card(CardValue.SIX, Suit.DIAMOND).hashCode(),
        205);
  }

  @Test
  public void testHashCodeSeven() {
    assertEquals(new Card(CardValue.SEVEN, Suit.DIAMOND).hashCode(),
        206);
  }

  @Test
  public void testHashCodeEight() {
    assertEquals(new Card(CardValue.EIGHT, Suit.DIAMOND).hashCode(),
        207);
  }

  @Test
  public void testHashCodeNine() {
    assertEquals(new Card(CardValue.NINE, Suit.DIAMOND).hashCode(),
        208);
  }

  @Test
  public void testHashCodeTen() {
    assertEquals(new Card(CardValue.TEN, Suit.DIAMOND).hashCode(),
        209);
  }

  @Test
  public void testHashCodeJack() {
    assertEquals(new Card(CardValue.JACK, Suit.DIAMOND).hashCode(),
        210);
  }

  @Test
  public void testHashCodeQueen() {
    assertEquals(new Card(CardValue.QUEEN, Suit.DIAMOND).hashCode(),
        211);
  }

  @Test
  public void testHashCodeKing() {
    assertEquals(new Card(CardValue.KING, Suit.DIAMOND).hashCode(),
        212);
  }
}