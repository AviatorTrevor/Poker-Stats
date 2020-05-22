/*
 * Poker - calculate the odds of all possible hands after cards are dealt to 2 persons
 */
package pokerstats;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class PokerStats {
    
    private CardDeck mCardDeck = new CardDeck();

    public static void main(String[] args) {
        System.out.println("Starting at " + getCurrentTime());
    }
    
    public static String getCurrentTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }
    
    public Card dealCard() {
        Random rand = new Random();
        return mCardDeck.get(rand.nextInt(mCardDeck.size()));
    }
    
}
