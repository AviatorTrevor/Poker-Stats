package pokerstats;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private CardDeck mCardDeck = new CardDeck();

    public Game(int numberOfPlayers) {
        
        Player playerA = new Player();
        playerA.dealHoleCards(new Card(CardValue.Four, CardSuit.Clubs), new Card(CardValue.Jack, CardSuit.Spades));
        
        Player playerB = new Player();
        playerB.dealHoleCards(new Card(CardValue.Ten, CardSuit.Spades), new Card(CardValue.King, CardSuit.Diamonds));
        
        int scoreA = playerA.dealCommunityCards(new Card(CardValue.Two, CardSuit.Spades),
                                                new Card(CardValue.Three, CardSuit.Spades),
                                                new Card(CardValue.Eight, CardSuit.Spades),
                                                new Card(CardValue.Jack, CardSuit.Hearts),
                                                new Card(CardValue.Queen, CardSuit.Spades), true);
        
        int scoreB = playerB.dealCommunityCards(new Card(CardValue.Two, CardSuit.Spades),
                                                new Card(CardValue.Three, CardSuit.Spades),
                                                new Card(CardValue.Eight, CardSuit.Spades),
                                                new Card(CardValue.Jack, CardSuit.Hearts),
                                                new Card(CardValue.Queen, CardSuit.Spades), true);
        
        for (int i = 0; i < numberOfPlayers; i++) {
            Player player = new Player();
            Card cardA = mCardDeck.dealRandomCard();
            Card cardB = mCardDeck.dealRandomCard();
            player.dealHoleCards(cardA, cardB);
            mPlayers.add(player);
            System.out.println("Player #" + (i + 1) + " is dealt " + cardA.getValue().name() + " of " + cardA.getSuit().name()
                + " + " + cardB.getValue().name() + " of " + cardB.getSuit().name());
        }
        
        int counter = 0;
        for (int a = 0; a < mCardDeck.size() - 4; a++) {
            for (int b = a + 1; b < mCardDeck.size() - 3; b++) {
                for (int c = b + 1; c < mCardDeck.size() - 2; c++) {
                    for (int d = c + 1; d < mCardDeck.size() - 1; d++) {
                        for (int e = d + 1; e < mCardDeck.size(); e++) {
                            counter++;
                            boolean printDebug = true;//(counter % 10000 == 0);
                            int indexOfWinningPlayer = 0;
                            int highestScore = 0;
                            List<Integer> indexesOfPossibleTies = new ArrayList<>();
                            for (int p = 0; p < mPlayers.size(); p++) {
                                if (printDebug) {
                                    System.out.print("Player #" + (p + 1) + ": ");
                                }
                                int playerScore = mPlayers.get(p).dealCommunityCards(
                                        mCardDeck.getCommunityCard(a),
                                        mCardDeck.getCommunityCard(b),
                                        mCardDeck.getCommunityCard(c),
                                        mCardDeck.getCommunityCard(d),
                                        mCardDeck.getCommunityCard(e),
                                        printDebug);

                                if (playerScore == highestScore) {
                                    indexesOfPossibleTies.add(p - 1);
                                    indexesOfPossibleTies.add(p);
                                }
                                else if (playerScore > highestScore) {
                                    highestScore = playerScore;
                                    indexOfWinningPlayer = p;
                                    indexesOfPossibleTies.clear();
                                }
                            }

                            if (indexesOfPossibleTies.size() > 0) { //the winner tied with 1 or more person
                                if (printDebug) {
                                    System.out.println("Tie");
                                }
                                for (int i = 0; i < indexesOfPossibleTies.size(); i++) {
                                    mPlayers.get(indexesOfPossibleTies.get(i)).addTie();
                                }
                            }
                            else { //else there was a clear winner
                                if (printDebug) {
                                    System.out.println("Player #" + (indexOfWinningPlayer + 1) + " wins");
                                }
                                mPlayers.get(indexOfWinningPlayer).addWin();
                            }
                        }
                    }
                }
            }
        }
        
        for (int i = 0; i < mPlayers.size(); i++) {
            System.out.println("Player #" + (i + 1) + " Wins " + mPlayers.get(i).oddsOfWinningHand() + "%;  Ties " + mPlayers.get(i).oddsOfTieingHand() + "%");
        }
    }
    
    private List<Player> mPlayers = new ArrayList<>();
}
