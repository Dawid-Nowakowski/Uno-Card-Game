package Mechanics;


public record Card(CardColor cardColor, SpecialEffect specialEffect, int value) {

    public enum CardColor {
        BLUE, GREEN, RED, YELLOW, NONE;

        public String getColor() {
            return (new String[]{"\uD83D\uDD35", "\uD83D\uDFE2", "\uD83D\uDD34", "\uD83D\uDFE1", ""})[this.ordinal()];
        }
    }

    public enum SpecialEffect {
        NONE, REVERSE, SHUFFLE, WILD, SKIP, DRAW2, DRAW4;

        public String getSpecial() {
            return (new String[]{"", "\u21BA", "\uD83C\uDF00", "\uD83C\uDF08", "\uD83D\uDEC7",
                    "+2\uD83C\uDF08", "+4\uD83C\uDF08"})[this.ordinal()];
        }
    }

    @Override
    public String toString() {
        if (specialEffect() == SpecialEffect.NONE) {
            return "%s%d".formatted(cardColor.getColor(), value);
        } else if (specialEffect() == SpecialEffect.DRAW2 || specialEffect() == SpecialEffect.REVERSE
                || specialEffect() == SpecialEffect.SKIP) {
            return "%s%s(%d)".formatted(cardColor.getColor(), specialEffect.getSpecial(), value);
        } else {
            return "%s(%d)".formatted(specialEffect.getSpecial(), value);
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
        }
        else {
                if (specialEffect == SpecialEffect.DRAW2 || specialEffect == SpecialEffect.SKIP ||
                        specialEffect == SpecialEffect.REVERSE) {
                    return new Card(cardColor, specialEffect, 20);
                }
            }
        System.out.println("Invalid Special Card: " + specialEffect);
        return null;
    }
}
