package Mechanics;

import java.util.ArrayList;

public class Player {
    private String name;
    private int score;
    private ArrayList<Card> playerHand;

    private boolean isBot;

    public Player(String name) {
        this.name = name;
        score = 0;
        playerHand = new ArrayList<>();
        isBot = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public ArrayList<Card> getPlayerHand() {
        return playerHand;
    }

    public void setPlayerHand(ArrayList<Card> playerHand) {
        this.playerHand = playerHand;
    }

    public void addCard(Card card) {
        getPlayerHand().add(card);
    }

    public boolean isBot() {
        return isBot;
    }

    public void setBot() {
        isBot = true;
    }

    @Override
    public String toString() {
        return name + ", score= " + score + "hand=" + playerHand;
    }
}