/*
 * Poker - calculate the odds of all possible hands after cards are dealt to 2 persons
 */
package pokerstats;

import java.util.ArrayList;
import java.util.List;

public class PokerStats {

    public static void main(String[] args) {
        playRandomGame(2); //2 players, random hole cards
        //playEveryPossiblePokerGame(2); //takes about 18 days on my computer to find every game possible for 2 people
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
            if (i != numberOfPlayers - 1) {
                playerHoleCardIndexes.add(i * 2 + 1);
            }
            else {
                playerHoleCardIndexes.add(i * 2);
            }
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
            for (int i = 0; i < playerHoleCardIndexes.size(); i++) {
                System.out.print(playerHoleCardIndexes.get(i) + ", ");
            }
            System.out.println();
        }
        System.out.println(counter + " possible games");
    }
    
    private static boolean dealCards(List<Integer> cardIndexes, int indexToIncrement) {
        int value = cardIndexes.get(indexToIncrement);
        if (indexToIncrement == cardIndexes.size() - 1) {
            value++;
            if (value < CardDeck.cCardsInDeck) {
                cardIndexes.set(indexToIncrement, value);
                return true;
            }
            else {
                value = cardIndexes.get(indexToIncrement - 1) + 2;
                if (value < CardDeck.cCardsInDeck) {
                  cardIndexes.set(indexToIncrement, value);
                }
                return dealCards(cardIndexes, indexToIncrement - 1);
            }
        }
        else if (indexToIncrement == 0) {
            value++;
            if (value < cardIndexes.get(indexToIncrement + 1)) {
                cardIndexes.set(indexToIncrement, value);
                return true;
            }
            else {
                return false;
            }
        }
        else {
            value++;
            if (value < cardIndexes.get(indexToIncrement + 1)) {
                cardIndexes.set(indexToIncrement, value);
                return true;
            }
            else {
                value = cardIndexes.get(indexToIncrement - 1) + 2;
                if (value < cardIndexes.get(indexToIncrement + 1)) {
                    cardIndexes.set(indexToIncrement, value);
                }
                return dealCards(cardIndexes, indexToIncrement - 1);
            }
        }
    }
}
