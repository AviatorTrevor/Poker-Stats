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
    
    private CardValue mValue;
    private CardSuit  mSuit;
}
