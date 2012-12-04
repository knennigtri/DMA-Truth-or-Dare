package com.nennig.dma.truth.or.dare;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ViewerActivity extends BaseActivity implements SensorEventListener {
	private static final String TAG = "com.nennig.dma.truth.or.dare.ToD";
	private static final String _NEW_ROUND_TITLE = "Truth or Dare?";
	private int _cdSeconds;

	private Sensor myShakeSensor;
	SensorManager sensorManager;
	
	Button nextButton, shareButton;
	TextView todText, todDescText, playerNameText, timer;
	private List<String> _players;
	
	ToDDB db;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewer);
        db = new ToDDB(ViewerActivity.this, "db.csv");
        
        // get sensor manager
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        // get compass sensor (ie magnetic field)
        myShakeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        
        shareButton = (Button) findViewById(R.id.viewer_share_button);
        nextButton = (Button) findViewById(R.id.viewer_next_button);
        todText = (TextView) findViewById(R.id.viewer_todText);
    	todDescText = (TextView) findViewById(R.id.viewer_todDescriptionText);
    	playerNameText = (TextView) findViewById(R.id.viewer_playerText);
    	timer = (TextView) findViewById(R.id.clockText);
        
    	loadPreferences();
        setNewPlayer();

        final CountDownTimer toDTimer = createTimer();
        toDTimer.start();        
        
        shareButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				shareIt();
			}	
        });
        
        nextButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				setNewPlayer();
				todText.setText(_NEW_ROUND_TITLE);
				todText.setTextColor(Color.GRAY);
				todDescText.setText("Shake to find out!");
				todDescText.setTextColor(Color.GRAY);
				toDTimer.cancel();
				toDTimer.start();
			}	
        }); 
    }
    
    private void shareIt() {
    	Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
    	sharingIntent.setType("text/plain");
    	String shareBody = playerNameText.getText() + " just did this " + todText.getText() + ": " + todDescText.getText();

    	sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "ToD");
    	sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
    	
    	startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }
    
    private CountDownTimer createTimer(){
    	return new CountDownTimer((_cdSeconds)*1000, 1000) {

            public void onTick(long millisUF) {
            	int sec = (int) (millisUF/1000);
            	String timeLeft = secondsToTimeString(sec);
            	timer.setText(timeLeft);
            }

            public void onFinish() {
                timer.setText("Time's Up!");
            }
         };
    }
   
    private void setNewPlayer(){
    	Random random = new Random();
        int i = random.nextInt(_players.size());
        String player = _players.get(i);
        playerNameText.setText(player);
    }
    
    private void loadPreferences(){
    	SharedPreferences settings = getSharedPreferences(PREFS,MODE_PRIVATE);
    	String playersStr = settings.getString(PLAYERS,BaseActivity.NO_ITEMS);
    	if(playersStr == "")
    		playersStr = BaseActivity.NO_ITEMS;
    	_players = new ArrayList<String>();
    	_players.addAll(Arrays.asList(playersStr.split(",")));
    	
    	_cdSeconds = settings.getInt(CD_IN_SECONDS, 30);
    }  
    
    public void nextQuestion(){
    	if(todText.getText().equals(_NEW_ROUND_TITLE)){
	    	String[] tod = db.getToD();
	    	
	    	String newToD, newToDDesc;
	    	newToD = tod[0];
	    	newToDDesc = tod[1];
	    	
	    	//TODO Query new ToD Question
	    	
	    	todText.setText(newToD);
	    	todDescText.setText(newToDDesc);
	    	
	    	if(newToD.equals("Truth")){
	    		todText.setTextColor(Color.BLUE);
	    		todDescText.setTextColor(Color.MAGENTA);
	    	}else{
	    		todText.setTextColor(Color.MAGENTA);
	    		todDescText.setTextColor(Color.BLUE);
	    	}
    	}
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.general, menu);
        return true;
	}

    // register to listen to sensors
    @Override
    public void onResume() {
      super.onResume();
      sensorManager.registerListener(this, myShakeSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    // unregister
    @Override
    public void onPause() {
      super.onPause();
      sensorManager.unregisterListener(this);
    }

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
	}

	float lastx,lasty,lastz;
	long lastClockTime = 0;
	boolean firstDataPts = true;
	@Override
	public void onSensorChanged(SensorEvent arg0) {
			float thresholdVal = 130;
		   float x_value = arg0.values[0];
		   float y_value = arg0.values[1];
		   float z_value = arg0.values[2];
		   
		   long currenttime = System.currentTimeMillis();
		   if(!firstDataPts){
			    long deltatime = currenttime - lastClockTime;
			    float xrate = Math.abs(x_value - lastx) * 10000/deltatime;
			    float yrate = Math.abs(y_value - lasty) * 10000/deltatime;
			    float zrate = Math.abs(z_value - lastz) * 10000/deltatime;
			    
			    if (xrate>thresholdVal){
			    	Log.d(TAG,"XShaking: "+xrate);
			    	nextQuestion();
			    }else if (yrate>thresholdVal){
			    	Log.d(TAG,"YShaking: " + yrate);
			    	nextQuestion();
			    }else if (zrate>thresholdVal){
			    	Log.d(TAG,"ZShaking: " + zrate);
			    	nextQuestion();
			    }
		   }
		   lastClockTime = currenttime;
		   lastx = x_value;
		   lasty = y_value;
		   lastz = z_value;
		   firstDataPts = false;
	}
}