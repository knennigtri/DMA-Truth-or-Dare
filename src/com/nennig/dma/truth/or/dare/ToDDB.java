package com.nennig.dma.truth.or.dare;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.util.Log;
import au.com.bytecode.opencsv.CSVReader;
	

class ToDDB {
	ArrayList<String> truthDB;
	ArrayList<String> dareDB;
	
	public ToDDB(Activity a, String csvFile) {
		try {
			InputStream iS = a.getAssets().open(csvFile);
			InputStreamReader iSR = new InputStreamReader(iS);
			BufferedReader bR = new BufferedReader(iSR);
		      String nextLineStr;
		      String[] nextLineParse;

		      truthDB = new ArrayList<String>();
		      dareDB = new ArrayList<String>();
		      Log.d("db","Start db parse");
		      while ((nextLineStr = bR.readLine()) != null) {
		    	  nextLineParse = nextLineStr.split(";");
		 //   	  Log.d("db", nextLineParse[0] + "--> " + nextLineParse[1]);
		        if(nextLineParse[0].equals("T")){
		        	truthDB.add(nextLineParse[1]);
		   //     	 Log.d("db", "T: " + nextLineParse[1]);
		        }else if (nextLineParse[0].equals("D")){
		        	dareDB.add(nextLineParse[1]);
		     //   	Log.d("db", "D: " + nextLineParse[1]);
		        }
		       
		      }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			 Log.d("db","Finished Parse");
		}
	}
	
	public String[] getToD(){
		String[] tod = new String[2];
		Random r = new Random();
		int i = r.nextInt(2);
		if(i<1){
			tod[0] = "Truth";
			tod[1] = getTruth();
		}else{
			tod[0] = "Dare";
			tod[1] = getDare();
		}
		Log.d("db", "Returning " + tod[0] + ": " + tod[1]);
		return tod;
	}
	
	public String getTruth(){
		Random r = new Random();
		int  i = r.nextInt(truthDB.size());
		Log.d("db", truthDB.get(i));
		return truthDB.get(i);
	}
	
	public String getDare(){
		Random r = new Random();
		int  i = r.nextInt(dareDB.size());
		Log.d("db", dareDB.get(i));
		return dareDB.get(i);
	}
}
