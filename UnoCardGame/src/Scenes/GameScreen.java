package Scenes;

import Mechanics.Card;
import Mechanics.GamesLogic;
import Mechanics.Player;

import java.util.*;

public class GameScreen {
    private final Scanner scanner = new Scanner(System.in);
    private final Random random = new Random();
    private final GamesLogic gamesLogic = new GamesLogic();

    public void startGameWindow() {

        System.out.println("""
                Welcome to an Uno-like Card Game.
                           
                You can play against AI or with other players, which you will choose in the following steps.
                To check rules of the game please type ( R ), otherwise enter any value to continue.""");
        char input = scanner.next().charAt(0);

        if (input == 'R' || input == 'r') {
            System.out.println(Info.RULES);
        }
        System.out.println("Please enter the number of players (2-10) you would like to start the game with.");
    }

    public int getValidAmount(Scanner scanner) {
        int amount;
        while (true) {
            if (scanner.hasNextInt()) {
                amount = scanner.nextInt();
                scanner.nextLine();
                if (amount > 1 && amount < 11) {
                    System.out.printf("%d players will participate in the game.%n", amount);
                    return amount;
                } else {
                    System.out.println("Incorrect amount of players, try again.");
                }
            } else {
                System.out.println("Unallowed value provided. Try again (2 - 10).");
                scanner.nextLine();
            }
        }
    }

    public int choosePlayersAmount(int totalPlayers) {
        System.out.println("Do you want to play with other players (Y) or with BOTs only (N)?");
        char yN;
        do {
            yN = scanner.next().charAt(0);
            if (yN == 'y' || yN == 'Y') {
                System.out.println("Enter with how many other players would you like to play:");
                int rPlayers;
                while (true) {
                    if (scanner.hasNextInt()) {
                        rPlayers = scanner.nextInt();
                        if (rPlayers > 0 && rPlayers < totalPlayers) {
                            return rPlayers;
                        } else {
                            System.out.printf("Incorrect amount of players, try again (1 - %d).%n", totalPlayers - 1);
                        }
                    } else {
                        System.out.printf("Unallowed value provided. Try again (1 - %d).%n", totalPlayers - 1);
                        scanner.next();
                    }
                }
            } else if (yN == 'n' || yN == 'N') {
                System.out.println("All other players will be BOTs.");
                return 0;
            } else {
                System.out.println("Unallowed value. Try again (Y / N)");
            }
        } while (true);
    }

    public List<String> getPlayersNames(int playersAmount, int botsAmount) {

        List<String> names = new ArrayList<>();
        String name;
        List<String> bots = new ArrayList<>(List.of("Alex", "Ann", "Bob", "Carol", "Daniel", "Eric", "Fabio", "Greg",
                "Henry", "Ines", "Johanna", "Kate", "Lara", "Martha", "Nancy", "Oleg", "Peter",
                "Ralph", "Steven", "Zoe"));
        List<String> botNames = new ArrayList<>();

        bots.forEach(s -> botNames.add(s + "(BOT)"));

        System.out.println("We are almost prepared for the game! However, before the game starts, I kindly ask you to enter players' names (3-12 characters).");
        for (int i = 0; i < playersAmount; i++) {
            System.out.println("Enter " + (i + 1) + ". player name: ");
            name = scanner.next();

            while (true) {
                if (name.length() > 12 || name.length() < 3) {
                    System.out.println("Unallowed name.\nTry again and enter name with at least 3 chars, up to 12 max.");
                    name = scanner.next();
                } else if (name.equalsIgnoreCase("deck")) {
                    System.out.println("Unallowed name.\nTry again");
                    name = scanner.next();
                } else if (names.contains(name)) {
                    System.out.println(name + " already in game, try different name.");
                    name = scanner.next();
                } else if (name.matches("[a-zA-Z ]+")) {
                    names.add(name);
                    System.out.printf("\uD83C\uDF1F %s has been added to game%n", names.get(i));
                    break;
                } else {
                    System.out.println("Unallowed name.\nPlayers name can't contain numbers. Try again.");
                    name = scanner.next();
                }
            }
        }
        for (int i = 0; i < botsAmount; i++) {
            name = botNames.get(random.nextInt(0, botNames.size()));
            names.add(name);
            botNames.remove(name);
            System.out.printf("\uD83C\uDF1F %s has been added to game%n", names.get(playersAmount + i));
        }
        return names;
    }

    public List<String> shuffleSeats(List<String> names) {
        Collections.shuffle(names);

        System.out.printf("""
                %nFor an improved experience, just before the first game begins, let's add a little twist and change the order!
                The player who will begin the first game is: \u2B50 %s
                The order for the subsequent turns and games: %n""", names.get(0));
        for (int i = 1; i < names.size(); i++) {
            if (i == 1) {
                System.out.print(" \u27A1 ");
            }
            System.out.print(names.get(i));
            if (i != (names.size() - 1)) {
                System.out.print(" \u27A1 ");
            }
        }
        System.out.println();
        return names;
    }

