package com.nennig.dma.truth.or.dare;

import android.os.Bundle;
import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class ViewerActivity extends BaseActivity {
	private boolean _truth = false;
	private boolean _dare = false;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewer);
        
//        if(savedInstanceState != null)
//        {
//        	_truth = savedInstanceState.getBoolean(TRUTH);
//        	_dare = savedInstanceState.getBoolean(DARE);
//        }
//        else
//        {
//        	loadPreferences();
//        }
        
        String tOd = getIntent().getStringExtra(TRUTH_OR_DARE);
        
        Log.d("viewer", "Value is: " + tOd);
        
        if(tOd.equals(TRUTH))
        {
        	_truth = true;
        	_dare = false;
        }
        else if(tOd.equals(DARE))
        {
        	_dare = true;
        	_truth = false;
        }
        
        TextView tv = (TextView) findViewById(R.id.viewerText);
        if(_truth)
        	tv.setText("Say the last person you texted");
        if(_dare)
        	tv.setText("Sing I'm a little tea pot with actions.");
        
    }

    private void loadPreferences(){
 	   SharedPreferences settings = getSharedPreferences(PREFS,MODE_PRIVATE);
 	   _truth = settings.getBoolean(TRUTH, true);
 	   _dare = settings.getBoolean(DARE, false);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.general, menu);
        return true;
    }
}
