package Scenes;

import Mechanics.Card;
import Mechanics.GamesLogic;

import java.util.*;

public class GameScreen {
    public Scanner scanner = new Scanner(System.in);
    public Random random = new Random();

    public void startGameWindow() {
        List<Card> deck = Card.getDeck();
        System.out.println("""
                Welcome to an Uno-like Card Game.
                           
                You can play against AI or with other players, which you will choose in the following steps.
                To check rules of the game please type ( R ), otherwise enter any value to continue.""");
        char input = scanner.next().charAt(0);

        if (input == 'R' || input == 'r') {
            System.out.println(Info.RULES);
        }
        System.out.println("Please enter the number of players (2-10) you would like to start the game with.");
        int amount = getValidAmount(scanner);
        int rPlayers = choosePlayersAmount(amount);

        System.out.printf("%d player(s) will play the game, and %d BOT(s).%n", ++rPlayers, amount - rPlayers);
        List<String> playersNames = getPlayersNames(rPlayers, (amount - rPlayers));
        List<String> shuffled = shuffleSeats(playersNames);
        Map<String, List<Card>> playersDecks = GamesLogic.dealCards(deck, amount, shuffled);
        Card firstCard = GamesLogic.drawCard(playersDecks.get("DECK"));

        System.out.printf("%nGame begins! %s has to match first drawn card: %s", shuffled.get(0), firstCard);
    }

    private int getValidAmount(Scanner scanner) {
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

    private int choosePlayersAmount(int totalPlayers) {
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

    private List<String> getPlayersNames(int playersAmount, int botsAmount) {

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

    private List<String> shuffleSeats(List<String> names) {
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
        return names;
    }
}