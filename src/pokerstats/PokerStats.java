/*
 * Poker - calculate the odds of all possible hands after cards are dealt to 2 persons
 */
package pokerstats;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PokerStats {

    public static void main(String[] args) {
        System.out.println("Starting at " + getCurrentTime());
        Game theGame = new Game();
        System.out.println("Ending at " + getCurrentTime());
    }
    
    public static String getCurrentTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }
    
}
