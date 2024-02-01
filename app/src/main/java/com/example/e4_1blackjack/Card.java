package com.example.e4_1blackjack;

public class Card {
    //Club, Spades, Hearts, Diamonds
    private Suit suit;
    //2-10, J, Q, K, A
    private Rank rank;

    public Card(Suit suit, Rank rank) {
        setSuit(suit);
        setRank(rank);
    }

    public Suit getSuit() {
        return suit;
    }

    private void setSuit(Suit suit) {
        if (suit != null){
            this.suit = suit;
        }
    }

    public Rank getRank() {
        return rank;
    }

    private void setRank(Rank rank) {
        if (rank != null){
            this.rank = rank;
        }
    }

    public int getValue(){
        switch (getRank()){
            case _2:
                return 2;
            case _3:
                return 3;
            case _4:
                return 4;
            case _5:
                return 5;
            case _6:
                return 6;
            case _7:
                return 7;
            case _8:
                return 8;
            case _9:
                return 9;
            case _10:
            case JACK:
            case QUEEN:
            case KING:
                return 10;
            case ACE:
                return 11;
        }
        return 0;
    }

    public String getImage(){
        return getRank().toString().toLowerCase() + "_of_" + getSuit().toString().toLowerCase();
    }

    @Override
    public String toString() {
        // 2 of Hearts
        return getRank().toString() + " of " + getSuit().toString();
    }
}
