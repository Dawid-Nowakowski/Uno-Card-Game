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
        System.out.printf("""
                _______
                %s%n""", players.get(players.size() - 1));

        return players;
    }

    public ArrayList<Player> dealCards(ArrayList<Card> deck, ArrayList<Player> players) {
        Collections.shuffle(deck);
        for (Player p : players) {
            p.getPlayerHand().clear();
        }
        for (Player p : players) {
            for (int j = 0; j < (deck.size() / players.size()); j++) {
                p.addCard(deck.get(j * players.size() + players.indexOf(p)));
            }
            p.getPlayerHand().sort(Card.sorting);
        }
        deck.clear();
        return players;
    }

    public Card firstCard(ArrayList<Card> deck, ArrayList<Card> discardPile, int turn) {
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
        deck.remove(first);
        discardPile.add(first);
        return first;
    }

    public ArrayList<Card> drawCard(ArrayList<Card> deck, ArrayList<Card> discardPile, int cardsToDraw) {

        Card topDiscardPile = discardPile.get(discardPile.size() - 1);
        discardPile.remove(topDiscardPile);
        ArrayList<Card> drawnCards = new ArrayList<>();
        int amountToDrawAfterShuffle = cardsToDraw - deck.size();

        if (deck.size() <= cardsToDraw) {
            drawnCards.addAll(deck);
        } else {
            for (int i = 0; i < cardsToDraw; i++) {
                drawnCards.add(deck.get(i));
            }
            for (Card c : drawnCards) {
                deck.remove(c);
            }
        }
        deck.clear();
        deck.addAll(discardPile);
        Collections.shuffle(deck);
        discardPile.clear();
        discardPile.add(topDiscardPile);

        if (amountToDrawAfterShuffle > 0) {
            for (int i = 0; i < amountToDrawAfterShuffle; i++) {
                Card first = deck.get(0);
                drawnCards.add(first);
                deck.remove(first);
            }
        }

        return drawnCards;
    }

    public int amountOfCards(ArrayList<Player> players, int index) {
        return players.get(index).getPlayerHand().size();
    }

    public Player nextPlayer(ArrayList<Player> players, int next) {
        ArrayList<Player> decks = new ArrayList<>(List.of(players.get(players.size() - 2), players.get(players.size() - 1)));
        players.removeAll(decks);
        Collections.rotate(players, next); // -1 by default, -2 for Skip
        players.addAll(decks);
        players.forEach(System.out::println);
        return players.get(0);
    }

    public Player reverseCard(ArrayList<Player> players) {
        ArrayList<Player> decks = new ArrayList<>(List.of(players.get(players.size() - 2), players.get(players.size() - 1)));
        players.removeAll(decks);
        Collections.reverse(players);
        players.addAll(decks);
        players.forEach(System.out::println);
        return players.get(0);
    }

    public ArrayList<Player> shuffleCard(ArrayList<Player> players) {
        ArrayList<Player> decks = new ArrayList<>(List.of(players.get(players.size() - 2), players.get(players.size() - 1)));
        players.removeAll(decks);
        ArrayList<Card> playersCards = new ArrayList<>();

        for (Player player : players) {
            playersCards.addAll(player.getPlayerHand());
        }
        players = dealCards(playersCards, players);
        players.addAll(decks);

        return players;
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