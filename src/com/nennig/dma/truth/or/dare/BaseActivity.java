package com.nennig.dma.truth.or.dare;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;

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
    		if(sec<10)
    			time = min+":0"+sec;
    		else
    			time = min + ":"+sec;
    	}
 //   	Log.d(TAG, "SecToString: " + time);
    	return time;
    }
    
    public static void aboutAlert(final Activity c){
    	AlertDialog.Builder alert = new AlertDialog.Builder(c); 

        alert.setTitle("About"); 
        alert.setMessage("This application was created by De Marillac Academy during an enrichment class. " +
        		"Students on the team included: " +
        		"Glydel Chua, Serra Er, Vince Dizon, and Julian Cisneros. Copywrite @ 2012 Kevin Nennig");
        
        alert.setPositiveButton("View Site", new DialogInterface.OnClickListener() { 
            public void onClick(DialogInterface dialog, int whichButton) { 
            	String url = "http://www2.demarillac.org/index.php";
            	Intent i = new Intent(Intent.ACTION_VIEW);
            	i.setData(Uri.parse(url));
            	c.startActivity(i);
            } 
        }); 
        
        alert.setNegativeButton("Close", new DialogInterface.OnClickListener() { 
            public void onClick(DialogInterface dialog, int whichButton) { 
              // Canceled. 
            } 
      }); 
      alert.show();
    }    
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch(item.getItemId()){
    	case R.id.menu_main_menu:
    		startActivity(new Intent(this, MainActivity.class));
    		finish();
    		return true;
    	case R.id.menu_about: //TODO Settings page
    		aboutAlert(this);
    		return true;
    	case R.id.menu_rate_this:
    		String str ="https://play.google.com/store/apps/details?id=com.nennig.dma.truth.or.dare";
    		startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(str)));
    		return true;
    	default:
    		return super.onOptionsItemSelected(item);
    	}
    }
}
