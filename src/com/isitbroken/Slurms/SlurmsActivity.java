package com.isitbroken.Slurms;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class SlurmsActivity extends Activity {
    private static final String PREFS_NAME = "SlurmsPrefs";
	private Handler handler;
	private SharedPreferences settings;
	private GameBoard LocalGameBoard;
	private float offset = 30;
	int BoardWidth;
	int BoardHight;
	Button MoveButton;
	Button FireButton;
	protected boolean Moving = false;


	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);

		Intent i = new Intent();
		i.setClassName("com.isitbroken.Slurms",
				"com.isitbroken.Slurms.Splash");
		startActivity(i);

		handler = new Handler();
		settings = getSharedPreferences(PREFS_NAME, 0);
		FrameLayout BottomLayout = (FrameLayout) (findViewById(R.id.GB));
		FireButton = (Button) (findViewById(R.id.Fire));

		FireButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				if(LocalGameBoard.Turns.Player){
					LocalGameBoard.Turns.Fire();
				}
			}});

		MoveButton = (Button) (findViewById(R.id.Move));
		MoveButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				togglebutton();
			}});

		LocalGameBoard = new GameBoard(BottomLayout.getContext(),this);

		BottomLayout.addView(LocalGameBoard, 0);

		//BottomLayout.addView(LocalGameBoard);
		LocalGameBoard.handler = handler;

    }
    void togglebutton(){
		Moving  = !Moving;
		if(Moving){
			MoveButton.setText("> <");
		}else{
			MoveButton.setText("< >");
		}
    }

	public void UPdateUI() {
		if(LocalGameBoard.Turns.StepCounter > 4){
			Moving  = false;
			MoveButton.setText("< >");
		}

	}

	@Override
	protected void onResume() {
		super.onResume();
		if(LocalGameBoard != null){
			if (LocalGameBoard.surfaceCreated == true) {
				LocalGameBoard.createThread(LocalGameBoard.getHolder());
			}
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		if(LocalGameBoard != null){
			LocalGameBoard.terminateThread();
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
			if(LocalGameBoard.Turns.Player){
					LocalGameBoard.Turns.UpdatePos(event,Moving);
			}
			//LocalGameBoard.levePlatform.Addhole(event.getX(),event.getY()-offset ,50);

		return true;
	}

}