package com.nayner.workoutbuddy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

public class CreateWorkout extends Activity {
	String workoutName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_workout);
		
		Bundle extras = getIntent().getExtras();
		workoutName = extras.getString("name");
		setTitle(workoutName);
		
		
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
