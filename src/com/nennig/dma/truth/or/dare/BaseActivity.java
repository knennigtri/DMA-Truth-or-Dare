package com.nennig.dma.truth.or.dare;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

public class BaseActivity extends Activity {
	public static final String PLAYERS = "com.nennig.dma.truth.or.dare.PLAYERS";
	public static final String CD_IN_SECONDS = "com.nennig.dma.truth.or.dare.CDSECONDS";
	public static final String PREFS = "com.nennig.dma.truth.or.dare.PREFS";
	public static final String NO_ITEMS = "No Current Players";
	
	public static final String ROOT_FOLDER = Environment.getExternalStorageDirectory().toString();
	private static final String TAG = "com.nennig.dma.truth.or.dare.Base";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        

        
    }

    public static String arrayToString(Object[] arr){
    	String str = "";
    	for(int i = 0; i< arr.length;i++){
    		if(arr[i] != NO_ITEMS)
    			str = str + arr[i] + ",";
    	}
    	Log.d(TAG, "ArrayToString: " +str);
    	return str;
    }
    
    public String secondsToTimeString(int sec){
    	String time = "";
    	if(sec<60)
    		if(sec<10)
    			time = "00:0"+sec;
    		else
    			time = "00:"+sec;
    	else
    	{	
    		int min = sec/60;
    		sec = sec%60;
    		time =  min+":"+sec;
    	}
    	Log.d(TAG, "SecToString: " + time);
    	return time;
    }
    
    public void aboutAlert(Context c){
    	AlertDialog.Builder alert = new AlertDialog.Builder(c); 

        alert.setTitle("About"); 
        alert.setMessage("Copywrite @ 2012 Kevin Nennig");
        
        alert.setPositiveButton("View Site", new DialogInterface.OnClickListener() { 
            public void onClick(DialogInterface dialog, int whichButton) { 
            	String url = "https://sites.google.com/site/nennigk/personal-projects/name-that";
            	Intent i = new Intent(Intent.ACTION_VIEW);
            	i.setData(Uri.parse(url));
            	BaseActivity.this.startActivity(i);
            } 
        }); 
        
        alert.setNegativeButton("Close", new DialogInterface.OnClickListener() { 
            public void onClick(DialogInterface dialog, int whichButton) { 
              // Canceled. 
            } 
      }); 
      alert.show();
    }    
}
