/*
 * Poker - calculate the odds of all possible hands after cards are dealt to 2 persons
 */
package pokerstats;

import java.util.ArrayList;
import java.util.List;

public class PokerStats {

    public static void main(String[] args) {
        //playRandomGame(2); //2 players, random hole cards
        double seconds = 0;
        for (int i = 0; i < 5; i++) {
            seconds += playSpecificHoleCards();
        }
        System.out.println("Average game was " + seconds / 5 + " seconds");
    }
    
    private static double playSpecificHoleCards() {
        List<Player> players = new ArrayList<>();
        Player player = new Player();
        player.dealHoleCards(new Card(Card.CardValue.Ace, Card.CardSuit.Clubs),
                             new Card(Card.CardValue.King, Card.CardSuit.Spades));
        
        players.add(player);
        
        player = new Player();
        player.dealHoleCards(new Card(Card.CardValue.Queen, Card.CardSuit.Hearts),
                             new Card(Card.CardValue.Jack, Card.CardSuit.Diamonds));
        
        players.add(player);
        Game theGame = new Game(players);
        System.out.println("Completed odds in " + theGame.getSecondsToPlayAllGames() + " seconds");
        return theGame.getSecondsToPlayAllGames();
    }
    
    private static void playRandomGame (int numberOfPlayers) {
        Game theGame = new Game(numberOfPlayers);
        System.out.println("Completed odds in " + theGame.getSecondsToPlayAllGames() + " seconds");
    }
    
    private static void playEveryPossiblePokerGame(int numberOfPlayers) {
        //I haven't finishing implementing this, but this will go through every possible poker game imaginable given X number of players
        CardDeck cardDeck = new CardDeck();
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < numberOfPlayers; i++) {
            for (int a = 0; a < cardDeck.size() - 1; a++) {
                for (int b = 0; b < cardDeck.size(); b++) {
                    //TODO need to finish
                }
            }
        }
    }
    
}
