package com.isitbroken.Slurms;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.LinearLayout;

public class SlurmsActivity extends Activity {
    private static final String PREFS_NAME = "SlurmsPrefs";
	private Handler handler;
	private SharedPreferences settings;
	private GameBoard LocalGameBoard;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		handler = new Handler();
		settings = getSharedPreferences(PREFS_NAME, 0);
		LinearLayout BottomLayout = (LinearLayout) (findViewById(R.id.GB));
		LocalGameBoard = new GameBoard(BottomLayout.getContext(),this);
		BottomLayout.addView(LocalGameBoard);
		LocalGameBoard.handler = handler;
    }

	public void UPdateUI() {
		// TODO Auto-generated method stub

	}
}