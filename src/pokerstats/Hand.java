package pokerstats;

import java.util.ArrayList;
import java.util.List;

public class Hand {
    enum HandRanking {
        StraightFlush, //no kicker, but compare value of top card. only a tie if the common cards are the straight-flush. Check for ace-low.
        FourOfAKind, //no kicker, but compare value. only a tie if the common cards are the four-of-a-kind
        FullHouse, //no kicker, but compare highest value of 3-of-a-kind. potentially tie.
        Flush, //no kicker, only check highest value card to resolve tie breaker. potentially a tie if common cards are the highest flush.
        Straight, //no kicker, highest value wins. potentially tie. Check for ace-low.
        ThreeOfAKind, //potentially 2 kickers. potentially tie.
        TwoPair, //potentially 1 kicker. potentially tie.
        OnePair, //potentially 3 kickers. potentially tie.
        HighCard, //potentially 4 kickers. potentially tie.
    }
    
    public Hand (List<Card> hand) {
        if (hand.size() != 5) {
            System.out.println("Illegal Hand Size");
        }
        mHand = hand;
        sortCards();
    }
    
    private void sortCards() {
        sort();
    }
    
    private void sort() {
        boolean sorted = false;
        for (int i = 0; i < mHand.size() - 1 && !sorted; i++) {
            if (mHand.get(i).getValue().compareTo(mHand.get(i).getValue())) {
                
            }
        }
    }
    
    private List<Card> mHand = new ArrayList<>();
}
