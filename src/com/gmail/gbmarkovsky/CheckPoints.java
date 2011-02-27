package com.gmail.gbmarkovsky;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class CheckPoints extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_points);
        Button add = (Button) findViewById(R.id.button_add_CP);
        add.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				Intent intent = new Intent(CheckPoints.this, AddDistanceCheckPoint.class);
				startActivity(intent);
			}
		});
        //ListView lv = (ListView) findViewById(R.id.listView1);
        //lv.addView(new TextView(this));
    }
}