    public Card chooseCard(Card lastCard, Player player, ArrayList<Card> deck, ArrayList<Card> discardPile) {
        int handSize = player.getPlayerHand().size();
        var playersDeck = player.getPlayerHand();
        String name = player.getName();
        boolean canPlay = gamesLogic.hasCardsToPlay(lastCard, player);

        if (canPlay) {
            if (handSize != 1) {
                System.out.printf("Enter card index to play this turn ( 1 - %d ).%n", handSize);
            } else {
                System.out.println("Enter card index to play this turn ( 1 )");
            }
            Card.printDeck(playersDeck);
            return getCardIndex(lastCard, player, discardPile);

        } else {
            System.out.println("You don't have any legal card to play, draw card.");
            Card.printDeck(playersDeck);
            var cardDrawn = gamesLogic.drawCard(deck, discardPile, 1);
            System.out.println("You have drawn: " + cardDrawn);
            playersDeck.addAll(cardDrawn);

            if (cardDrawn.size() == 1 && gamesLogic.areMatching(lastCard, cardDrawn.get(0))) {
                System.out.println("Card you have drawn matches discard pile top card.");
                Card.printDeck(playersDeck);
                return getCardIndex(lastCard, player, discardPile);
            }
        }
        return lastCard;
    }

    private Card getCardIndex(Card lastCard, Player player, ArrayList<Card> discardPile) {
        int cardNumber;
        int handSize = player.getPlayerHand().size();
        Card playedCard;
        Card rainbowCard;
        String name = player.getName();
        while (true) {
            if (scanner.hasNextInt()) {
                cardNumber = scanner.nextInt() - 1;
                if (cardNumber > -1 && cardNumber < handSize) {
                    if (gamesLogic.areMatching(lastCard, player.getPlayerHand().get(cardNumber))) {
                        playedCard = player.getPlayerHand().get(cardNumber);
                        System.out.printf("%s played card: %s%n", name, playedCard);
                        rainbowCard = checkForRainbow(playedCard);
                        player.getPlayerHand().remove(cardNumber);
                        playedCard = rainbowCard;
                        discardPile.add(playedCard);
                        break;
                    } else {
                        System.out.println("Provided card doesn't match top discard pile card in either value, color or special effect. Try again.");
                    }
                } else {
                    System.out.printf("Card index out of bounds. Try again (1 - %d).%n", handSize);
                }
            } else {
                System.out.printf("Unallowed value provided. Try again (1 - %d).%n", handSize);
                scanner.next();
            }
        }
        return playedCard;
    }

    public Card chooseColor() {
        int index;
        System.out.println("""
                        1. \uD83D\uDD35    2. \uD83D\uDFE2    3. \uD83D\uDD34    4. \uD83D\uDFE1
                Enter a color index to set it as required for the next turn.""");
        while (true) {
            if (scanner.hasNextInt()) {
                index = scanner.nextInt();
                if (index > 0 && index < 5) {
                    Card chosenColor = new Card(gamesLogic.wildCard(index));
                    System.out.println("Chosen color is: " + chosenColor.getCardColor().getColor());
                    return chosenColor;
                } else {
                    System.out.println("Unallowed value, try again.");
                }
            }
        }
    }

    public Card checkForRainbow(Card card){
        switch (card.getSpecialEffect()){
            case DRAW4, SHUFFLE, WILD -> card = chooseColor();
            default -> {}
        }
        return card;
    }

    public Card firstCard(ArrayList<Card> deck, ArrayList<Card> discardPile, ArrayList<Player> players) {
        Card first = deck.get(0);
        System.out.print("The first card drawn is ");
        while (true) {
            if (first.getSpecialEffect() == Card.SpecialEffect.DRAW4) {
                System.out.println(Card.SpecialEffect.DRAW4 + ". Shuffling deck in process.");
                Collections.shuffle(deck);
                first = deck.get(0);
            } else if (first.getSpecialEffect() == Card.SpecialEffect.SKIP) {
                gamesLogic.nextPlayer(players, -1);
                System.out.println(Card.SpecialEffect.SKIP + ". First player skips turn. Next players turn.");
                break;
            } else if (first.getSpecialEffect() == Card.SpecialEffect.DRAW2) {
                players.get(0).addCards(gamesLogic.drawCard(deck, discardPile, 2));
                System.out.println(Card.SpecialEffect.DRAW2 + ". First player draws 2 cards and skips turn. Next players turn.");
                break;
            } else if (first.getSpecialEffect() == Card.SpecialEffect.REVERSE) {
                System.out.println(Card.SpecialEffect.REVERSE);
                gamesLogic.reverseCard(players);
                break;
            } else if (first.getSpecialEffect() == Card.SpecialEffect.SHUFFLE) {
                System.out.println(Card.SpecialEffect.SHUFFLE);
                gamesLogic.shuffleCard(players);
                deck.remove(first);
                discardPile.add(first);
                first = chooseColor();
                break;
            } else if (first.getSpecialEffect() == Card.SpecialEffect.WILD){
                System.out.println(Card.SpecialEffect.WILD);
                deck.remove(first);
                discardPile.add(first);
                first = chooseColor();
                break;
            } else if (first.getSpecialEffect() == Card.SpecialEffect.NONE) {
                break;
            }
        }
        if (first.getValue() != -1) {
            deck.remove(first);
        }
        discardPile.add(first);
        return first;
    }
}