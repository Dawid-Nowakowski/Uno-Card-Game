import Mechanics.Card;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        Card[] card = {
        Card.getNumericCard(Card.CardColor.BLUE, 9),
        Card.getNumericCard(Card.CardColor.BLUE, 10),
        Card.getSpecialCard(Card.CardColor.GREEN, Card.SpecialEffect.REVERSE),
        Card.getSpecialCard(Card.CardColor.NONE, Card.SpecialEffect.DRAW4),
        Card.getSpecialCard(Card.CardColor.NONE, Card.SpecialEffect.REVERSE),
        Card.getSpecialCard(Card.CardColor.RED, Card.SpecialEffect.DRAW4)};

        List<Card> cards = new ArrayList<>(Arrays.asList(card));

        cards.forEach(System.out::println);
    }
}