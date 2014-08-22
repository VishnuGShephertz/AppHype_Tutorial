package com.apphype.TicTacToe;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shephertz.android.apphype.connector.AppHypeException;
import com.shephertz.android.apphype.sdk.AppHype;
import com.shephertz.android.apphype.sdk.AppHype.AppHypeListener;
import com.shephertz.android.apphype.util.AdCode;

public class GameActivity extends Activity implements AppHypeListener {

	RelativeLayout grid_view;
	private char myPlayer = 'X';
	private char cmpPlayer = '0';
	private char currentPlayer;
	private TextView turnText;
	private char[][] gameBoard = new char[3][3];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game);
		turnText=(TextView)findViewById(R.id.turn_text);
		init();
		AppHype.setAppHypeListener(this);
		AppHype.intialize(
				this,
				"Your API Key",
				"Your Secret Key");
		AppHype.enableLogs();
		AppHype.preLoadAd(AdCode.Video);
		AppHype.preLoadAd(AdCode.Interstitial);
		

	}
	

	private void updateStatus(){
		if(currentPlayer==myPlayer){
			turnText.setText("Play Your Turn");
		}
		else{
			turnText.setText("Play Computer Turn");
		}
		
	}
	private void init() {
		currentPlayer=myPlayer;
		updateStatus();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				gameBoard[i][j] = '-';
				((ImageButton) this.findViewById(getImageId(i * 3 + j))).setImageResource(R.drawable.empty_cell);
			}
		}
	}

	public void onCellClicked(View view) {
		int no = getCellIndexFromView(view);
		playGame(no / 3, no % 3);
	}

	private int getCellIndexFromView(View view) {
		int viewId = view.getId();
		switch (viewId) {
		case R.id.cell_00:
			return 0;
		case R.id.cell_01:
			return 1;
		case R.id.cell_02:
			return 2;
		case R.id.cell_10:
			return 3;
		case R.id.cell_11:
			return 4;
		case R.id.cell_12:
			return 5;
		case R.id.cell_20:
			return 6;
		case R.id.cell_21:
			return 7;
		case R.id.cell_22:
			return 8;
		}
		return 0;
	}
	private void updateTurn(){
		if(currentPlayer==cmpPlayer){
			currentPlayer=myPlayer;
		}
		else{
			currentPlayer=cmpPlayer;
		}
		updateStatus();
	}

	private void playGame(int i, int j) {

		if (gameBoard[i][j] == '-') {
			gameBoard[i][j] = currentPlayer;
			updateUI(i, j);
		}
		if(Util.hasEmptyPlace(gameBoard)){
		handleGameOver();
		updateTurn();
		}
		else{
			displayAd(true);
			showResultDialog("Game is Drawn");
		}
	}
	
	private void updateUI(int index, int j) {
		setupButton(currentPlayer,
				(ImageButton) this.findViewById(getImageId(index * 3 + j)));
	}

	private int getImageId(int index) {
		switch (index) {
		case 0:
			return R.id.cell_00;
		case 1:
			return R.id.cell_01;
		case 2:
			return R.id.cell_02;
		case 3:
			return R.id.cell_10;
		case 4:
			return R.id.cell_11;
		case 5:
			return R.id.cell_12;
		case 6:
			return R.id.cell_20;
		case 7:
			return R.id.cell_21;
		case 8:
			return R.id.cell_22;
		}
		return 0;
	}

	private void setupButton(char boardTile, ImageButton button) {
		if (boardTile == 'X') {
			button.setImageResource(R.drawable.cross_cell);
		} else {
			button.setImageResource(R.drawable.circle_cell);
		}
	}

	private void handleGameOver() {
		char r = Util.checkForWin(gameBoard);
		if (r != '-') {
			if (r == myPlayer) {
				displayAd(true);
				showResultDialog("Congrats, You Win");

			} else {
				displayAd(false);
				showResultDialog("Oops, You Loose");

			}
		}
	}

	private void displayAd(boolean isWon) {
		if (isWon) {
			if (AppHype.isAvailable(AdCode.Video)) {
				AppHype.showAd(this, AdCode.Video);
			} else if (AppHype.isAvailable(AdCode.Interstitial)) {

				AppHype.showAd(this, AdCode.Interstitial);
			}
		} else {
			if (AppHype.isAvailable(AdCode.Interstitial)) {

				AppHype.showAd(this, AdCode.Interstitial);
			}
			if (AppHype.isAvailable(AdCode.Video)) {
				AppHype.showAd(this, AdCode.Video);
			}
		}
	}

	private void showResultDialog(String message) {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(message).setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						init();
					}
				});
		AlertDialog alert = builder.create();
		alert.show();

	}
	
	@Override
	public void onAdAvailable(AdCode adcode) {
		String message="onAdAvailable :  ";
		if(adcode==AdCode.Interstitial)
			message+="Interstitial Ad is Available";
		else if(adcode==AdCode.Video)
			message+="Video Ad is Available";
		displayMessage(message);
	}

	@Override
	public void onShow(AdCode adcode) {
		String message="onShow :  ";
		if(adcode==AdCode.Interstitial)
			message+="Interstitial Ad is showing";
		else if(adcode==AdCode.Video)
			message+="Video Ad is showing";
		displayMessage(message);
	}

	@Override
	public void onHide(AdCode adcode) {
		String message="onHide :  ";
		if(adcode==AdCode.Interstitial)
			message+="Interstitial Ad is Hide";
		else if(adcode==AdCode.Video)
			message+="Video Ad is Hide";
		displayMessage(message);
	}

	@Override
	public void onFailedToShow(AppHypeException appHypeEx) {
		displayMessage("onFailedToShow :  "+appHypeEx.toString());
	}

	@Override
	public void onIntegrationError(AppHypeException appHypeEx) {
		displayMessage("onFailedToShow :  "+appHypeEx.toString());

	}
	@Override
	public void onFailedToLoad(AppHypeException appHypeEx) {
		displayMessage("onFailedToLoad :  "+appHypeEx.toString());
	}
private void displayMessage(final String error) {
		
		GameActivity.this.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(GameActivity.this, error, Toast.LENGTH_SHORT)
				.show();
			}
		});
	}
	
}
