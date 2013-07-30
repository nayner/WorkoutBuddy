package com.nayner.workoutbuddy;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class HomeActivity extends Activity {

	static final String[] workoutNames = new String[] {"Create New", "Leg Day","Chest Day"};
	private String[] contextMenuItems;
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
        	firstRunSetup();
        }
        
        contextMenuItems = getResources().getStringArray(R.array.context_menu_items);
        
        final ListView workoutList = (ListView) findViewById(R.id.listView1);
        final ArrayList<String> list = db.getAllWorkouts();
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.listviewfill,R.id.list_content,list);
        workoutList.setAdapter(adapter);
        workoutList.setClickable(true);
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
        
        
        
       registerForContextMenu(workoutList);
       
        
    }
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
	    ContextMenuInfo menuInfo) {
	  if (v.getId()==R.id.listView1) {
	    AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
	    menu.setHeaderTitle(contextMenuItems[info.position]);
	    String[] menuItems = getResources().getStringArray(R.array.context_menu_items);
	    for (int i = 0; i<menuItems.length; i++) {
	      menu.add(Menu.NONE, i, i, menuItems[i]);
	    }
	  }
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
	  AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
	  int menuItemIndex = item.getItemId();
	  String[] menuItems = getResources().getStringArray(R.array.context_menu_items);
	  String menuItemName = menuItems[menuItemIndex];
	  String listItemName = contextMenuItems[info.position];

	 // TextView text = (TextView)findViewById(R.id.footer);
	  //text.setText(String.format("Selected %s for item %s", menuItemName, listItemName));
	  Log.d("selected : on", menuItemName);
	  return true;
	}
    
    private void firstRunSetup() {
		// TODO Auto-generated method stub
    	db.addWorkout(res.getString(R.string.woCreate));
    	db.addExcercise("Chest Press", "Chest", "Barbell");
		
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
