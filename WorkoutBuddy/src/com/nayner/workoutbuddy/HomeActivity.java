package com.nayner.workoutbuddy;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.database.DatabaseUtils;
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

public class HomeActivity extends Activity {

	static final String[] workoutNames = new String[] {"Create New", "Leg Day","Chest Day"};
	
	Dialog myDialog;
	EditText workoutName;
	DatabaseHandler db;
	Resources res;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        db = new DatabaseHandler(this);
        res = getResources();
        
        if(DatabaseUtils.queryNumEntries(db.getReadableDatabase(),"workout") == 0)
        {
        	db.addWorkout(res.getString(R.string.woCreate));
        }
        
        final ListView workoutList = (ListView) findViewById(R.id.listView1);
        final ArrayList<String> list = db.getAllWorkouts();
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.listviewfill,R.id.list_content,list);
        workoutList.setAdapter(adapter);
        
        workoutList.setClickable(true);
        workoutList.setLongClickable(true);
        workoutList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        	
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				if(position==0)
				{
					openWorkoutDialog();
				}
				Object o = workoutList.getItemAtPosition(position);
				String str = (String)o;
				
				//Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG).show();
				
			}	
		});
        workoutList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				Object o = workoutList.getItemAtPosition(position);
				String delName = (String)o;
				if(!delName.equals(res.getString(R.string.woCreate)))
				{
					openOptionsMenu();
					Log.d("String", delName);
					//db.deleteWorkout(delName);
					//finish();
					//startActivity(getIntent());
				}
				
				return true;
			}
        	
		});
       
        
    }
    
    public void openWorkoutDialog()
    {
    	myDialog = new Dialog(this);
    	myDialog.setContentView(R.layout.workout_dialog_layout);
    	myDialog.setCancelable(true);
    	Button create = (Button)myDialog.findViewById(R.id.bWorkout);
    	workoutName = (EditText)myDialog.findViewById(R.id.etWorkoutName);
    	myDialog.show();
    	
    	create.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				db.addWorkout(workoutName.getText().toString());
				Intent i = new Intent(HomeActivity.this, CreateWorkout.class);
				i.putExtra("name", workoutName.getText().toString());
				startActivity(i);
				
			}
		});
    	
    	
    }
    

        
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_home, menu);
        return true;
    }
    
    
}
