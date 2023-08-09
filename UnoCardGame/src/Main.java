import Mechanics.Card;
import Mechanics.GamesLogic;
import Mechanics.Player;
import Scenes.GameScreen;
import Scenes.Info;

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

        int turn = 1;
        List<Player> players = gamesLogic.dealCards(deck, amount, shuffled);
        players.forEach(System.out::println);
        List<Card> unplayedCards = players.get(players.size() - 2).getPlayerHand();
        Card draw = gamesLogic.drawCard(players.get(players.size() - 2).getPlayerHand(), turn);

        System.out.printf("%nGame begins! %s has to match first drawn card: %s%n", shuffled.get(0), draw);
        gameScreen.chooseCard(draw, players.get(0), unplayedCards);
    }
}

