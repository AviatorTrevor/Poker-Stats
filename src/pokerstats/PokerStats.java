/*
 * Poker - calculate the odds of all possible hands after cards are dealt to 2 persons
 */
package pokerstats;

import java.util.ArrayList;
import java.util.List;

public class PokerStats {

    public static void main(String[] args) {
        playRandomGame(2); //2 players, random hole cards
    }
    
    private static void playRandomGame (int numberOfPlayers) {
        Game theGame = new Game(numberOfPlayers);
        System.out.println("Completed odds in " + theGame.getSecondsToPlayAllGames() + " seconds");
    }
    
    private static void playEveryPossiblePokerGame(int numberOfPlayers) {
        CardDeck cardDeck = new CardDeck();
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < numberOfPlayers; i++) {
            for (int a = 0; a < cardDeck.size() - 1; a++) {
                for (int b = 0; b < cardDeck.size(); b++) {
                    //////////TODO
                }
            }
        }
    }
    
}
