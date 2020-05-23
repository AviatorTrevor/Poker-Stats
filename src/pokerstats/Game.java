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
            System.out.println("Player #" + i + " is dealt " + cardA.getValue().name() + " of " + cardA.getSuit().name()
                + " + " + cardB.getValue().name() + " of " + cardB.getSuit().name());
        }
        
        for (int a = 0; a < mCardDeck.size() - 2; a++) {
            for (int b = 1; b < mCardDeck.size() - 1; b++) {
                for (int c = 2; c < mCardDeck.size(); c++) {
                    int indexOfWinningPlayer = 0;
                    int highestScore = 0;
                    boolean tie = false;
                    for (int p = 0; p < mPlayers.size(); p++) {
                        int playerScore = mPlayers.get(p).dealCommunityCards(mCardDeck.getCommunityCard(a), mCardDeck.getCommunityCard(b), mCardDeck.getCommunityCard(c));
                        if (playerScore == highestScore) {
                            tie = true;
                            mPlayers.get(indexOfWinningPlayer).addTie();
                            mPlayers.get(p).addTie();
                        }
                    }
                    
                }
            }
        }
    }
    
    private List<Player> mPlayers = new ArrayList<>();
}
