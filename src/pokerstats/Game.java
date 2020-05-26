package pokerstats;

import java.util.ArrayList;
import java.util.List;

public class Game {
    public Game(int numberOfPlayers) {
        mStartTime = System.currentTimeMillis();
        for (int i = 0; i < cNumberOfThreads; i++) {
            Hand hand = new Hand(this);
            hand.start();
            mThreadPool.add(hand);
        }
        for (int i = 0; i < numberOfPlayers; i++) {
            Player player = new Player();
            Card cardA = mCardDeck.dealRandomCard();
            Card cardB = mCardDeck.dealRandomCard();
            player.dealHoleCards(cardA, cardB);
            mPlayers.add(player);
            System.out.println("Player #" + (i + 1) + " is dealt " + cardA.getValue().name() + " of " + cardA.getSuit().name()
                + " + " + cardB.getValue().name() + " of " + cardB.getSuit().name());
        }
        everyPossibleGame();
        setRunTime();
    }
    
    //this assumes players have been dealt hole cards
    public Game(List<Player> players) {
        mStartTime = System.currentTimeMillis();
        for (int i = 0; i < cNumberOfThreads; i++) {
            Hand hand = new Hand(this);
            hand.start();
            mThreadPool.add(hand);
        }
        mPlayers = players;
        
        //remove the player cards from the deck
        for (int i = 0; i < mPlayers.size(); i++) {
            for (int j = 0; j < mPlayers.get(i).getHoleCards().size(); j++) {
                Card.CardValue value = mPlayers.get(i).getHoleCards().get(j).getValue();
                Card.CardSuit suit = mPlayers.get(i).getHoleCards().get(j).getSuit();
                mCardDeck.dealSpecificCard(value, suit);
            }
        }
        
        everyPossibleGame();
        setRunTime();
    }
    
    private void everyPossibleGame() {
        for (int a = 0; a < mCardDeck.size() - 4; a++) {
            for (int b = a + 1; b < mCardDeck.size() - 3; b++) {
                for (int c = b + 1; c < mCardDeck.size() - 2; c++) {
                    for (int d = c + 1; d < mCardDeck.size() - 1; d++) {
                        for (int e = d + 1; e < mCardDeck.size(); e++) {
                            List<Card> communityCards = new ArrayList<>();
                            communityCards.add(mCardDeck.getCommunityCard(a));
                            communityCards.add(mCardDeck.getCommunityCard(b));
                            communityCards.add(mCardDeck.getCommunityCard(c));
                            communityCards.add(mCardDeck.getCommunityCard(d));
                            communityCards.add(mCardDeck.getCommunityCard(e));
                            playHand(communityCards);
                        }
                    }
                }
            }
        }
        
        while (getThreadCount() > 0) {
            System.out.println("Waiting for hands to finish " + getThreadCount() + " Count: " + mCounter);
            synchronized(mGameMutex) {
                try {
                    mGameMutex.wait(1000);
                }
                catch (InterruptedException ex) { }
            }
        }
        while (!mThreadPool.isEmpty()) {
            getThread().killThread();
        }
        for (int i = 0; i < mPlayers.size(); i++) {
            System.out.println("Player #" + (i + 1) + " Wins " + mPlayers.get(i).oddsOfWinningHand() + "%;  Ties " + mPlayers.get(i).oddsOfTieingHand() + "%");
        }
    }
    
    
    //debug test game.
    //This constructor is only used if I want to deal a very specific hand for debug purposes
    public Game() {
        mStartTime = System.currentTimeMillis();
        
        Hand hand = new Hand(this);
        hand.start();
        mThreadPool.add(hand);
        
        Player playerA = new Player();
        playerA.dealHoleCards(new Card(Card.CardValue.Four, Card.CardSuit.Clubs), new Card(Card.CardValue.Jack, Card.CardSuit.Spades));
        
        Player playerB = new Player();
        playerB.dealHoleCards(new Card(Card.CardValue.Ten, Card.CardSuit.Spades), new Card(Card.CardValue.King, Card.CardSuit.Diamonds));
        
        List<Card> communityCards = new ArrayList<>();
        communityCards.add(new Card(Card.CardValue.Two, Card.CardSuit.Spades));
        communityCards.add(new Card(Card.CardValue.Three, Card.CardSuit.Spades));
        communityCards.add(new Card(Card.CardValue.Eight, Card.CardSuit.Spades));
        communityCards.add(new Card(Card.CardValue.Jack, Card.CardSuit.Hearts));
        communityCards.add(new Card(Card.CardValue.Queen, Card.CardSuit.Spades));
        
        playHand(communityCards);
        
        setRunTime();
    }
    
    private void setRunTime() {
        mRunTime = (System.currentTimeMillis() - mStartTime) / 1000.0;
    }
    
    public double getSecondsToPlayAllGames() {
        return mRunTime;
    }
    
    private void playHand(List<Card> communityCards) {
        mCounter++;
        Hand hand = getThread();
        hand.playHand(mPlayers, communityCards);
    }
    
    private Hand getThread() {
        synchronized (mGameMutex) {
            if (mThreadPool.isEmpty()) {
                try {
                    mGameMutex.wait();
                }
                catch (InterruptedException ex) {

                }
            }
            Hand hand = mThreadPool.remove(0);
            //System.out.println("Thread Pool Size: " + mThreadPool.size() + " (getThread)");
            return hand;
        }
    }
    
    public void threadFinished(Hand hand) {
        synchronized (mGameMutex) {
            mThreadPool.add(hand);
            //System.out.println("Thread Pool Size: " + mThreadPool.size() + " (threadFinished)");
            mGameMutex.notifyAll();
        }
    }
    
    public int getThreadCount() {
        synchronized (mGameMutex) {
            return cNumberOfThreads - mThreadPool.size();
        }
    }
    
    private List<Player> mPlayers = new ArrayList<>();
    private final long mStartTime;
    private double mRunTime;
    private int mCounter = 0;
    private CardDeck mCardDeck = new CardDeck();
    private final Object mGameMutex = new Object();
    private List<Hand> mThreadPool = new ArrayList<>();
    private final int cNumberOfThreads = Runtime.getRuntime().availableProcessors();
}
