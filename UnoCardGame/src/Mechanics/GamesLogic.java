package Mechanics;

import java.util.*;

public class GamesLogic {

    public ArrayList<Player> dealCards(ArrayList<Card> deck, int playersAmount, List<String> names) {
        Collections.shuffle(deck);
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

    public ArrayList<Player> playersOrDecks(ArrayList<Player> players, int choice){
        ArrayList<Player> decks = new ArrayList<>();
        if(choice == 1){
            players.remove(players.get(players.size() - 1));
            players.remove(players.get(players.size() - 1));
            return players;
        }else {
            decks.add(players.get(players.size() - 2));
            decks.add(players.get(players.size() - 1));
            return decks;
        }
    }
    public ArrayList<Card> deckOrDiscard(ArrayList<Player> list, int choice){
        if(choice == 1){
            return list.get(0).getPlayerHand();
        }
        return list.get(1).getPlayerHand();
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

    public Card firstCard(ArrayList<Card> deck, ArrayList<Card> discardPile) {
        Card first = deck.get(0);

        while (true) {
            if (first.getSpecialEffect() == Card.SpecialEffect.DRAW4) {
                System.out.println("The first card drawn is " + Card.SpecialEffect.DRAW4 + ". Shuffling deck in process.");
                Collections.shuffle(deck);
                first = deck.get(0);
            } else {
                break;
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
            deck.clear();
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

    public int amountOfCards(ArrayList<Player> players, int index) {
        return players.get(index).getPlayerHand().size();
    }

    public Player nextPlayer(ArrayList<Player> players, int next) {
        Collections.rotate(players, next); // -1 by default, -2 for Skip
        players.forEach(System.out::println);
        return players.get(0);
    }

    public Player reverseCard(ArrayList<Player> players) {
        Collections.reverse(players);
        players.forEach(System.out::println);
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

        return players;
    }

    public boolean hasCardsToPlay(Card card, Player player) {

        for (var c : player.getPlayerHand()) {
            if (areMatching(card, c)) return true;
        }
        return false;
    }

    public boolean areMatching(Card card, Card card2) {
        if (Card.matchingColor.compare(card, card2) == 0 ||
                (Card.matchingSE.compare(card, card2) == 0 &&
                        (card.getSpecialEffect() != Card.SpecialEffect.NONE &&
                                card2.getSpecialEffect() != Card.SpecialEffect.NONE))) {
            return true;
        } else if (Card.matchingValue.compare(card, card2) == 0 &&
                card.getSpecialEffect() == Card.SpecialEffect.NONE) {
            return true;
        } else return card2.getValue() > 39;
    }
}