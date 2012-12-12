package com.nennig.dma.truth.or.dare;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends BaseActivity {
	private static final String TAG = "com.nennig.dma.truth.or.dare.Main";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        final Button truthButton = (Button) findViewById(R.id.main_play_button);
        truthButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MainActivity.this, PlayerActivity.class);
				startActivity(intent);
			}	
        });   
        final Button setTimerButton = (Button) findViewById(R.id.main_timer_button);
        setTimerButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				setTimerAlert();
			}	
        });
    }
    
    private void setTimerAlert(){
    	AlertDialog.Builder alert = new AlertDialog.Builder(this); 

        alert.setTitle("Set time"); 
        final Spinner sp = new Spinner(this);
        List<String> list = new ArrayList<String>();
    	list.add("00:30");
    	list.add("00:45");
    	list.add("1:00");
    	list.add("1:15");
    	list.add("1:30");
    	list.add("1:45");
    	list.add("2:00");
    	list.add("2:15");
    	list.add("2:30");
    	list.add("2:45");
    	list.add("3:00");
    	list.add("3:15");
    	list.add("3:30");
    	list.add("3:45");
    	list.add("4:00");
    	list.add("5:00");
    	list.add("6:00");
    	list.add("7:00");
    	list.add("8:00");
    	ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
    		android.R.layout.simple_spinner_item, list);
    	dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	sp.setAdapter(dataAdapter);
   
    	final SharedPreferences settings = getSharedPreferences(BaseActivity.PREFS,MODE_PRIVATE);
        int savedTime = settings.getInt(CD_IN_SECONDS, 30);
        int curPos = list.indexOf(secondsToTimeString(savedTime));
    	sp.setSelection(curPos);
        alert.setView(sp);
        
        alert.setPositiveButton("Set", new DialogInterface.OnClickListener() { 
            public void onClick(DialogInterface dialog, int whichButton) { 
            	String str = String.valueOf(sp.getSelectedItem());
            	String[] time = str.split(":");
            	int seconds = Integer.valueOf(time[0])*60 + Integer.valueOf(time[1]);
            	
            	Log.d(TAG, "Time: " + str + " Seconds: " + seconds);
            	
            	
            	Editor e = settings.edit();
            	e.putInt(BaseActivity.CD_IN_SECONDS,seconds);
            	e.commit();
            	
            	Toast.makeText(MainActivity.this, "Timer changed to " + sp.getSelectedItem(), Toast.LENGTH_SHORT).show();
            } 
        }); 
        
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() { 
            public void onClick(DialogInterface dialog, int whichButton) { 
              // Canceled. 
            } 
      }); 
      alert.show();
    }

    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.general, menu);
        return true;
    }
}
