package pokerstats;

import java.util.ArrayList;
import java.util.List;

public class Player {
    public void dealHoleCards(Card cardA, Card cardB) {
        insertPlayerCardSorted(cardA);
        insertPlayerCardSorted(cardB);
        mHoleCards = mPlayerCards;
    }
    
    //returns score of your hand given these community cards + your hole cards
    //this function should never return "zero" as a score or else bad things happen
    public int dealCommunityCards(Card cardA, Card cardB, Card cardC, Card cardD, Card cardE) {
        insertPlayerCardSorted(cardA);
        insertPlayerCardSorted(cardB);
        insertPlayerCardSorted(cardC);
        insertPlayerCardSorted(cardD);
        insertPlayerCardSorted(cardE);
        
        mPossibleHands++;
        Hand playerHand = new Hand(mPlayerCards); //TODO: the Hand class will try to sort this. Not sure if this is a copy-constructor type deal or a pointer
        resetCommunityCards();
        return playerHand.getHandScore();
    }
    
    private void resetCommunityCards() {
        mPlayerCards.clear();
        mPlayerCards = mHoleCards;
    }
    
    public double oddsOfWinningHand() {
        return (double)mWins / mPossibleHands;
    }
    
    public double oddsOfTieingHand() {
        return (double)mTies / mPossibleHands;
    }
    
    public void addWin() {
        mWins++;
    }
    
    public void addTie() {
        mTies++;
    }
    
    private void insertPlayerCardSorted(Card addedCard) {
        boolean valueAdded = false;
        for (int i = 0; i < mPlayerCards.size(); i++) {
            if (mPlayerCards.get(i).getValue().compareTo(addedCard.getValue()) == -1) {
                mPlayerCards.add(i, addedCard);
                valueAdded = true;
                break;
            }
        }
        if (!valueAdded) {
            mPlayerCards.add(addedCard);
        }
    }
    
    private List<Card> mPlayerCards = new ArrayList<>();
    private List<Card> mHoleCards = new ArrayList<>();
    private int mWins;
    private int mTies;
    private int mPossibleHands;
    
}
