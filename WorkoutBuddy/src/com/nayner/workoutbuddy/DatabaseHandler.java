package com.nayner.workoutbuddy;


import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper{
	//ALL STATIC VARIABLES
	//db version
	private static final int DATABASE_VERSION = 1;
	//db name
	private static final String DATABASE_NAME = "workoutBuddy";
	
	//workout table
	private static final String WORKOUT_TABLE = "workout";
	//workout Columns
	private static final String WORKOUT_ID = "workout_id";
	private static final String WORKOUT_NAME = "workout_name";
	
	//excercise table
	private static final String EXCERCISE_TABLE = "excercise";
	//workout columns
	private static final String EXCERCISE_ID = "excercise_id";
	private static final String EXCERCISE_NAME = "excercise_name";
	private static final String EXCERCISE_AREA = "excercise_area";
	private static final String EXCERCISE_EQUIPTMENT = "excercise_equiptment";
	
	//goal table
	private static final String GOAL_TABLE = "goal";
	//goal columns
		//workout_id
		//excercise_id
	private static final String GOAL_POSITION = "goal_position";
	private static final String GOAL_SETS = "goal_sets";
	private static final String GOAL_REPS = "goal_reps";
	private static final String GOAL_WEIGHT = "goal_weight";
	
	//session table
	private static final String SESSION_TABLE = "session";
	//session columns
	private static final String SESSION_ID = "session_id";
		//workout_id
		//excercise_id
	private static final String SET_NUMBER = "set_number";
	private static final String SET_REPS = "set_reps";
	private static final String SET_WEIGHT = "set_weight";
	private static final String DATE = "date";
	
	public DatabaseHandler(Context context)
	{
		super(context,DATABASE_NAME,null,DATABASE_VERSION);
		Log.d("handler","good");
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d("on", "create");
		//CREATE WORKOUT TABLE
		String CREATE_WORKOUT_TABLE = "CREATE TABLE IF NOT EXISTS " + WORKOUT_TABLE + "(" + WORKOUT_ID +
				" INTEGER PRIMARY KEY AUTOINCREMENT," + WORKOUT_NAME +" TEXT)";
		//CREATE EXCERCISE TABLE
		String CREATE_EXCERCISE_TABLE = "CREATE TABLE IF NOT EXISTS "+ EXCERCISE_TABLE+ "(" + EXCERCISE_ID +
				" INTEGER PRIMARY KEY AUTOINCREMENT,"+ EXCERCISE_NAME + " TEXT," + EXCERCISE_AREA + " TEXT," +
				EXCERCISE_EQUIPTMENT + " TEXT)";
		//CREATE GOAL TABLE
		String CREATE_GOAL_TABLE = "CREATE TABLE IF NOT EXISTS " +GOAL_TABLE + "("+WORKOUT_ID + " INTEGER," +
				EXCERCISE_ID + " INTEGER," +GOAL_POSITION + " INTEGER,"+GOAL_SETS+ " INTEGER,"+GOAL_REPS+
				" INTEGER," + GOAL_WEIGHT +" INTEGER)";
		String CREATE_SESSION_TABLE = "CREATE TABLE IF NOT EXISTS " + SESSION_TABLE + "("+SESSION_ID +" INTEGER,"
				+ WORKOUT_ID +" INTEGER,"+EXCERCISE_ID + " INTEGER,"+SET_NUMBER + " INTEGER,"+SET_REPS+" INTEGER,"
				+SET_WEIGHT + " INTEGER," + DATE + " TEXT)";
		
		Log.w("workout ",CREATE_WORKOUT_TABLE);
		Log.w("excercise ",CREATE_EXCERCISE_TABLE);
		Log.w("goal ",CREATE_GOAL_TABLE);
		Log.w("session ",CREATE_SESSION_TABLE);
		try{
		db.execSQL(CREATE_WORKOUT_TABLE);
		db.execSQL(CREATE_EXCERCISE_TABLE);
		db.execSQL(CREATE_GOAL_TABLE);
		db.execSQL(CREATE_SESSION_TABLE);
		Log.w("create", "success");
		}catch(SQLException e){
			Log.w("create", e.toString());
		}
		
		
		}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//drop older tables
		db.execSQL("DROP TABLE IF EXISTS "+ EXCERCISE_TABLE);
		db.execSQL("DROP TABLE IF EXISTS "+ GOAL_TABLE);
		db.execSQL("DROP TABLE IF EXISTS "+ SESSION_TABLE);
		db.execSQL("DROP TABLE IF EXISTS "+ WORKOUT_TABLE);
		//create them all again
		onCreate(db);
	}
	
	void addWorkout(String workoutName){
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(WORKOUT_NAME, workoutName);
		db.insert(WORKOUT_TABLE, null, values);
		Log.d("workout added", workoutName);
		db.close();
	}
	
	void addExcercise(String exerciseName, String bodyPart, String equiptment){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(EXCERCISE_NAME, exerciseName);
		values.put(EXCERCISE_AREA, bodyPart);
		values.put(EXCERCISE_EQUIPTMENT, equiptment);
		db.insert(EXCERCISE_TABLE, null, values);
		Log.d("Excercise added", exerciseName);
		db.close();
	}
	
	void deleteWorkout(String woName)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		try{
			db.delete(WORKOUT_TABLE,WORKOUT_NAME+" = ?", new String[]{woName});
			Log.d("deleted",woName);
		}catch(Exception e){
			Log.d("delete Error", e.toString());
		}
		db.close();
		
	}
	
	public ArrayList<String> getAllWorkouts()
	{
		ArrayList<String> list = new ArrayList<String>();
		//select query
		String SelectQuery = "Select * FROM "+ WORKOUT_TABLE;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(SelectQuery, null);
		//loop through rows and add to list
		if(cursor.moveToFirst())
		{
			do{
					list.add(cursor.getString(1));
					//list.add(cursor.getString(1));
			}while(cursor.moveToNext());
		}
		db.close();
		return list;
	}

	public ArrayList<String> getExcerciseNames() {
		// TODO Auto-generated method stub
		ArrayList<String> exList = new ArrayList<String>();
		//SELECT query string
		String selectExcercises = "SELECT "+ EXCERCISE_NAME+ " FROM " + EXCERCISE_TABLE;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectExcercises, null);
		//loop through returned rows and add to list
		if(cursor.moveToFirst())
		{
			do{
				exList.add(cursor.getString(0));
			}while(cursor.moveToNext());
		}
		db.close();
		return exList;
	}
	
	
	
	
	
	
	

}
