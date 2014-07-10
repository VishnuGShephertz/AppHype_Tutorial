package com.apphype.TicTacToe;

import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.shephertz.android.apphype.sdk.AppHypeAPI;
import com.shephertz.android.apphype.sdk.AppHypeAPI.AppHypeListener;

public class GameActivity extends Activity implements AppHypeListener {

	RelativeLayout grid_view;
	private char myTurn = 'X';
	private char cmpTurn = '0';
	private char[][] ARRAY = new char[3][3];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game);
		AppHypeAPI.setAppHypeListener(this);
		AppHypeAPI
				.intialize(
						this,
						"38f81f40df1e56c2e2acac924ffe0147022fcb5590aeb8701b46345719264835",
						"393b76768b24d4a0770c000a216dc5c2a8172a65a7436a5b7d148c41d9e7f39c");
		AppHypeAPI.enableLogs();
		AppHypeAPI.loadFullScreenAd();
		AppHypeAPI.loadVideoAd();
		init();

	}

	private void init() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				ARRAY[i][j] = '-';
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

	private void playGame(int i, int j) {

		if (ARRAY[i][j] == '-') {
			ARRAY[i][j] = myTurn;
			updateUI(i, j, myTurn);
			completeComputerTurn();
		}
		handleGameOver();
	}

	private void completeComputerTurn() {
		// check empty space
		if (!Util.hasEmptyPlace(ARRAY)) {
			showResultDialog("Match Draw");
			return;
		}
		ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (ARRAY[i][j] == '-') {
					list.add(i + "-" + j);
				}
			}
		}
		String s = list.get(new Random().nextInt(list.size()));
		int i = Integer.parseInt(s.substring(0, s.indexOf('-')));
		int j = Integer.parseInt(s.substring(s.indexOf('-') + 1, s.length()));
		updateUI(i, j, cmpTurn);

	}

	private void updateUI(int index, int j, char ch) {
		setupButton(ch,
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
		char r = Util.checkForWin(ARRAY);
		if (r != '-') {
			if (r == myTurn) {
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
			if (AppHypeAPI.isVideoAvailable()) {
				AppHypeAPI.showVideoAd(this);
			} else if (AppHypeAPI.isFullScreenAvailable()) {

				AppHypeAPI.showFullScreenAd(this);
			}
		} else {
			if (AppHypeAPI.isFullScreenAvailable()) {

				AppHypeAPI.showFullScreenAd(this);
			} else if (AppHypeAPI.isVideoAvailable()) {
				AppHypeAPI.showVideoAd(this);
			}
		}
	}

	private void showResultDialog(String message) {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(message).setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						onBackPressed();
					}
				});
		AlertDialog alert = builder.create();
		alert.show();

	}

	@Override
	public void onAdAvailable(String tag) {
		displayMessage(true);
	}

	@Override
	public void onShow(String tag) {
		displayMessage(false);
	}

	@Override
	public void onHide(String tag) {
	}

	@Override
	public void onFailedToShow(String tag) {
		displayError("Failed to show Ads");
	}

	@Override
	public void onIntegrationError(String error) {
		// TODO Auto-generated method stub
		Log.d("AppHype-Error", error);
		displayError(error);

	}

	private void displayError(final String error) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(GameActivity.this, error, Toast.LENGTH_SHORT)
						.show();
			}
		});
	}

	private void displayMessage(final Boolean notify) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (AppHypeAPI.isFullScreenAvailable() && notify) {
					System.out.println("FullScreen Ad is available");
				} else if (AppHypeAPI.isVideoAvailable() && notify) {
					System.out.println("Video Ad is available");
				}
			}
		});
	}

	@Override
	public void onFailedToLoad(String arg0) {
		// TODO Auto-generated method stub

	}
}
