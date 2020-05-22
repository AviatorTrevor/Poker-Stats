package pokerstats;

import java.util.List;

public class Hand {
    enum HandRanking {
        StraightFlush, //no kicker, but compare value of top card
        FourOfAKind, //no kicker, but compare value
        FullHouse, //no kicker, but compare highest value of 3-of-a-kind. potentially tie.
        Flush, //no kicker, only check highest value card to resolve tie breaker
        Straight, //no kicker, highest value wins. potentially tie.
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
        sort();
    }
    
    private sort() {
        mHand.sort(c);
    }
    
    private List<Card> mHand;
}
