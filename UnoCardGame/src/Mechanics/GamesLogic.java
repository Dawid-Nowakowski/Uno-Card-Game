package Mechanics;

import java.util.*;

public class GamesLogic {

    public ArrayList<Player> dealCards(ArrayList<Card> deck, int playersAmount, List<String> names) {
        Collections.shuffle(deck);
        Card.printDeck(deck);
        ArrayList<Player> players = new ArrayList<>();

        if (playersAmount > 1 && playersAmount < 11) {
            for (int i = 0; i < playersAmount; i++) {
                players.add(i, new Player(names.get(i)));
                if (names.get(i).startsWith("(BOT)", names.get(i).length() - 5)) {
                    players.get(i).setBot();
                }
                var miniDeck = players.get(i).getPlayerHand();

                for (int j = 0; j < 7; j++) {
                    players.get(i).addCard(deck.get(j * playersAmount + i));
                }
                miniDeck.sort(Card.sorting);
            }
            for (var p : players) {
                for (var c : p.getPlayerHand()) {
                    deck.remove(c);
                }
            }
        } else {
            System.out.println("Invalid amount of players: " + playersAmount + "\nRequired amount: 2-10");
        }
        players.add(players.size(), new Player("UNDRAWN_CARDS"));
        players.get(players.size() - 1).setPlayerHand(deck);
        players.add(players.size(), new Player("DISCARD_PILE"));

        return players;
    }

    public Card drawCard(List<Card> deck, int turn) {
        Queue<Card> gDeck = new LinkedList<>(deck);
        Card first = deck.get(0);

        if (turn == 1) {
            while (true) {
                if (first.getSpecialEffect() == Card.SpecialEffect.DRAW4) {
                    System.out.println("The first card drawn is " + Card.SpecialEffect.DRAW4 + ". Shuffling deck in process.");
                    Collections.shuffle(deck);
                    first = deck.get(0);
                } else {
                    break;
                }
            }
        }
        first = gDeck.poll();
        return first;
    }

    public int amountOfCards(List<Player> players, int index) {
        return players.get(index).getPlayerHand().size();
    }

    public Player nextPlayer(List<Player> players, int next) {
        Collections.rotate(players, next); // -1 by default, -2 for Skip
        return players.get(0);
    }

    public boolean hasCardsToPlay(Card card, Player player) {

        for (var c : player.getPlayerHand()) {
            if (areMatching(card, c)) return true;
        }
        return false;
    }

    public boolean areMatching(Card card, Card card2) {
        return Card.matchingColor.compare(card2, card) == 0 ||
                Card.matchingValue.compare(card2, card) == 0 ||
                (Card.matchingSE.compare(card2, card) == 0 &&
                        (card.getSpecialEffect() != Card.SpecialEffect.NONE &&
                                card2.getSpecialEffect() != Card.SpecialEffect.NONE));
    }
}