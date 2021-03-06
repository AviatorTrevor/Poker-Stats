package pokerstats;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CardDeck {
    public static final int cCardsInDeck = 52;

    public CardDeck() {
        for (Card.CardSuit suit : Card.CardSuit.values()) {
            for (Card.CardValue value : Card.CardValue.values()) {
                mCards.add(new Card(value, suit));
            }
        }
    }
    
    public int size() {
        return mCards.size();
    }
    
    public Card dealSpecificCard(int index) {
        return mCards.remove(index);
    }
    
    public Card dealSpecificCard(Card.CardValue value, Card.CardSuit suit) {
        return mCards.remove(suit.ordinal() * 13 + value.ordinal());
    }
    
    public Card dealRandomCard() {
        Random rand = new Random();
        return mCards.remove(rand.nextInt(size()));
    }
    
    public Card getCommunityCard(int index) {
        return mCards.get(index);
    }
    
    private List<Card> mCards = new ArrayList<>();
}
