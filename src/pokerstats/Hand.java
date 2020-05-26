package pokerstats;

import java.util.ArrayList;
import java.util.List;

public class Hand extends Thread {
    static final int cMultiplier1 = 14;
    static final int cMultiplier2 = cMultiplier1 * cMultiplier1;
    static final int cMultiplier3 = cMultiplier2 * cMultiplier1; 
    static final int cMultiplier4 = cMultiplier3 * cMultiplier1;

    static final int cStraightFlush = 2700000;
    static final int cFourOfAKind = 2600000;
    static final int cFullHouse = 2500000;
    static final int cFlush = 1500000;
    static final int cStraight = 1400000;
    static final int cThreeOfAKind = 1300000;
    static final int cTwoPair = 1200000;
    static final int cOnePair = 1000000;
    
    static final int cHighestCardIndex = 4;
    
    //this class ASSUMES the 2 hole cards are sorted and community cards are not sorted.
    public Hand (Game game) {
        mGame = game;
    }
    
    public void playHand(List<Player> players, List<Card> communityCards) {
        mPlayers = players;
        mCommunityCards = communityCards;
        synchronized (mHandMutex) {
            mHandMutex.notifyAll();
        }
    }
    
    //no kicker, but compare value of top card. only a tie if the common cards are the straight-flush. Check for ace-low.
    private int checkForStraightFlush(List<Card> hand) {
        boolean isStraight = true;
        boolean isFlush = true;
        for (int i = 1; i < hand.size(); i++) {
            if (hand.get(i - 1).getScoreValue() != hand.get(i).getScoreValue() - 1) {
                isStraight = false;
                break;
            }
            if (!hand.get(i - 1).getSuit().equals(hand.get(i).getSuit())) {
                isFlush = false;
                break;
            }
        }
        if (isStraight && isFlush) {
            return cStraightFlush + hand.get(hand.size() - 1).getScoreValue();
        }
        //else - check for ace high, and make it ace-low to check for straight-flush that way
        else if (isFlush && hand.get(cHighestCardIndex).getValue() == Card.CardValue.Ace && hand.get(0).getValue() != Card.CardValue.Ace) {
            Card ace = hand.remove(cHighestCardIndex);
            hand.add(0, ace);
            int straightFlushLowAceScore = checkForStraightFlush(hand);
            
            //add the ace back to the end, because this effects the "hand" list for other functions to check
            ace = hand.remove(0);
            hand.add(ace);
            
            return straightFlushLowAceScore;
        }
        else {
            return 0;
        }
    }
    
    //no kicker, but compare value. only a tie if the common cards are the four-of-a-kind
    private int checkForFourOfAKind(List<Card> hand) {
        int starting = 1;
        int ending = 4;
        for (int i = starting; i < ending; i++) {
            if (!hand.get(i - 1).getValue().equals(hand.get(i).getValue())) {
                if (starting == 1) {
                    starting = 2;
                    ending = 5;
                    i = 1; //setting to "1" because it's going to increment by 1 at the end of the for-loop
                }
                else {
                    return 0;
                }
            }
        }
        return cFourOfAKind + hand.get(1).getScoreValue(); //index 1 has to be part of the four-of-a-kind in all cases
    }
    
    //no kicker, but compare highest value of 3-of-a-kind. potentially tie.
    private int checkForFullHouse(List<Card> hand) {
        if (hand.get(0).getValue().equals(hand.get(1).getValue()) && hand.get(0).getValue().equals(hand.get(2).getValue())) {
            if (hand.get(3).getValue().equals(hand.get(cHighestCardIndex).getValue())) {
                return cFullHouse + cMultiplier1 * hand.get(0).getScoreValue() + hand.get(cHighestCardIndex).getScoreValue();
            }
        }
        
        if (hand.get(2).getValue().equals(hand.get(3).getValue()) && hand.get(2).getValue().equals(hand.get(cHighestCardIndex).getScoreValue())) {
            if (hand.get(0).getValue().equals(hand.get(1).getValue())) {
                return cFullHouse + cMultiplier1 * hand.get(cHighestCardIndex).getScoreValue() + hand.get(0).getScoreValue();
            }
        }
        
        return 0;
    }
    
    //no kicker, ties break with highest hole care. potentially a tie if common cards are the highest flush.
    private int checkForFlush(List<Card> hand) {
        for (int i = 1; i < hand.size(); i++) {
            if (!hand.get(i).getSuit().equals(hand.get(i - 1).getSuit())) {
                return 0;
            }
        }
        return cFlush + hand.get(cHighestCardIndex).getScoreValue()
                   + cMultiplier4 * hand.get(4).getScoreValue()
                   + cMultiplier3 * hand.get(3).getScoreValue()
                   + cMultiplier2 * hand.get(2).getScoreValue()
                   + cMultiplier1 * hand.get(1).getScoreValue()
                   + hand.get(0).getScoreValue();
    }
    
