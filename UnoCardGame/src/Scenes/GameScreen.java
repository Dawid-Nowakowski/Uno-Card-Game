package Scenes;
import java.util.Scanner;

public class GameScreen {
    public Scanner scanner = new Scanner(System.in);


    public int startGame() {
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
            return amount;
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

}
