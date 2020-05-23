package pokerstats;

import java.util.ArrayList;
import java.util.List;

public class Hand {
    /* Hand Rankings:
        StraightFlush, //no kicker, but compare value of top card. only a tie if the common cards are the straight-flush. Check for ace-low.
        FourOfAKind, //no kicker, but compare value. only a tie if the common cards are the four-of-a-kind
        FullHouse, //no kicker, but compare highest value of 3-of-a-kind. potentially tie.
        Flush, //no kicker, only check highest value card to resolve tie breaker. potentially a tie if common cards are the highest flush.
        Straight, //no kicker, highest value wins. potentially tie. Check for ace-low.
        ThreeOfAKind, //potentially 2 kickers. potentially tie.
        TwoPair, //potentially 1 kicker. potentially tie.
        OnePair, //potentially 3 kickers. potentially tie.
        HighCard //potentially 4 kickers. potentially tie. */
    
    final int cStraightFlush = 800;
    final int cFourOfAKind = 700;
    final int cFullHouse = 600;
    final int cFlush = 500;
    final int cStraight = 400;
    final int cThreeOfAKind = 300;
    final int cTwoPair = 200;
    final int cOnePair = 100;
    final int cHighCard = 0;
    
    //this class ASSUMES the 7 cards are sorted by value, least to greatest
    public Hand (List<Card> allCards) {
        if (allCards.size() != 7) {
            System.out.println("Illegal Hand Size");
        }
        for (int a = 0; a < allCards.size() - 4; a++) {
            for (int b = 1; b < allCards.size() - 3; b++) {
                for (int c = 2; c < allCards.size() - 2; c++) {
                    for (int d = 3; d < allCards.size() - 1; d++) {
                        for (int e = 4; e < allCards.size(); e++) {
                            
                        }
                    }
                }
            }
        }
    }
    
    private int checkForStraightFlush(List<Card> hand) {
        boolean isStraight = true;
        boolean isFlush = true;
        for (int i = 1; i < hand.size(); i++) {
            if (hand.get(i - 1).getValue().ordinal() != hand.get(i).getValue().ordinal() - 1) {
                isStraight = false;
                break;
            }
            if (!hand.get(i - 1).getSuit().equals(hand.get(i).getSuit())) {
                isFlush = false;
                break;
            }
        }
        if (isStraight && isFlush) {
            return cStraightFlush + hand.get(hand.size() - 1).getValue().ordinal();
        }
        //else - check for ace high, and make it ace-low to check for straight-flush that way
        else if (isFlush && hand.get(hand.size() - 1).getValue() == CardValue.Ace && hand.get(0).getValue() != CardValue.Ace) {
            Card ace = hand.get(hand.size() - 1);
            hand.add(0, ace);
            return checkForStraightFlush(hand);
        }
        else {
            return 0;
        }
    }
    
    private int checkForFourOfAKind() {
        
    }
    
    private int checkForFullHouse() {
        
    }
    
    private int checkForFlush() {
        
    }
    
    private int checkForStraight() {
        
    }
    
    private int checkForThreeOfAKind() {
        
    }
    
    private int checkForTwoPair() {
        
    }
    
    private int checkForPair() {
        
    }
    
    public int getHandScore() {
        return mHandScore;
    }
    
    private int mHandScore;
}