    //no kicker, highest value wins. potentially tie. Check for ace-low.
    private int checkForStraight(List<Card> hand) {
        boolean isStraight = true;
        for (int i = 1; i < hand.size(); i++) {
            if (hand.get(i - 1).getScoreValue() != hand.get(i).getScoreValue() - 1) {
                isStraight = false;
                break;
            }
        }
        if (isStraight) {
            return cStraight + hand.get(cHighestCardIndex).getScoreValue();
        }
        //else - check for ace high, and make it ace-low to check for straight-flush that way
        else if (hand.get(cHighestCardIndex).getValue() == Card.CardValue.Ace && hand.get(0).getValue() != Card.CardValue.Ace) {
            Card ace = hand.remove(cHighestCardIndex);
            hand.add(0, ace);
            int straightLowAceScore = checkForStraight(hand);
            
            //add the ace back to the end, because this effects the "hand" list for other functions to check
            ace = hand.remove(0);
            hand.add(ace);
            
            return straightLowAceScore;
        }
        else {
            return 0;
        }
    }
    
    //potentially 2 kickers. potentially tie.
    private int checkForThreeOfAKind(List<Card> hand) {
        for (int i = 2; i < hand.size(); i++) {
            if (hand.get(i).getValue().equals(hand.get(i - 1).getValue()) && hand.get(i).getValue().equals(hand.get(i - 2).getValue())) {
                int score = cThreeOfAKind + cMultiplier2 * hand.get(i).getScoreValue();
                
                //add value of the kickers
                if (i == 2) {
                    score += hand.get(3).getScoreValue() + cMultiplier1 * hand.get(cHighestCardIndex).getScoreValue();
                }
                else if (i == 3) {
                    score += hand.get(0).getScoreValue() + cMultiplier1 * hand.get(cHighestCardIndex).getScoreValue();
                }
                else { //if i == 4
                    score += hand.get(0).getScoreValue() + cMultiplier1 * hand.get(1).getScoreValue();
                }
                
                return score;
            }
        }
        return 0;
    }
    
    //potentially 1 kicker. potentially tie.
    private int checkForTwoPair(List<Card> hand) {
        int score = 0;
        if (hand.get(0).getValue().equals(hand.get(1).getValue())) {
            if (hand.get(2).getValue().equals(hand.get(3).getValue())) {
                score = cMultiplier2 * hand.get(3).getScoreValue() + cMultiplier1 * hand.get(0).getScoreValue() + hand.get(cHighestCardIndex).getScoreValue();
            }
            else if (hand.get(3).getValue().equals(hand.get(cHighestCardIndex).getValue())) {
                score = cMultiplier2 * hand.get(cHighestCardIndex).getScoreValue() + cMultiplier1 * hand.get(0).getScoreValue() + hand.get(2).getScoreValue();
            }
        }
        else if (hand.get(1).getValue().equals(hand.get(2).getValue()) && hand.get(3).getValue().equals(hand.get(cHighestCardIndex).getValue())) {
            score = cMultiplier2 * hand.get(cHighestCardIndex).getScoreValue() + cMultiplier1 * hand.get(1).getScoreValue();
        }
        return score;
    }
    
    //potentially 3 kickers. potentially tie.
    private int checkForOnePair(List<Card> hand) { 
        int score = 0;
        for (int i = 1; i < hand.size(); i++) {
            if (hand.get(i).getValue().equals(hand.get(i - 1).getValue())) {
                score = cOnePair + cMultiplier3 * hand.get(i).getScoreValue();
                if (i == 1) {
                    score += cMultiplier2 * hand.get(cHighestCardIndex).getScoreValue() + cMultiplier1 * hand.get(3).getScoreValue() + hand.get(2).getScoreValue();
                }
                else if (i == 2) {
                    score += cMultiplier2 * hand.get(cHighestCardIndex).getScoreValue() + cMultiplier1 * hand.get(3).getScoreValue() + hand.get(0).getScoreValue();
                }
                else { //if (i == 3)
                    score += cMultiplier2 * hand.get(2).getScoreValue() + cMultiplier1 * hand.get(1).getScoreValue() + hand.get(0).getScoreValue();
                }
            }
        }
        return score;
    }
    
