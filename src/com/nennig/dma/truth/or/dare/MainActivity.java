package com.nennig.dma.truth.or.dare;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        final Button truthButton = (Button) findViewById(R.id.main_truth_button);
        truthButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MainActivity.this, ViewerActivity.class);
				intent.putExtra(TRUTH_OR_DARE, TRUTH);
				startActivity(intent);
			}	
        });
        
        final Button dareButton = (Button) findViewById(R.id.main_dare_button);
        dareButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MainActivity.this, ViewerActivity.class);
				intent.putExtra(TRUTH_OR_DARE, DARE);
				startActivity(intent);
			}	
        });
        
    }
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.general, menu);
        return true;
    }
}
