package com.example.bowlstats;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;
import android.database.Cursor;
import android.os.strictmode.SqliteObjectLeakedViolation;
import android.util.Log;

public class sqlHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    static final String TABLE_NAME = "Names";
    static final String COL1 = "Name";

    private static String NAME_DB;
    private static final String theDate = "Date";
    private static final String Game1 = "Game1";
    private static final String Game2 = "Game2";
    private static final String Game3 = "Game3";
    private static final String gamesToday = "Day Games";
    private static final String totalGames = "Total_Games";
    private static final String dayAverage = "Daily_Average";
    private static final String totalAverage = "Total_Average";
    private static final String highScore = "High_Score";
    private static final String lowScore = "Low_Score";
    private static final String totalPoints = "Total_Points";



    public sqlHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +  COL1 + " TEXT " + ")";
//        String deleteTable = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(createTable);
    }

//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        String createTable = "CREATE TABLE " + TABLE_NAME + " (" + COL1 + "DATE," + COL2 + "SMALLINT," +
//                COL3 + "SMALLINT," + COL4 + "SMALLINT," + COL5 + "SMALLINT," + COL6 + "FLOAT," + COL7 +
//                "SMALLINT," + COL8 + "SMALLINT," + COL9 + "SMALLINT," + COL10 + "SMALLINT)";
//        db.execSQL(createTable);
//    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    /**
     * this funciton will create a new database for a new player
     * @param name
     * @return
     */
    public  boolean createTable(String name) {
        SQLiteDatabase db = getWritableDatabase();
//        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase("main.db", null, null);
        NAME_DB = name;
        Cursor cursor = db.rawQuery("SELECT DISTINCT tbl_name FROM sqlite_master WHERE tbl_name = '" +
                name + "'", null);
        if(cursor != null) {
            if(cursor.getCount() == 1) {
                return false;
            }
            String createTable = " CREATE TABLE IF NOT EXISTS " + NAME_DB + "(" + theDate + " DATE, " +
                    Game1 + " SMALLINT(300), " + Game2 + " SMALLINT(300), " + Game3 + " SMALLINT(300), " +
                    gamesToday + " SMALLINT(3), " + totalGames + " SMALLINT, " + dayAverage + " FLOAT(300.3), " +
                    totalAverage + " FLOAT, " + highScore + " SMALLINT(300), " + lowScore + " SMALLINT(300), " +
                    totalPoints + " BIGINT " + ") ";
            db.execSQL(createTable);
            return true;
        }
        return false;
//        try {
//            Cursor cursor = db.rawQuery(" SELECT DISTINCT tbl_name FROM sqlite_master WHERE tbl_name = " +
//                    NAME_DB + "", null);
//            if(cursor.getCount() != 1) {
//                String createTable = " CREATE TABLE IF NOT EXISTS " + NAME_DB + "(" + theDate + " DATE, " +
//                        Game1 + " SMALLINT(300), " + Game2 + " SMALLINT(300), " + Game3 + " SMALLINT(300), " +
//                        gamesToday + " SMALLINT(3), " + totalGames + " SMALLINT, " + dayAverage + " FLOAT(300.3), " +
//                        totalAverage + " FLOAT, " + highScore + " SMALLINT(300), " + lowScore + " SMALLINT(300), " +
//                        totalPoints + " BIGINT " + ") ";
//                db.execSQL(createTable);
//                return true;
//            }
//            return false;
//        }
//        catch (Exception e) {
//            return false;
//        }

    }

    /**
     * This function will add a given name to the Name database
     */
    public boolean addPlayer(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL1, name);

        Log.d(TAG, "addData: Adding " + name + " to " + TABLE_NAME);
        long result = db.insert(TABLE_NAME, null, values);

        if(result == -1)
            return false;
        else
            return true;
    }

    /**
     * This method will return all the players in the database name in an array
     * @return
     */
    public Cursor getPlayers(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor player = db.rawQuery(query, null);
        return player;
    }

    public void recordScore(String name, int score) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        DateFormat formatDate = new SimpleDateFormat("yyyy-dd-mm");
        Date date = new Date();
        //formatDate.format(date);
        int tempDate = date.getDate();
        System.out.println(formatDate.format(date));
//        values.put(theDate, )
    }




}