    //potentially 4 kickers. potentially tie.
    private int calculateHighCardScore(List<Card> hand) {
        return cMultiplier4 * hand.get(cHighestCardIndex).getScoreValue() + cMultiplier3 * hand.get(3).getScoreValue() + cMultiplier2 * hand.get(2).getScoreValue()
                   + cMultiplier1 * hand.get(1).getScoreValue() + hand.get(0).getScoreValue();
    }
    
    @Override
    public void run() {
        synchronized (mHandMutex) {
            try {
                mHandMutex.wait();
            }
            catch (InterruptedException ex) { }
        }

        List<Integer> indexesOfPossibleTies = new ArrayList<>();
        int indexOfWinningPlayer;
        int highestScoreOfAllPlayers;
        while (mRunning) {
            indexOfWinningPlayer = 0;
            highestScoreOfAllPlayers = 0;
            indexesOfPossibleTies.clear();
            for (int p = 0; p < mPlayers.size(); p++) {
                mPlayers.get(p).addHand();
                List<Card> allCards = new ArrayList<>();
                allCards.addAll(mPlayers.get(p).getHoleCards());
                Card.insertCardsSorted(allCards, mCommunityCards);
                int playerHandScore;
                int highestPlayerScore = 0;

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

                                    if ((playerHandScore = checkForStraightFlush(hand)) > 0) {
                                        if (playerHandScore > highestPlayerScore) {
                                            highestPlayerScore = playerHandScore;
                                        }
                                    }
                                    else if ((playerHandScore = checkForFourOfAKind(hand)) > 0) {
                                        if (playerHandScore > highestPlayerScore) {
                                            highestPlayerScore = playerHandScore;
                                        }
                                    }
                                    else if ((playerHandScore = checkForFullHouse(hand)) > 0) {
                                        if (playerHandScore > highestPlayerScore) {
                                            highestPlayerScore = playerHandScore;
                                        }
                                    }
                                    else if ((playerHandScore = checkForFlush(hand)) > 0) {
                                        if (playerHandScore > highestPlayerScore) {
                                            highestPlayerScore = playerHandScore;
                                        }
                                    }
                                    else if ((playerHandScore = checkForStraight(hand)) > 0) {
                                        if (playerHandScore > highestPlayerScore) {
                                            highestPlayerScore = playerHandScore;
                                        }
                                    }
                                    else if ((playerHandScore = checkForThreeOfAKind(hand)) > 0) {
                                        if (playerHandScore > highestPlayerScore) {
                                            highestPlayerScore = playerHandScore;
                                        }
                                    }
                                    else if ((playerHandScore = checkForTwoPair(hand)) > 0) {
                                        if (playerHandScore > highestPlayerScore) {
                                            highestPlayerScore = playerHandScore;
                                        }
                                    }
                                    else if ((playerHandScore = checkForOnePair(hand)) > 0) {
                                        if (playerHandScore > highestPlayerScore) {
                                            highestPlayerScore = playerHandScore;
                                        }
                                    }
                                    else {
                                        playerHandScore = calculateHighCardScore(hand);
                                        if (playerHandScore > highestPlayerScore) {
                                            highestPlayerScore = playerHandScore;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if (highestPlayerScore == highestScoreOfAllPlayers) {
                    if (indexesOfPossibleTies.isEmpty()) {
                        indexesOfPossibleTies.add(indexOfWinningPlayer);
                    }
                    indexesOfPossibleTies.add(p);
                }
                else if (highestPlayerScore > highestScoreOfAllPlayers) {
                    highestScoreOfAllPlayers = highestPlayerScore;
                    indexOfWinningPlayer = p;
                    indexesOfPossibleTies.clear();
                }
            }
            if (!indexesOfPossibleTies.isEmpty()) { //the winner tied with 1 or more person
                for (int i = 0; i < indexesOfPossibleTies.size(); i++) {
                    mPlayers.get(indexesOfPossibleTies.get(i)).addTie();
                }
            }
            else { //else there was a clear winner
                mPlayers.get(indexOfWinningPlayer).addWin();
            }
            synchronized (mHandMutex) {
                mGame.threadFinished(this);
                try {
                    mHandMutex.wait();
                }
                catch (InterruptedException ex) { }
            }
        }
    }
    
    public void killThread() {
        mRunning = false; 
        synchronized (mHandMutex) {
            mHandMutex.notifyAll();
        }
    }
    

    private List<Player> mPlayers;
    private List<Card> mCommunityCards;
    private Game mGame;
    private final Object mHandMutex = new Object();
    private boolean mRunning = true;
}
