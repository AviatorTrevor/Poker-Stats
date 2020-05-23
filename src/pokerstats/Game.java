package pokerstats;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private CardDeck mCardDeck = new CardDeck();

    public Game(int numberOfPlayers) {
        
        for (int i = 0; i < numberOfPlayers; i++) {
            Player player = new Player();
            Card cardA = mCardDeck.dealRandomCard();
            Card cardB = mCardDeck.dealRandomCard();
            player.dealHoleCards(cardA, cardB);
            mPlayers.add(player);
            System.out.println("Player #" + (i + 1) + " is dealt " + cardA.getValue().name() + " of " + cardA.getSuit().name()
                + " + " + cardB.getValue().name() + " of " + cardB.getSuit().name());
        }
        
        for (int a = 0; a < mCardDeck.size() - 4; a++) {
            for (int b = 1; b < mCardDeck.size() - 3; b++) {
                for (int c = 2; c < mCardDeck.size() - 2; c++) {
                    for (int d = 3; d < mCardDeck.size() - 1; d++) {
                        for (int e = 4; e < mCardDeck.size(); e++) {
                            int indexOfWinningPlayer = 0;
                            int highestScore = 0;
                            List<Integer> indexesOfPossibleTies = new ArrayList<>();
                            for (int p = 0; p < mPlayers.size(); p++) {
                                int playerScore = mPlayers.get(p).dealCommunityCards(
                                        mCardDeck.getCommunityCard(a),
                                        mCardDeck.getCommunityCard(b),
                                        mCardDeck.getCommunityCard(c),
                                        mCardDeck.getCommunityCard(d),
                                        mCardDeck.getCommunityCard(e));
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
                                for (int i = 0; i < indexesOfPossibleTies.size(); i++) {
                                    mPlayers.get(indexesOfPossibleTies.get(i)).addTie();
                                }
                            }
                            else { //else there was a clear winner
                                mPlayers.get(indexOfWinningPlayer);
                            }
                        }
                    }
                }
            }
        }
        
        for (int i = 0; i < mPlayers.size(); i++) {
            System.out.println("Player #" + (i + 1) + "Wins " + mPlayers.get(i).oddsOfWinningHand() + "%;  Ties " + mPlayers.get(i).oddsOfTieingHand() + "%");
        }
    }
    
    private List<Player> mPlayers = new ArrayList<>();
}
