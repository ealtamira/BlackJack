package com.example.e4_1blackjack;

public enum Suit {
    HEART("Hearts"),
    DIAMOND("Diamonds"),
    CLUB("Clubs"),
    SPADE("Spades");
    private final String prettyName;
    Suit(String prettyName){
        this.prettyName = prettyName;
    }

    @Override
    public String toString() {
        return prettyName;
    }
}
