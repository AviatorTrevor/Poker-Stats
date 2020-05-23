package pokerstats;

import java.util.ArrayList;
import java.util.List;

public class Player {
    public void dealHoleCards(Card cardA, Card cardB) {
        mPlayerCards.add(cardA);
        mPlayerCards.add(cardB);
    }
    
    //returns score of your hand given these community cards + your hole cards
    public int dealCommunityCards(Card cardA, Card cardB, Card cardC) {
        mPlayerCards.add(cardA);
        mPlayerCards.add(cardB);
        mPlayerCards.add(cardC);
        
        Hand playerHand = new Hand(mPlayerCards); //TODO: the Hand class will try to sort this. Not sure if this is a copy-constructor type deal or a pointer
        resetCommunityCards();
        return playerHand.getHandScore();
    }
    
    private void resetCommunityCards() {
        mPlayerCards.subList(0, 1);
    }
    
    public double oddsOfWinningHand() {
        return (double)mWins / mPossibleHands;
    }
    
    public double oddsOfTieingHand() {
        return (double)mTies / mPossibleHands;
    }
    
    public void addWin() {
        mWins++;
        mPossibleHands++;
    }
    public void addLoss() {
        mPossibleHands++;
    }
    
    public void addTie() {
        mTies++;
        mPossibleHands++;
    }
    
    public void removeTie() {
        mTies--;
        mPossibleHands--;
    }
    
    private List<Card> mPlayerCards = new ArrayList<>();
    private int mWins;
    private int mTies;
    private int mPossibleHands;
    
}
