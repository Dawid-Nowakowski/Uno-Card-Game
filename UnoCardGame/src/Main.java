import Mechanics.Card;
import Mechanics.GamesLogic;
import Mechanics.Player;
import Scenes.GameScreen;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        playGame();

    }

    public static void playGame() {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Card> deck = Card.getDeck();
        GamesLogic gamesLogic = new GamesLogic();
        GameScreen gameScreen = new GameScreen();
        gameScreen.startGameWindow();
        int amount = gameScreen.getValidAmount(scanner);
        int rPlayers = gameScreen.choosePlayersAmount(amount);

        System.out.printf("%d player(s) will play the game, and %d BOT(s).%n", ++rPlayers, amount - rPlayers);
        List<String> playersNames = gameScreen.getPlayersNames(rPlayers, (amount - rPlayers));
        List<String> shuffled = gameScreen.shuffleSeats(playersNames);
        ArrayList<Player> playersAndDecks = gamesLogic.setPlayers(deck, amount, shuffled);

        ArrayList<Card> unplayedCards = gamesLogic.deckOrDiscard(gamesLogic.playersOrDecks(playersAndDecks, 2), 1);
        ArrayList<Card> discardPile = gamesLogic.deckOrDiscard(gamesLogic.playersOrDecks(playersAndDecks, 2), 2);
        ArrayList<Player> players = gamesLogic.playersOrDecks(playersAndDecks, 1);
        Card draw = gameScreen.firstCard(unplayedCards, discardPile, players);

        System.out.printf("%nGame begins! %s has to match first drawn card: %s%n", shuffled.get(0), draw);
        Card lastCard = gameScreen.chooseCard(draw, players.get(0), unplayedCards, discardPile);
        System.out.println(discardPile);

        int score = 0;
        while (true) {
            gameScreen.chooseCard(lastCard, players.get(0), deck, discardPile);
            System.out.println(discardPile);
            if (gamesLogic.amountOfCards(players) == 0) {
                for (Player p : players) {
                    for (Card c : p.getPlayerHand()) {
                        score += c.getValue();
                    }
                }
                players.get(0).setScore(score);
                System.out.println(players.get(0) + " has won this turn! Gains: " + score + " points!");
                break;
            }
            lastCard = gamesLogic.action(lastCard, players.get(1), unplayedCards, discardPile, players);
            Player actualPlayer = gamesLogic.nextPlayer(players, -1);
            System.out.printf("%s turn.", actualPlayer.getName());
        }

    }
}