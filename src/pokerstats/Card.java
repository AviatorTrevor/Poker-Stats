package pokerstats;

import java.util.List;


public class Card {
    public enum CardValue {
        Two,
        Three,
        Four,
        Five,
        Six,
        Seven,
        Eight,
        Nine,
        Ten,
        Jack,
        Queen,
        King,
        Ace
    }

    public enum CardSuit {
        Clubs,
        Diamonds,
        Hearts,
        Spades
    }

    public Card(CardValue value, CardSuit suit) {
        mValue = value;
        mSuit = suit;
    }
    
    public CardValue getValue() {
        return mValue;
    }
    
    public int getScoreValue() {
        return mValue.ordinal() + 1;
    }
    
    public CardSuit getSuit() {
        return mSuit;
    }
    
    public static void insertCardSorted(List<Card> cards, Card addedCard) {
        boolean valueAdded = false;
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).getValue().compareTo(addedCard.getValue()) > 0) {
                cards.add(i, addedCard);
                valueAdded = true;
                break;
            }
        }
        if (!valueAdded) {
            cards.add(addedCard);
        }
    }
    
    public static void insertCardsSorted(List<Card> destination, List<Card> source) {
        for (int i = 0; i < source.size(); i++) {
            insertCardSorted(destination, source.get(i));
        }
    }
    
    private final CardValue mValue;
    private final CardSuit  mSuit;
}
