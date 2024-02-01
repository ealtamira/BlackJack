package com.example.e4_1blackjack;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

@SuppressWarnings("all")
public class MainActivity extends AppCompatActivity {
    private TextView tvMyCash,tvPlayerLabel, tvDealerLabel, tvBet, tvGameFeed;
    private EditText etBet;
    private LinearLayout dealerCardList, playerCardList;
    BlackJack game;
    boolean validBet = true;

    Button btnHitMe, btnStand, btnPlaceBet, btnStartNewGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onInit();
        game = new BlackJack(1);
        toggleUI(true, true, true);
        updateUI("Hit or Stand");

    }

    public void onStartGame(View v){
        String betAmounts = etBet.getText().toString();
        if (betAmounts.length() > 0) {
            int betAmount = Integer.parseInt(betAmounts);
            boolean validBet = game.placeBet(betAmount);
            if (validBet){
                toggleUI(true,true,false);
                this.validBet = true;
            } else {
                this.validBet = false;
            }
            updateUI("");
            tvBet.setText("You MUST! Place Your Bet!");
        }

        if (game.checkForBlackJack(game.getPlayerOne())){
            NaturalBlackjackResult[] naturalReasult = game.checkForNaturalBlackJack();
            StringBuilder naturalWinner = new StringBuilder();
            for (int i = 0; i < naturalReasult.length; i++) {
                NaturalBlackjackResult pResult = naturalReasult[i];
                StringBuilder pWinner = new StringBuilder();
                //player1 got a natural blackjack($400)
                pWinner.append(pResult.getPlayer().getName());
                pWinner.append(" got a NATURAL BLACKJACK! ($");
                pWinner.append(pResult.getWinnings());
                pWinner.append(")");
                naturalWinner.append(pWinner);
            }
            updateUI(naturalWinner.toString());
            btnStartNewGame.setVisibility(View.VISIBLE);
            toggleUI(false,false,false);
        } else if (game.checkForBlackJack(game.getDealer())) {

            updateUI("You LOSE! Dealer has 21!");
            btnStartNewGame.setVisibility(View.VISIBLE);
            toggleUI(false,false,false);
        }
    }

    public void onNewGame(View v){
        game.nextRound();
        toggleUI(false,false,true);
        btnStartNewGame.setVisibility(View.GONE);
    }

    public void onStand(View v){
        String standText = game.getCurrentPlayer().getName() + " stands!";
        game.stand();
        updateUI(standText);

        if (game.isDealerTurn()){
            dealerTurn();
            //standText = "Dealer stands!";
        }

    }

    public void dealerTurn(){
        game.dealerTurn();

        BlackjackResult[] allPlayerResult = game.checkForWinner();

        StringBuilder allPlayerWinnings = new StringBuilder();

        for (int i = 0; i < allPlayerResult.length; i++) {
            StringBuilder finalText = new StringBuilder();
            BlackjackResult r = allPlayerResult[i];
            //finalText.append("Player1: Won ($200) [21]");
            finalText.append(r.getPlayer().getName());
            finalText.append(": ");
            finalText.append(r.isWon() ? "Won " : "Lost ");
            finalText.append("($");
            finalText.append(r.getWinnings());
            finalText.append(")");
            finalText.append(r.isGotBlackjack() ? "[21]\n" : "\n");
            allPlayerWinnings.append(finalText);
        }
        updateUI(allPlayerWinnings.toString());
        btnStartNewGame.setVisibility(View.VISIBLE);
        toggleUI(false,false,false);
    }

    public void onHit(View v){
        Card cardDrawn = game.hitMe();
        if(game.checkForBust()){
            toggleUI(false, false, false);
            btnStartNewGame.setVisibility(View.VISIBLE);
            BlackjackResult[] results = game.checkForWinner();
            //Todo Maybe bug
            updateUI("Busted! Game Over! (" + cardDrawn.toString() + ") You Earned: $" + results[0].getWinnings());
        } else {
            updateUI("Player took a card (" + cardDrawn.toString() + ")");
        }

    }

    private void displayCards(Card[] hand, LinearLayout layout){
        layout.removeAllViews();
        for (int i = 0; i < hand.length; i++) {
            if(hand[i] == null){
                break;
            }
            ImageView image = new ImageView(this);
            ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(275, 400);
            params.setMargins(10,10,10,10);
            image.setLayoutParams(params);
            image.setMaxWidth(275);
            image.setImageResource(getResources().getIdentifier(hand[i].getImage(), "drawable", getPackageName()));
            //image.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ace_of_clubs, null));
            //Adds the view to the layout
            layout.addView(image);
        }
    }

    public void updateUI(String gameFeed){
        tvGameFeed.setText(gameFeed);
        tvMyCash.setText("Your Cash: $" + game.getPlayerOne().getCash() + (validBet ? "" : " - Invalid Bet"));
        displayCards(game.getDealer().getHand(), dealerCardList);
        displayCards(game.getPlayerOne().getHand(), playerCardList);

        //Todo: Modify when adding more players
    }

    public void toggleUI(boolean enableHitMe, boolean enableStand, boolean showBet){
        btnHitMe.setEnabled(enableHitMe);
        btnStand.setEnabled(enableStand);



        int betVisibility = showBet ? View.VISIBLE : View.GONE;
        int gameVisibility = showBet ? View.GONE : View.VISIBLE;
        //Show Bet
        tvBet.setVisibility(betVisibility);
        etBet.setVisibility(betVisibility);
        btnPlaceBet.setVisibility(betVisibility);
        //Show Game
        dealerCardList.setVisibility(gameVisibility);
        playerCardList.setVisibility(gameVisibility);
        tvGameFeed.setVisibility(gameVisibility);
        btnStand.setVisibility(gameVisibility);
        btnHitMe.setVisibility(gameVisibility);
        tvDealerLabel.setVisibility(gameVisibility);
        tvPlayerLabel.setVisibility(gameVisibility);

    }

    public void onInit() {
        tvMyCash = findViewById(R.id.tvMyCash);

        dealerCardList = findViewById(R.id.dealerCardList);
        playerCardList = findViewById(R.id.playerCardList);

        etBet = findViewById(R.id.etBet);
        tvBet = findViewById(R.id.tvBet);
        tvGameFeed = findViewById(R.id.tvGameFeed);

        btnHitMe = findViewById(R.id.btnHitMe);
        btnStand = findViewById(R.id.btnStand);
        btnStartNewGame = findViewById(R.id.btnStartNewGame);
        btnStartNewGame.setVisibility(View.GONE);
        btnPlaceBet = findViewById(R.id.btnPlaceBet);

        tvDealerLabel = findViewById(R.id.tvDealerLabel);
        tvPlayerLabel = findViewById(R.id.tvPlayerLabel);
    }

}