package Mechanics;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public record Card(CardColor cardColor, SpecialEffect specialEffect, int value) {

    public static Comparator<Card> sorting = Comparator.comparing(Card::cardColor)
            .thenComparing(Card::value)
            .thenComparing(Card::specialEffect);

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
        if (specialEffect() == SpecialEffect.NONE) {
            return "%s%d".formatted(cardColor.getColor(), value);
        } else if (specialEffect() == SpecialEffect.DRAW2 || specialEffect() == SpecialEffect.REVERSE
                || specialEffect() == SpecialEffect.SKIP) {
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

    public static List<Card> getDeck() {
        CardColor[] cardColors = CardColor.values();
        SpecialEffect[] specialEffects = SpecialEffect.values();
        List<Card> deck = new ArrayList<>();

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
        int cardsInColor = (int) Math.ceil((double) deck.size() / 4);
        for (int i = 0; i < 4; i++) {

            int startIndex = i * cardsInColor;
            int endIndex = startIndex + cardsInColor;

            if (endIndex > deck.size()) {
                endIndex = deck.size();
            }
            deck.subList(startIndex, endIndex).forEach(c -> System.out.print(c + ", "));
            System.out.println();
        }
    }
}