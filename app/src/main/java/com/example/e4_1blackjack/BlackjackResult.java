package com.example.e4_1blackjack;

public class BlackjackResult {
    private Player player;

    private int winnings;

    private boolean won;

    private boolean gotBlackjack;

    public BlackjackResult(Player player, int winnings, boolean won, boolean gotBlackjack) {
        setPlayer(player);
        setWinnings(winnings);
        setWon(won);
        setGotBlackjack(gotBlackjack);
    }

    public Player getPlayer() {
        return player;
    }

    private void setPlayer(Player player) {
        if (player != null) {
            this.player = player;
        }
    }

    public int getWinnings() {
        return winnings;
    }

    private void setWinnings(int winnings) {
        this.winnings = winnings;
    }

    public boolean isWon() {
        return won;
    }

    private void setWon(boolean won) {
        this.won = won;
    }

    public boolean isGotBlackjack() {
        return gotBlackjack;
    }

    private void setGotBlackjack(boolean gotBlackjack) {
        this.gotBlackjack = gotBlackjack;
    }
}
