package Mechanics;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Card {

    private CardColor cardColor;
    private SpecialEffect specialEffect;
    private int value;

    public Card(CardColor cardColor, SpecialEffect specialEffect, int value) {
        this.cardColor = cardColor;
        this.specialEffect = specialEffect;
        this.value = value;
    }

    public Card(CardColor cardColor){
        this.cardColor = cardColor;
        specialEffect = SpecialEffect.NONE;
        value = -1;
    }

    public CardColor getCardColor() {
        return cardColor;
    }

    public void setCardColor(CardColor cardColor) {
        this.cardColor = cardColor;
    }

    public SpecialEffect getSpecialEffect() {
        return specialEffect;
    }

    public void setSpecialEffect(SpecialEffect specialEffect) {
        this.specialEffect = specialEffect;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static Comparator<Card> sorting = Comparator.comparing(Card::getCardColor)
            .thenComparing(Card::getValue)
            .thenComparing(Card::getSpecialEffect);

    public static Comparator<Card> matchingColor = Comparator.comparing(Card::getCardColor);
    public static Comparator<Card> matchingValue = Comparator.comparing(Card::getValue);
    public static Comparator<Card> matchingSE = Comparator.comparing(Card::getSpecialEffect);

    public enum CardColor {
        BLUE, GREEN, RED, YELLOW, NONE;

        public String getColor() {
            return (new String[]{"\uD83D\uDD35", "\uD83D\uDFE2", "\uD83D\uDD34", "\uD83D\uDFE1", ""})[this.ordinal()];
        }
    }

    public enum SpecialEffect {
        DRAW2, REVERSE, SKIP, DRAW4, SHUFFLE, WILD, NONE;

        public String getSpecial() {
            return (new String[]{"+2", "\u21BA", "\uD83D\uDEC7", "+4\uD83C\uDF08", "\uD83C\uDF00", "\uD83C\uDF08", ""})[this.ordinal()];
        }
    }

    @Override
    public String toString() {
        if (getSpecialEffect() == SpecialEffect.NONE) {
            return "%s%d".formatted(cardColor.getColor(), value);
        } else if (getSpecialEffect() == SpecialEffect.DRAW2 || getSpecialEffect() == SpecialEffect.REVERSE
                || getSpecialEffect() == SpecialEffect.SKIP) {
            return "%s%s".formatted(cardColor.getColor(), specialEffect.getSpecial());
        } else {
            return "%s".formatted(specialEffect.getSpecial());
        }
    }

    public static Card getNumericCard(CardColor cardColor, int number) {
        if (number >= 0 && number < 10) {
            return new Card(cardColor, SpecialEffect.NONE, number);
        }
        System.out.println("Invalid Numeric card: " + number);
        return null;
    }

    public static Card getSpecialCard(CardColor cardColor, SpecialEffect specialEffect) {
        if (cardColor == CardColor.NONE) {
            if (specialEffect == SpecialEffect.DRAW4 || specialEffect == SpecialEffect.SHUFFLE ||
                    specialEffect == SpecialEffect.WILD) {
                return new Card(CardColor.NONE, specialEffect, switch (specialEffect) {
                    case SHUFFLE -> 40;
                    default -> 50;
                });
            }
        } else {
            if (specialEffect == SpecialEffect.DRAW2 || specialEffect == SpecialEffect.SKIP ||
                    specialEffect == SpecialEffect.REVERSE) {
                return new Card(cardColor, specialEffect, 20);
            }
        }
        System.out.println("Invalid Special Card: " + specialEffect);
        return null;
    }

    public static ArrayList<Card> getDeck() {
        CardColor[] cardColors = CardColor.values();
        SpecialEffect[] specialEffects = SpecialEffect.values();
        ArrayList<Card> deck = new ArrayList<>();

        for (CardColor color : cardColors) {
            if (color.ordinal() == 4) {
                break;
            }
            for (int j = 0; j <= 9; j++) {
                deck.add(Card.getNumericCard(color, j));
                if (j != 0) {
                    deck.add(Card.getNumericCard(color, j));
                }
            }
            for (int i = 0; i < 3; i++) {
                SpecialEffect sEffect = specialEffects[i];
                deck.add(Card.getSpecialCard(color, sEffect));
                deck.add(Card.getSpecialCard(color, sEffect));
            }
            deck.add(Card.getSpecialCard(CardColor.NONE, specialEffects[3]));
            deck.add(Card.getSpecialCard(CardColor.NONE, specialEffects[4]));
            deck.add(Card.getSpecialCard(CardColor.NONE, specialEffects[5]));
        }
        return deck;
    }

    public static void printDeck(List<Card> deck) {
        int rows = 4;
        if (deck.size() > 10 && deck.size() < 21) {
            rows = 2;
        } else if (deck.size() > 0 && deck.size() < 11) {
            rows = 1;
        }
        int cardsInColor = (int) Math.ceil((double) deck.size() / rows);


        for (int i = 0; i < rows; i++) {

            int startIndex = i * cardsInColor;
            int endIndex = startIndex + cardsInColor;

            if (endIndex > deck.size()) {
                endIndex = deck.size();
            }
            deck.subList(startIndex, endIndex).forEach(c -> System.out.printf("[%d. %s ] ", (deck.indexOf(c) + 1), c));
            System.out.println();
        }
    }
}