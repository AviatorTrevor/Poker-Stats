package pokerstats;

import java.util.ArrayList;
import java.util.List;

public class CardDeck {
    public CardDeck() {
        for (CardSuit suit : CardSuit.values()) {
            for (CardValue value : CardValue.values()) {
                mCards.add(new Card(value, suit));
            }
        }
    }
    
    public int size() {
        return mCards.size();
    }
    
    public Card get(int index) {
        return mCards.remove(index);
    }
    
    private List<Card> mCards = new ArrayList<>();
}
