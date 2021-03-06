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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class CreateWorkout extends Activity {
	String workoutName;
	ArrayList<String> exNames;
	DatabaseHandler db;
	Button selectExcercise, addExcercise;
	Dialog exDialog, exNewDialog;
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
		
		addExcercise.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				openNewExDialog();
			}

			private void openNewExDialog() {
				// TODO Auto-generated method stub
				exNewDialog = new Dialog(CreateWorkout.this);
				exNewDialog.setContentView(R.layout.new_excercise_dialog);
				exNewDialog.setCancelable(true);
				exNewDialog.setTitle("New Excercise");
				exNewDialog.show();
				
				final EditText exName = (EditText) exNewDialog.findViewById(R.id.etExName);
				final EditText exMuscleGroup = (EditText) exNewDialog.findViewById(R.id.etMuscleGroup);
				final EditText exEquiptment = (EditText) exNewDialog.findViewById(R.id.etEquiptment);
				Button submitExcercise = (Button) exNewDialog.findViewById(R.id.bSubmitExcercise);
				
				submitExcercise.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						String sExName = exName.getText().toString();
						String sMuscle = exMuscleGroup.getText().toString();
						String sEquiptment = exEquiptment.getText().toString();
						db.addExcercise(sExName, sMuscle, sEquiptment);
						
						exNames = db.getExcerciseNames();
						exNewDialog.dismiss();
						
					}
				});
				
			}
		});
	
	}
	
	
	public void openExDialog() {
		// TODO Auto-generated method stub
		exDialog = new Dialog(CreateWorkout.this);
		exDialog.setContentView(R.layout.list_dialog);
		exDialog.setCancelable(true);
		exDialog.setTitle("Excercise List");
		
		dialogList = (ListView) exDialog.findViewById(R.id.lvDialog);
		//final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,exNames);
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.listviewfill,R.id.list_content,exNames);
		Object[] listPositions = exNames.toArray();
		Log.d("exnames", listPositions[0].toString());
		dialogList.setAdapter(adapter);
		exDialog.show();
		
		dialogList.setClickable(true);
		dialogList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				Object o = dialogList.getItemAtPosition(position);
				String str = (String)o;
				selectExcercise.setText(str);
				exDialog.dismiss();
			}
		});
		
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
