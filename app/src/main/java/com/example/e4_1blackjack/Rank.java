package com.example.e4_1blackjack;

public enum Rank {
    _2("Two"),
    _3("Three"),
    _4("Four"),
    _5("Five"),
    _6("Six"),
    _7("Seven"),
    _8("Eight"),
    _9("Nine"),
    _10("Ten"),
    JACK("Jack"),
    QUEEN("Queen"),
    KING("King"),
    ACE("Ace");
    private final String prettyName;

    Rank(String prettyName){
        this.prettyName = prettyName;
    }

    @Override
    public String toString() {
        return prettyName;
    }
}
