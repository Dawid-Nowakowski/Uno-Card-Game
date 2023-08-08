import Mechanics.Card;
import Scenes.GameScreen;
import Mechanics.GamesLogic;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        List<Card> deck = Card.getDeck();
        GameScreen gameScreen = new GameScreen();

        var decks = GamesLogic.dealCards(deck,gameScreen.startGame());

        decks.forEach(System.out::println);
        System.out.println();
        Card.printDeck(deck);
        System.out.println(deck.size());
    }
}