package com.example.e4_1blackjack;

public class Player {
    private final int MAX_HAND_COUNT = 15;
    private String name;
    private int cash;
    private Card[] hand;
    private int cardCount = 0;

    public Player(String name) {
        setName(name);
        setCash(500);
        hand = new Card[MAX_HAND_COUNT];
    }

    public Card[] getHand(){
        return hand;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        if (name != null && name.length() >= 2){
            this.name = name;
        }
    }

    public int getCash() {
        return cash;
    }

    private void setCash(int cash) {
            this.cash = cash;
    }
    public void hitTheATM(){
        setCash(getCash() + 500);
    }

    public void adjustCash(int amount){
        setCash(getCash() + amount);
    }

    public void receiveCard(Card card){
        if (cardCount < MAX_HAND_COUNT) {
            hand[cardCount++] = card;
        }
    }

    public int getHandSum(){
        boolean hasAce = false;
        //todo handle ace
        int cardSum = 0;
        for (int i = 0; i < cardCount; i++) {
            Card c = hand[i];
            if (c.getRank().equals(Rank.ACE)) {
                hasAce = true;
            }
            cardSum += c.getValue();
        }

        //todo:if ace cause bust, subtract 10 from total

        return cardSum;
    }

    public void resetHand(){
        hand = new Card[MAX_HAND_COUNT];
        cardCount = 0;
    }
}
