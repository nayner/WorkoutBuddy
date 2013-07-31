package com.nayner.workoutbuddy;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class CreateWorkout extends Activity {
	String workoutName;
	ArrayList<String> exNames;
	DatabaseHandler db;
	Button selectExcercise, addExcercise;
	Dialog exDialog;
	ListView dialogList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_workout);
		
		Bundle extras = getIntent().getExtras();
		workoutName = extras.getString("name");
		setTitle(workoutName);
		//link xml
		selectExcercise = (Button) findViewById(R.id.bSelectExcercise);
		addExcercise = (Button) findViewById(R.id.bAdExcercise);

		//database access
		db = new DatabaseHandler(this);		
		exNames = db.getExcerciseNames();
		
		selectExcercise.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				openExDialog();
				
			}

			
		});
	}
	
	public void openExDialog() {
		// TODO Auto-generated method stub
		exDialog = new Dialog(CreateWorkout.this);
		exDialog.setContentView(R.layout.list_dialog);
		exDialog.setCancelable(true);
		
		dialogList = (ListView) exDialog.findViewById(R.id.lvDialog);
		//final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,exNames);
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.listviewfill,R.id.list_content,exNames);
		Object[] listPositions = exNames.toArray();
		Log.d("exnames", listPositions[0].toString());
		dialogList.setAdapter(adapter);
		
		exDialog.show();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_create_workout, menu);
		return true;
	}
	
	@Override
	public void onBackPressed()
	{
		Intent i = new Intent(this, HomeActivity.class);
		startActivity(i);
	}
	

}
