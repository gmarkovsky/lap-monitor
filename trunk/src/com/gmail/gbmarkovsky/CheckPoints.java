package com.gmail.gbmarkovsky;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

public class CheckPoints extends Activity {
	ListView lv;
	ArrayAdapter<DistanceCheckPoint> ad;
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
        lv = (ListView) findViewById(R.id.listView1);
        ad = new ArrayAdapter<DistanceCheckPoint>(this, android.R.layout.simple_list_item_1);
        ad.add(new DistanceCheckPoint(10));
        ad.add(new DistanceCheckPoint(100));
        ad.add(new DistanceCheckPoint(1000));
        ad.add(new DistanceCheckPoint(1000));
        ad.add(new DistanceCheckPoint(1000));
        ad.add(new DistanceCheckPoint(1000));
        ad.add(new DistanceCheckPoint(1000));
        ad.add(new DistanceCheckPoint(1000));
        ad.add(new DistanceCheckPoint(1000));
        
        lv.setAdapter(ad);
        lv.setSelection(1);
        Button del = (Button) findViewById(R.id.button_add_CP);
        del.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				long ind = lv.getSelectedItemId();
				ad.remove((DistanceCheckPoint) lv.getSelectedItem());
				lv.refreshDrawableState();
			}
		});
//        EditText ed1 = new EditText(this);
//        ed1.setText("ffff");
//        EditText ed2 = new EditText(this);
//        ed2.setText("aaa");
//        lv.addView(ed1);
//        lv.addView(ed2);
    }
}
