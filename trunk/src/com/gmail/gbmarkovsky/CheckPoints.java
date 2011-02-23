package com.gmail.gbmarkovsky;

import android.app.Activity;
import android.os.Bundle;

public class CheckPoints extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testlay);
        /*Button button = (Button) findViewById(R.id.button1);
        button.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO обработка добавления контрольной точки
				RadioGroup rg = (RadioGroup)  findViewById(R.id.radioGroup1);
				int chekedId = rg.getCheckedRadioButtonId();
				TextView tv = (TextView) findViewById(R.id.textView11);
				tv.setText("Выбран вариант " + Integer.toString(chekedId));
				setContentView(R.layout.main);
			}
		});
        Button back = (Button) findViewById(R.id.button2);
        back.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				setContentView(R.layout.editcheckpoint);
			}
		});*/
    }
}