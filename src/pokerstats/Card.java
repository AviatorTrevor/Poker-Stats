package pokerstats;


enum CardValue {
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

enum CardSuit {
    Clubs,
    Diamonds,
    Hearts,
    Spades
}

public class Card {
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
    
    private CardValue mValue;
    private CardSuit  mSuit;
}
