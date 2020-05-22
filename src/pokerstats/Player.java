package pokerstats;

import java.util.ArrayList;
import java.util.List;

public class Player {
    public void dealHoleCards(Card cardA, Card cardB) {
        mPlayerCards.add(cardA);
        mPlayerCards.add(cardB);
    }
    
    public void dealCommunityCards(Card cardA, Card cardB, Card cardC) {
        mPlayerCards.add(cardA);
        mPlayerCards.add(cardB);
        mPlayerCards.add(cardC);
    }
    
    public void resetCommunityCards() {
        mPlayerCards.subList(0, 1);
    }
    
    private List<Card> mPlayerCards = new ArrayList<>();
}
