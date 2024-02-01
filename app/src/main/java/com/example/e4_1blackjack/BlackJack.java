package com.example.e4_1blackjack;

import androidx.annotation.NonNull;

public class BlackJack {
    public final int MAX_PLAYER = 1;

    public final int WINNING_AMOUNT = 21;

    public final int DEALER_STAND_MIN = 17;

    public final int INITIAL_CARD_COUNT = 2;

    private int playerBet = 0;

    private Deck deck;

    private Player dealer;

    private Player[] players;

    private int playerCount = 0;

    private Player currentPlayer;

    public BlackJack(int numPlayers) {
        players = new Player[MAX_PLAYER];
        setDeck(new Deck());
        setDealer(new  Player("Dealer"));
        for (int i = 0; i < numPlayers; i++) {
            addPlayer(new Player("Player " + (i + 1)));
        }
        setCurrentPlayer(players[0]);
        dealStartCard();

        playerBet = 0;
    }

    public void dealStartCard(){
        //deal 2 cards to every player and dealer
        for (int numCard = 0; numCard < INITIAL_CARD_COUNT; numCard++) {
            for (int playerNum = 0; playerNum < playerCount; playerNum++) {
                dealCard(players[playerNum]);
            }
            dealCard(dealer);
        }
    }



    public Player getDealer() {
        return dealer;
    }

    private void setDealer(Player dealer) {
        if (dealer != null) {
            this.dealer = dealer;
        }
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    private void setCurrentPlayer(Player currentPlayer) {
        if (currentPlayer != null) {
            this.currentPlayer = currentPlayer;
        }
    }

    private Deck getDeck() {
        return deck;
    }

    public Player getPlayerOne(){
        return players[0];
    }

    private void setDeck(Deck deck) {
        if (deck != null) {
            this.deck = deck;
        }
    }

    public void nextRound(){
        setDeck(new Deck());
        for (Player player : players) {
            player.resetHand();
        }
        getDealer().resetHand();
        dealStartCard();
        currentPlayer = players[0];
        //playerBet = 0;
    }

    public void addPlayer(Player player){
        if (playerCount < MAX_PLAYER){
            players[playerCount++] = player;
        }
    }

    public void dealCard(Player player){
        Card drawnCard = getDeck().drawACard();
        player.receiveCard(drawnCard);
    }

    /**
     * This method accepts a bet, if valid , subtracts the cash from the player.
     * @param amount the amount of the bet
     * @return Boolean true if bet is accepted, false if rejected
     */

    public boolean placeBet(int amount){
        if(amount > 0 && amount <= getPlayerOne().getCash()) {
            getPlayerOne().adjustCash(-amount);
            //Todo: When move bet to player
            playerBet = amount;
            return true;
        }else {
            return false;
        }
    }

    public void dealerTurn(){
        boolean dealerBusted;
        for (int i = 0; i < 200; i++){
            dealerBusted = checkForBust();
            if (dealerBusted){
                break;
            } else if (getDealer().getHandSum() >= DEALER_STAND_MIN && getDealer().getHandSum() <= WINNING_AMOUNT) {
                //dealer stand
                break;
            }
            hitMe();
        }
    }

    /**
     * Changes the turn to the next player, or dealer if none are left
     */

    private void changeTurn(){
        int index = -1;
        for (int i = 0; i < playerCount; i++) {
            if (players[i] == getCurrentPlayer()) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            if (index == players.length - 1) {
                setCurrentPlayer(getDealer());
            } else {
                setCurrentPlayer(players[index + 1]);
            }
        }
    }

    public void stand(){
        changeTurn();
    }

    public boolean isDealerTurn(){
        return getCurrentPlayer() == getDealer();
    }

    /**
     * This give the current player a card and returns the card that was drawn
     * @return The card that was drawn
     */

    public Card hitMe(){
        Card card = getDeck().drawACard();
        getCurrentPlayer().receiveCard(card);
        return card;
    }

    /**
     * Checks if the current player has busted
     * @return Boolean true if current player has busted, otherwise false
     */

    public boolean checkForBust(){
        return getCurrentPlayer().getHandSum() > WINNING_AMOUNT;
    }

    public boolean checkForBlackJack(Player player){
        return player.getHandSum() == WINNING_AMOUNT;
    }

    public NaturalBlackjackResult[] checkForNaturalBlackJack(){
        NaturalBlackjackResult[] results = new NaturalBlackjackResult[playerCount];
        for (int i = 0; i < playerCount; i++) {
            NaturalBlackjackResult pResult = new NaturalBlackjackResult();
            Player p = players[i];
            pResult.setPlayer(p);
            pResult.setHasBlackjack(checkForBlackJack(p));
            //todo: when bet moved to player
            if (pResult.HasBlackjack()) {
                pResult.setWinnings(playerBet * 3);
                p.adjustCash(pResult.getWinnings());
            }
            results[i] = pResult;
        }
        return results;
    }

    public BlackjackResult[] checkForWinner(){
        BlackjackResult[] allPlayerResult = new BlackjackResult[playerCount];
        for (int i = 0; i < playerCount; i++) {
            Player p = players[i];
            BlackjackResult pResult = calculatePayout(p);
            allPlayerResult[i] = pResult;
        }
        return allPlayerResult;
    }

    /**
     * Calculate payout and updates player cash
     * tie = equal bet
     * win = 2x bet
     * Natural BlackJack = 3x bet
     * lose = lose bet
     * @return The payout amount
     */
    private BlackjackResult calculatePayout(Player player){
        //tie = bet
        //win = 2x
        //blackjack = 3x
        //lose = kicked out
        int lastPayout = 0;
        boolean playerWon = true;
        boolean gotBlackjack = false;

        if (player.getHandSum() == getDealer().getHandSum()){
            lastPayout = playerBet;
        } else if (checkForBlackJack(player)){
            lastPayout = (playerBet * 3);
            gotBlackjack = true;
        } else if (getDealer().getHandSum() < player.getHandSum() && playerBet <= WINNING_AMOUNT) {
            lastPayout = (playerBet * 2);
        } else if (getDealer().getHandSum() > WINNING_AMOUNT){
            lastPayout = (playerBet * 2);
        } else {
            //Dealer got blackjack
            //dealer wins
            //we bust
            lastPayout = (-playerBet);
            playerWon = false;
        }
        if (playerWon) {
            player.adjustCash(lastPayout);
        }
        return new BlackjackResult(player,lastPayout,playerWon,gotBlackjack);
    }
}
