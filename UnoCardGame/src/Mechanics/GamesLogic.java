package Mechanics;

import java.util.*;

public class GamesLogic {

    public ArrayList<Player> setPlayers(ArrayList<Card> deck, int playersAmount, List<String> names) {
        Collections.shuffle(deck);
        ArrayList<Player> players = new ArrayList<>();

        if (playersAmount > 1 && playersAmount < 11) {
            for (int i = 0; i < playersAmount; i++) {
                players.add(new Player(names.get(i)));
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
        players.add(new Player("UNDRAWN_CARDS"));
        players.get(players.size() - 1).setPlayerHand(deck);
        players.add(new Player("DISCARD_PILE"));

        return players;
    }

    public ArrayList<Player> playersOrDecks(ArrayList<Player> players, int choice) {
        ArrayList<Player> decks = new ArrayList<>();
        if (choice == 1) {
            players.remove(players.get(players.size() - 1));
            players.remove(players.get(players.size() - 1));
            return players;
        } else {
            decks.add(players.get(players.size() - 2));
            decks.add(players.get(players.size() - 1));
            return decks;
        }
    }

    public ArrayList<Card> deckOrDiscard(ArrayList<Player> list, int choice) {
        if (choice == 1) {
            return list.get(0).getPlayerHand(); // returns remaining, unplayed cards
        }
        return list.get(1).getPlayerHand(); // returns empty discard pile
    }

    public ArrayList<Player> dealCards(ArrayList<Card> deck, ArrayList<Player> players) {
        Collections.shuffle(deck);
        for (Player p : players) {
            p.getPlayerHand().clear();
        }
        for (Player p : players) {
            for (int j = 0; j < (deck.size() / players.size()); j++) {
                p.addCard(deck.get(j * players.size() + players.indexOf(p))); // deals each player 7 cards, 1 card every n-time (n - number of players) so such method deals in similar way to dealing each player 1 card at a time
            }
            p.getPlayerHand().sort(Card.sorting);
        }
        deck.clear();
        return players;
    }
    
    public ArrayList<Card> drawCard(ArrayList<Card> deck, ArrayList<Card> discardPile, int cardsToDraw) {


        ArrayList<Card> drawnCards = new ArrayList<>();
        int amountToDrawAfterShuffle = cardsToDraw - deck.size();

        if (deck.size() <= cardsToDraw) {
            drawnCards.addAll(deck);
            deck.clear();
            Card topDiscardPile = discardPile.get(discardPile.size() - 1);
            discardPile.remove(topDiscardPile);
            discardPile.removeIf(c -> c.getValue() == -1);
            deck.addAll(discardPile);
            Collections.shuffle(deck);
            discardPile.clear();
            discardPile.add(topDiscardPile);

            if (amountToDrawAfterShuffle > 0) {
                for (int i = 0; i < amountToDrawAfterShuffle; i++) {
                    drawnCards.add(deck.get(0));
                    deck.remove(deck.get(0));
                }
            }
        } else {
            for (int i = 0; i < cardsToDraw; i++) {
                drawnCards.add(deck.get(0));
                deck.remove(deck.get(0));
            }
        }
        return drawnCards;
    }

    public int amountOfCards(ArrayList<Player> players) {
        return players.get(0).getPlayerHand().size();
    }

    public Player nextPlayer(ArrayList<Player> players, int next) {
        Collections.rotate(players, next); // -1 by default, -2 for Skip
        return players.get(0);
    }

    public Player reverseCard(ArrayList<Player> players) {
        Collections.reverse(players);
        System.out.println("Reverse card has been played. Actual order of play is:");
        for (int i = 1; i < players.size(); i++) {
            if (i == 1) {
                System.out.print(" \u27A1 ");
            }
            System.out.print(players.get(i).getName());
            if (i != (players.size() - 1)) {
                System.out.print(" \u27A1 ");
            }
        }
        System.out.println();
        return players.get(0);
    }

    public Card.CardColor wildCard(int index) {
        return switch (index) {
            case 1 -> Card.CardColor.BLUE;
            case 2 -> Card.CardColor.GREEN;
            case 3 -> Card.CardColor.RED;
            case 4 -> Card.CardColor.YELLOW;
            default -> throw new IllegalArgumentException("Unallowed value: " + index);
        };
    }

    public ArrayList<Player> shuffleCard(ArrayList<Player> players) {
        ArrayList<Card> playersCards = new ArrayList<>();

        for (Player player : players) {
            playersCards.addAll(player.getPlayerHand());
        }
        nextPlayer(players, -1);
        players = dealCards(playersCards, players);
        nextPlayer(players, 1);
        System.out.println("Shuffle card has been played. Each player hand are collected, shuffled and dealt evenly.");
        return players;
    }

    public boolean hasCardsToPlay(Card card, Player player) {

        for (var c : player.getPlayerHand()) {
            if (areMatching(card, c)) return true;
        }
        return false;
    }

    public boolean areMatching(Card card, Card card2) {
        if (Card.matchingColor.compare(card, card2) == 0) {
            return true;
        } else if (Card.matchingSE.compare(card, card2) == 0 &&
                (card.getSpecialEffect() != Card.SpecialEffect.NONE &&
                        card2.getSpecialEffect() != Card.SpecialEffect.NONE)) {
            return true;
        } else if (Card.matchingValue.compare(card, card2) == 0 &&
                card.getSpecialEffect() == Card.SpecialEffect.NONE) {
            return true;
        } else return card2.getValue() > 39;
    }

    public Card action(Card lastCard, Player player, ArrayList<Card> deck, ArrayList<Card> discardPile, ArrayList<Player> players) {

        switch (lastCard.getSpecialEffect()) {
            case DRAW2 -> {
                player.addCards(drawCard(deck, discardPile, 2));
                nextPlayer(players, -1);
            }
            case DRAW4 -> {
                player.addCards(drawCard(deck, discardPile, 4));
                nextPlayer(players, -1);
            }
            case REVERSE -> reverseCard(players);
            case SHUFFLE -> shuffleCard(players);
            case SKIP -> nextPlayer(players, -1);
            default -> {
            }
        }
        return lastCard;
    }

}