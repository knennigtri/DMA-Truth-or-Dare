package com.nennig.dma.truth.or.dare;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PlayerActivity extends ListActivity {
	
//	private static final String PLAYERS = "players";
	private List<String> _players;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        
        loadPlayers();
        
        final ArrayAdapter<String> aa = new ArrayAdapter<String>(this,R.layout.player_item,_players);
        setListAdapter(aa);
        
        getListView().setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
            	String selectedPlayer = aa.getItem(position);
            	if(selectedPlayer == BaseActivity.NO_ITEMS){
            		addPlayerAlert();
            	}
            	else
            	{
            		editPlayerAlert(aa.getItem(position));
            	}
            }
        });
        
        Button addButton = (Button) findViewById(R.id.player_add_button);
        addButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				addPlayerAlert();
			}	
        });
        
        Button startButton = (Button) findViewById(R.id.player_start_button);
        startButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(_players.size() != 1){
					Intent intent = new Intent(PlayerActivity.this, ViewerActivity.class);
					startActivity(intent);
				}
				else
				{
					Toast.makeText(PlayerActivity.this, "To start you must have 2 or more players.", Toast.LENGTH_LONG).show();
				}
			}	
        });
    }

    private void loadPlayers(){
    	SharedPreferences settings = getSharedPreferences(BaseActivity.PREFS,MODE_PRIVATE);
    	String playersStr = settings.getString(BaseActivity.PLAYERS,BaseActivity.NO_ITEMS);
    	if(playersStr == "")
    		playersStr = BaseActivity.NO_ITEMS;
    	_players = new ArrayList<String>();
    	_players.addAll(Arrays.asList(playersStr.split(",")));
    }
    private void savePlayers(){
    	SharedPreferences settings = getSharedPreferences(BaseActivity.PREFS,MODE_PRIVATE);
    	Editor e = settings.edit();
    	String str = BaseActivity.arrayToString(_players.toArray());
    	e.putString(BaseActivity.PLAYERS,str);
    	e.commit();
    }
    
    private void addPlayerAlert(){
    	AlertDialog.Builder alert = new AlertDialog.Builder(this); 

        alert.setTitle("Add Player"); 
    //    alert.setMessage("Copywrite @ 2012 Kevin Nennig");
        final EditText name = new EditText(this);
        name.setHint("Name");
        
        alert.setView(name);
        
        alert.setPositiveButton("Add", new DialogInterface.OnClickListener() { 
            public void onClick(DialogInterface dialog, int whichButton) { 
            	String _name = name.getText().toString();
            	if(_name != "")
            		_players.add(_name);
            	savePlayers();
            	recreate();
            } 
        }); 
        
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() { 
            public void onClick(DialogInterface dialog, int whichButton) { 
              // Canceled. 
            } 
      }); 
      alert.show();
    }
    
    private void editPlayerAlert(final String player){
    	AlertDialog.Builder alert = new AlertDialog.Builder(this); 

        alert.setTitle(player); 
        alert.setPositiveButton("Delete", new DialogInterface.OnClickListener() { 
            public void onClick(DialogInterface dialog, int whichButton) { 
            	deletePlayerAlert(player);
            } 
        }); 
        
        alert.setNegativeButton("Rename", new DialogInterface.OnClickListener() { 
              public void onClick(DialogInterface dialog, int whichButton) { 
                renamePlayerAlert(player); 
              } 
        }); 
        alert.show();
    }
    
    private void deletePlayerAlert(final String player){
    	AlertDialog.Builder alert = new AlertDialog.Builder(this); 
        alert.setTitle("Delete " + player + "?"); 

        alert.setPositiveButton("Delete", new DialogInterface.OnClickListener() { 
            public void onClick(DialogInterface dialog, int whichButton) { 
            	_players.remove(player);
            	savePlayers();
            	recreate();
            } 
        }); 
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() { 
              public void onClick(DialogInterface dialog, int whichButton) { 
                // Canceled. 
              } 
        }); 
        alert.show();
    }
    
    private void renamePlayerAlert(final String oldName){
    	AlertDialog.Builder alert = new AlertDialog.Builder(this); 
        alert.setTitle("Edit Player"); 
        
        final EditText renamePlayer = new EditText(this);
        renamePlayer.setText(oldName);
        renamePlayer.setHint("Name");
        alert.setView(renamePlayer); 

        alert.setPositiveButton("Save", new DialogInterface.OnClickListener() { 
            public void onClick(DialogInterface dialog, int whichButton) { 
            	String newName = renamePlayer.getText().toString();
            	if(newName != oldName){
            		_players.remove(oldName);
            		_players.add(newName);
            	}
            	savePlayers();
            	recreate();
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
        getMenuInflater().inflate(R.menu.activity_player, menu);
        return true;
    }
}
