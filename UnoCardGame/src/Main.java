import Mechanics.Card;
import Mechanics.GamesLogic;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        List<Card> deck = Card.getDeck();

        var decks = GamesLogic.dealCards(deck,3);

        decks.forEach(System.out::println);
    }
}