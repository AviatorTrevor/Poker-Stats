package pokerstats;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.ArrayList;
import java.util.List;

public class Player {
    public void dealHoleCards(Card cardA, Card cardB) {
        Card.insertCardSorted(mHoleCards, cardA);
        Card.insertCardSorted(mHoleCards, cardB);
    }
    
    public List<Card> getHoleCards() {
        return mHoleCards;
    }
    
    public double oddsOfWinningHand() {
        return 100 * (double)mWins.get() / mPossibleHands.get();
    }
    
    public double oddsOfTieingHand() {
        return 100 * (double)mTies.get() / mPossibleHands.get();
    }
    
    public void addHand() {
        mPossibleHands.incrementAndGet();
    }
    
    public void addWin() {
        mWins.incrementAndGet();
    }
    
    public void addTie() {
        mTies.incrementAndGet();
    }
    
    public int getHandsPlayed() {
        return mPossibleHands.get();
    }
    
    private final List<Card> mHoleCards = new ArrayList<>();
    private AtomicInteger mWins = new AtomicInteger(0);
    private AtomicInteger mTies = new AtomicInteger(0);
    private AtomicInteger mPossibleHands = new AtomicInteger(0);
}
