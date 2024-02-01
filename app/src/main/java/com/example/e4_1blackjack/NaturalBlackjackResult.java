package com.example.e4_1blackjack;

public class NaturalBlackjackResult {
    private Player player;
    private boolean hasBlackjack;

    private int winnings;

    public int getWinnings() {
        return winnings;
    }

    public void setWinnings(int winnings) {
        this.winnings = winnings;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        if (player != null) {
            this.player = player;
        }
    }

    public boolean HasBlackjack() {
        return hasBlackjack;
    }

    public void setHasBlackjack(boolean hasBlackjack) {
        this.hasBlackjack = hasBlackjack;
    }
}
