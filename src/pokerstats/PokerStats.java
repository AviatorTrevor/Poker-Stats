/*
 * Poker - calculate the odds of all possible hands after cards are dealt to 2 persons
 */
package pokerstats;

import java.util.ArrayList;
import java.util.List;

public class PokerStats {

    public static void main(String[] args) {
        //playRandomGame(2); //2 players, random hole cards
        playEveryPossiblePokerGame(2);
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
        CardDeck cardDeck = new CardDeck();
        List<Player> players = new ArrayList<>();
        List<Integer> playerHoleCardIndexes = new ArrayList<>();
        int counter = 1;
        for (int i = 0; i < numberOfPlayers; i++) {
            playerHoleCardIndexes.add(i * 2);
            playerHoleCardIndexes.add(i * 2 + 1);
        }

        while (dealCards(playerHoleCardIndexes, playerHoleCardIndexes.size() - 1)) {
            for (int i = 0; i < numberOfPlayers; i++) {
                for (int j = 0; j < numberOfPlayers; j++) {
                    Player newPlayer = new Player();
                    newPlayer.dealHoleCards(cardDeck.getCommunityCard(playerHoleCardIndexes.get(((i * 2)     + (j * 2)) % (numberOfPlayers * 2))),
                                            cardDeck.getCommunityCard(playerHoleCardIndexes.get(((i * 2 + 1) + (j * 2)) % (numberOfPlayers * 2))));
                    players.add(newPlayer);
                }
                Game theGame = new Game(players);
                System.out.println("Completed Game " + counter + " in " + theGame.getSecondsToPlayAllGames() + " seconds");
                players.clear();
                counter++;
            }
        }
    }
    
    private static boolean dealCards(List<Integer> cardIndexes, int indexToIncrement) {
        int value = cardIndexes.get(indexToIncrement);
        if (indexToIncrement == cardIndexes.size() - 1) {
            if (value + 1 < CardDeck.cCardsInDeck) {
                cardIndexes.set(indexToIncrement, value + 1);
                return true;
            }
            else {
                cardIndexes.set(indexToIncrement, cardIndexes.get(indexToIncrement - 1) + 2);
                return dealCards(cardIndexes, indexToIncrement - 1);
            }
        }
        else if (indexToIncrement == 0) {
            if (value + 1 < cardIndexes.get(indexToIncrement + 1)) {
                cardIndexes.set(indexToIncrement, value + 1);
                return true;
            }
            else {
                return false;
            }
        }
        else {
            if (value + 1 < cardIndexes.get(indexToIncrement + 1)) {
                cardIndexes.set(indexToIncrement, value + 1);
                return true;
            }
            else {
                cardIndexes.set(indexToIncrement, cardIndexes.get(indexToIncrement - 1) + 2);
                return dealCards(cardIndexes, indexToIncrement - 1);
            }
        }
    }
}
