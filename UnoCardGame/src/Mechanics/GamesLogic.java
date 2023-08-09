package Mechanics;

import java.util.*;

public class GamesLogic {

    public static Map<String, List<Card>> dealCards(List<Card> deck, int playersAmount, List<String> names) {
        Collections.shuffle(deck);
        Map<String, List<Card>> playersDecks = new LinkedHashMap<>();

        if (playersAmount > 1 && playersAmount < 11) {
            for (int i = 0; i < playersAmount; i++) {
                List<Card> playerHand = new ArrayList<>();
                for (int j = 0; j < 7; j++) {
                    playerHand.add(deck.get(j * playersAmount + i));
                }
                playerHand.sort(Card.sorting);
                playersDecks.put(names.get(i), playerHand);
            }
        } else {
            System.out.println("Invalid amount of players: " + playersAmount + "\nRequired amount: 2-10");
        }
        playersDecks.put("DECK", deck);
        return playersDecks;
    }

    public static Card drawCard(List<Card> deck) {
        Queue<Card> gDeck = new LinkedList<>(deck);
        Card first = deck.get(0);

        while (true) {
            if (first.specialEffect() == Card.SpecialEffect.DRAW4) {
                System.out.println("First draw card was " + Card.SpecialEffect.DRAW4 + ". Shuffling deck in process.");
                Collections.shuffle(deck);
                first = deck.get(0);
            } else {
                break;
            }
        }
        first = gDeck.poll();
        return first;
    }
}