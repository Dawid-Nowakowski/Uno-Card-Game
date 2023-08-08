package Mechanics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GamesLogic {

    public static List<List<Card>> dealCards(List<Card> deck, int playersAmount) {
        Collections.shuffle(deck);
        List<List<Card>> playersDecks = new ArrayList<>();

        Card.printDeck(deck);
        System.out.println();

        if (playersAmount > 1 && playersAmount < 11) {
            for (int i = 0; i < playersAmount; i++) {
                List<Card> playerHand = new ArrayList<>();
                for (int j = 0; j < 7; j++) {
                    playerHand.add(deck.get(j * playersAmount + i));
                }
                playerHand.sort(Card.sorting);
                playersDecks.add(playerHand);
            }
            for(var d : playersDecks){
                for(var c : d){
                    deck.remove(c);
                }
            }
        } else {
            System.out.println("Invalid amount of players: " + playersAmount + "\nRequired amount: 2-10");
        }
        return playersDecks;
    }

}
