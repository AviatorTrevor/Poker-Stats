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
    
    final int cStraightFlush = 800000;
    final int cFourOfAKind = 700000;
    final int cFullHouse = 600000;
    final int cFlush = 500000;
    final int cStraight = 400000;
    final int cThreeOfAKind = 300000;
    final int cTwoPair = 200000;
    final int cOnePair = 100000;
    
    final int cHighestCardIndex = 4;
    
    //this class ASSUMES the 7 cards are sorted by value, least to greatest
    public Hand (List<Card> allCards) {
        if (allCards.size() != 7) {
            System.out.println("Illegal Hand Size");
        }
        for (int a = 0; a < allCards.size() - 4; a++) {
            for (int b = a + 1; b < allCards.size() - 3; b++) {
                for (int c = b + 1; c < allCards.size() - 2; c++) {
                    for (int d = c + 1; d < allCards.size() - 1; d++) {
                        for (int e = d + 1; e < allCards.size(); e++) {
                            List<Card> hand = new ArrayList<>();
                            hand.add(allCards.get(a));
                            hand.add(allCards.get(b));
                            hand.add(allCards.get(c));
                            hand.add(allCards.get(d));
                            hand.add(allCards.get(e));
                            
                            if (checkForStraightFlush(hand)) {
                                return;
                            }
                            else if (checkForFourOfAKind(hand)) {
                                return;
                            }
                            else if (checkForFullHouse(hand)) {
                                return;
                            }
                            else if (checkForFlush(hand)) {
                                return;
                            }
                            else if (checkForStraight(hand)) {
                                return;
                            }
                            else if (checkForThreeOfAKind(hand)) {
                                return;
                            }
                            else if (checkForTwoPair(hand)) {
                                return;
                            }
                            else if (checkForPair(hand)) {
                                return;
                            }
                            else {
                                mHandScore = hand.get(cHighestCardIndex).getScoreValue();
                            }
                        }
                    }
                }
            }
        }
    }
    
    private boolean checkForStraightFlush(List<Card> hand) {
        boolean isStraight = true;
        boolean isFlush = true;
        for (int i = 1; i < hand.size(); i++) {
            if (!hand.get(i - 1).getScoreValue() != hand.get(i).getScoreValue() - 1) {
                isStraight = false;
                break;
            }
            if (!hand.get(i - 1).getSuit().equals(hand.get(i).getSuit())) {
                isFlush = false;
                break;
            }
        }
        if (isStraight && isFlush) {
            mHandScore = cStraightFlush + hand.get(hand.size() - 1).getScoreValue();
            return true;
        }
        //else - check for ace high, and make it ace-low to check for straight-flush that way
        else if (isFlush && hand.get(cHighestCardIndex).getValue() == CardValue.Ace && hand.get(0).getValue() != CardValue.Ace) {
            Card ace = hand.remove(cHighestCardIndex);
            hand.add(0, ace);
            return checkForStraightFlush(hand);
        }
        else {
            return false;
        }
    }
    
    private boolean checkForFourOfAKind(List<Card> hand) {
        int starting = 1;
        int ending = cHighestCardIndex;
        for (int i = starting; i < ending; i++) {
            if (!hand.get(i - 1).equals(hand.get(i))) {
                if (starting == 1) {
                    starting = 2;
                    ending = 5;
                    i = 1; //setting to "1" because it's going to increment by 1 at the end of the for-loop
                }
                else {
                    return false;
                }
            }
        }
        mHandScore = cFourOfAKind + hand.get(1).getScoreValue(); //index 1 has to be part of the four-of-a-kind in all cases
        return true;
    }
    
    private boolean checkForFullHouse(List<Card> hand) {
        if (hand.get(0).getValue().equals(hand.get(1).getValue()) && hand.get(0).getValue().equals(hand.get(2).getValue())) {
            if (hand.get(3).getValue().equals(hand.get(cHighestCardIndex).getValue())) {
                mHandScore = cFullHouse + 100 * hand.get(0).getScoreValue() + hand.get(cHighestCardIndex).getScoreValue();
                return true;
            }
        }
        
        if (hand.get(2).getValue().equals(hand.get(3).getValue()) && hand.get(2).getValue().equals(hand.get(cHighestCardIndex).getScoreValue())) {
            if (hand.get(0).getValue().equals(hand.get(1).getValue())) {
                mHandScore = cFullHouse + 100 * hand.get(cHighestCardIndex).getScoreValue() + hand.get(0).getScoreValue();
                return true;
            }
        }
        
        return false;
    }
    
    private boolean checkForFlush(List<Card> hand) {
        for (int i = 1; i < cHighestCardIndex; i++) {
            if (!hand.get(i).getSuit().equals(hand.get(i - 1).getSuit())) {
                return false;
            }
        }
        mHandScore = cFlush + hand.get(cHighestCardIndex).getScoreValue();
        return true;
    }
    
    private boolean checkForStraight(List<Card> hand) {
        boolean isStraight = true;
        for (int i = 1; i < hand.size(); i++) {
            if (hand.get(i - 1).getScoreValue() != hand.get(i).getScoreValue() - 1) {
                isStraight = false;
                break;
            }
        }
        if (isStraight) {
            mHandScore = cStraight + hand.get(cHighestCardIndex).getScoreValue();
            return true;
        }
        //else - check for ace high, and make it ace-low to check for straight-flush that way
        else if (hand.get(cHighestCardIndex).getValue() == CardValue.Ace && hand.get(0).getValue() != CardValue.Ace) {
            Card ace = hand.remove(cHighestCardIndex);
            hand.add(0, ace);
            return checkForStraight(hand);
        }
        else {
            return false;
        }
    }
    
    private boolean checkForThreeOfAKind(List<Card> hand) {
        for (int i = 2; i < cHighestCardIndex; i++) {
            if (hand.get(i).getValue().equals(hand.get(i - 1).getValue()) && hand.get(i).getValue().equals(hand.get(i - 2).getValue())) {
                mHandScore = cThreeOfAKind + 1000*hand.get(i).getScoreValue();
                
                //add value of the kickers
                if (i == 2) {
                    mHandScore += hand.get(3).getScoreValue() + 100 * hand.get(cHighestCardIndex).getScoreValue();
                }
                else if (i == 3) {
                    mHandScore += hand.get(0).getScoreValue() + 100 * hand.get(cHighestCardIndex).getScoreValue();
                }
                else { //if i == 4
                    mHandScore += hand.get(0).getScoreValue() + 100 * hand.get(1).getScoreValue();
                }
                
                return true;
            }
        }
        return false;
    }
    
    private boolean checkForTwoPair(List<Card> hand) {
        if (hand.get(0).getValue().equals(hand.get(1).getValue())) {
            if (hand.get(2).getValue().equals(hand.get(3).getValue())) {
                mHandScore = 200 * hand.get(3).getScoreValue() + 100 * hand.get(0).getScoreValue() + hand.get(cHighestCardIndex).getScoreValue();
                return true;
            }
            else if (hand.get(3).getValue().equals(hand.get(cHighestCardIndex).getValue())) {
                mHandScore = 200 * hand.get(cHighestCardIndex).getScoreValue() + 100 * hand.get(0).getScoreValue() + hand.get(2).getScoreValue();
                return true;
            }
            else {
                return false;
            }
        }
        else if (hand.get(1).getValue().equals(hand.get(2).getValue()) && hand.get(3).getValue().equals(hand.get(cHighestCardIndex).getValue())) {
            mHandScore = 200 * hand.get(cHighestCardIndex).getScoreValue() + 100 * hand.get(1).getScoreValue();
            return true;
        }
        return false;
    }
    
    private boolean checkForPair(List<Card> hand) {
        for (int i = 1; i < cHighestCardIndex; i++) {
            if (hand.get(i).getValue().equals(hand.get(i - 1).getValue())) {
                if (i == 1) {
                    
                }
                else if (i == 2) {
                    
                }
                else { //if (i == 3)
                    
                }
            }
        }
    }
    
    public int getHandScore() {
        return mHandScore;
    }
    
    private int mHandScore;
}
