package com.example.e4_1blackjack;

import java.util.Random;

public class Deck {
    private Card[] cards;
    private int cardCount = 0;

    public Deck(){
        cards = new Card[52];
        populate();
        shuffleDeck();
    }

    private void populate() {
        for (int suit = 0; suit < 4; suit++) {
            Suit suitValue = getSuit(suit);
            for (int rank = 0; rank < 13; rank++) {
                Rank rankValue = getRank(rank);
                Card newCard = new Card(suitValue, rankValue);
                cards[cardCount++] = newCard;
            }
        }
    }

    public void shuffleDeck(){
        Random random = new Random();

        for (int i = cards.length - 1; i > 0; i--) {
            int randomCardIndex = random.nextInt(i + 1);
            //swap
            Card c = cards[randomCardIndex];
            cards[randomCardIndex] = cards[i];
            cards[i] = c;
        }
    }

    public Card drawACard(){
        Card[] tempDeck = new Card[cards.length - 1];
        Card drawnCard = cards[cards.length - 1];
        for (int i = 0; i < cards.length - 1; i++) {
            tempDeck[i] = cards[i];
        }
        cards = tempDeck;
        return drawnCard;
    }

    private Suit getSuit(int suit){
        Suit suitValue;
        switch (suit){
            case 0:
                suitValue = Suit.DIAMOND;
                break;
            case 1 :
                suitValue = Suit.SPADE;
                break;
            case 2:
                suitValue = Suit.CLUB;
                break;
            default:
                suitValue = Suit.HEART;
                break;
        }
        return suitValue;
    }

    private Rank getRank(int rank){
        Rank rankValue;
        switch (rank){
            case 0:
                rankValue = Rank._2;
                break;
            case 1:
                rankValue = Rank._3;
                break;
            case 2:
                rankValue = Rank._4;
                break;
            case 3:
                rankValue = Rank._5;
                break;
            case 4:
                rankValue = Rank._6;
                break;
            case 5:
                rankValue = Rank._7;
                break;
            case 6:
                rankValue = Rank._8;
                break;
            case 7:
                rankValue = Rank._9;
                break;
            case 8:
                rankValue = Rank._10;
                break;
            case 9:
                rankValue = Rank.JACK;
                break;
            case 10:
                rankValue = Rank.QUEEN;
                break;
            case 11:
                rankValue = Rank.KING;
                break;
            default:
                rankValue = Rank.ACE;
                break;
        }
        return rankValue;
    }


}
