package pokerstats;

import java.util.Random;

public class Game {
    private CardDeck mCardDeck = new CardDeck();

    public Game() {
        Player playerOne = new Player();
        Player playerTwo = new Player();
        playerOne.dealHoleCards(dealCard(), dealCard());
    }
    
    public Card dealCard() {
        Random rand = new Random();
        return mCardDeck.get(rand.nextInt(mCardDeck.size()));
    }
}
