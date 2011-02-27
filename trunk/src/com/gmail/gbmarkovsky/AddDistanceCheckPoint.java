package com.gmail.gbmarkovsky;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class AddDistanceCheckPoint extends Activity {
	protected ArrayAdapter<CharSequence> mAdapter;
	private String mes = "m";
	EditText distance;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_distance_check_point);
        distance = (EditText) findViewById(R.id.edDistance);
        Spinner mesure = (Spinner) findViewById(R.id.spDisMes);
        this.mAdapter = ArrayAdapter.createFromResource(this, R.array.Measures,
                android.R.layout.simple_spinner_dropdown_item);

        mesure.setAdapter(this.mAdapter);
        OnItemSelectedListener spinnerListener = new myOnItemSelectedListener(this,this.mAdapter);
        mesure.setOnItemSelectedListener(spinnerListener);
        Button okButton = (Button) findViewById(R.id.addDisCPOKButton);
        okButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				double numDis = Integer.parseInt(AddDistanceCheckPoint.this.distance.getText().toString());
				double distance = numDis;
				if (mes.equals("km")) {
					distance = numDis * 1000;
				} else if (mes.equals("ft")) {
					distance = numDis/3;
				}
				CheckPointsManager.getInstance().createDistanceCheckPoint(distance, true);
				finish();
			}
		});
        Button cancelButton = (Button) findViewById(R.id.addDisCPCancelButton);
        cancelButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				finish();
			}
		});
    }
    
    public class myOnItemSelectedListener implements OnItemSelectedListener {
    /*
     * provide local instances of the mLocalAdapter and the mLocalContext
     */

    ArrayAdapter<CharSequence> mLocalAdapter;
    Activity mLocalContext;

    /**
     *  Constructor
     *  @param c - The activity that displays the Spinner.
     *  @param ad - The Adapter view that
     *    controls the Spinner.
     *  Instantiate a new listener object.
     */
    public myOnItemSelectedListener(Activity c, ArrayAdapter<CharSequence> ad) {

      this.mLocalContext = c;
      this.mLocalAdapter = ad;

    }

    /**
     * When the user selects an item in the spinner, this method is invoked by the callback
     * chain. Android calls the item selected listener for the spinner, which invokes the
     * onItemSelected method.
     *
     * @see android.widget.AdapterView.OnItemSelectedListener#onItemSelected(
     *  android.widget.AdapterView, android.view.View, int, long)
     * @param parent - the AdapterView for this listener
     * @param v - the View for this listener
     * @param pos - the 0-based position of the selection in the mLocalAdapter
     * @param row - the 0-based row number of the selection in the View
     */
    public void onItemSelected(AdapterView<?> parent, View v, int pos, long row) {
        mes = parent.getItemAtPosition(pos).toString();
    }

    /**
     * The definition of OnItemSelectedListener requires an override
     * of onNothingSelected(), even though this implementation does not use it.
     * @param parent - The View for this Listener
     */
    public void onNothingSelected(AdapterView<?> parent) {

        // do nothing

    }
}
}
