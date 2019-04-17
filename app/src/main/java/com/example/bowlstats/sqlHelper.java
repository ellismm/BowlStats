package com.example.bowlstats;


import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import android.database.Cursor;
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
    private static final String gamesToday = "Day_Games";
    private static final String totalGames = "Total_Games";
    private static final String dayAverage = "Daily_Average";
    private static final String totalAverage = "Total_Average";
    private static final String highScore = "High_Score";
    private static final String lowScore = "Low_Score";
    private static final String dayPoints = "Day_Points";
    private static final String totalPoints = "Total_Points";
    private static final String wonToday = "Won_Today";
    private static final String wonTotal = "Won_Total";



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
                    dayPoints + " SMALLINT(900), " + totalPoints +  " BIGINT ) ";
            db.execSQL(createTable);
            return true;
        }
        return false;
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

    /**
     * record new scores and updates stats
     * @param name
     * @param score
     * @return
     */
    public boolean recordScore(String name, int score) {

        // Name of the specified persons database;
        NAME_DB = name;

        // Initializing temp variables to use throughout
        int tempInt, tempInt2, tempInt3;
        String tempDate;
        Float tempFloat, tempFloat2;
        long result;

        // Initializing cursors
        Cursor cursor1, cursor2;

        SQLiteDatabase db = getWritableDatabase();
//        SQLiteDatabase db2 = getReadableDatabase();
        ContentValues values = new ContentValues();

        DateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        tempDate = formatDate.format(date);
        System.out.println(tempDate);

        cursor2 = db.rawQuery("SELECT * FROM " + NAME_DB + " WHERE " + theDate + " = '" +
                tempDate + "'", null);
        System.out.println("number of rows in the table: " + cursor2.getCount());
        // If this is the first game of the day
        if(cursor2.getCount() == 0) {
            values.put(theDate, tempDate);
            values.put(Game1, score);
            values.put(Game2, -1);
            values.put(Game3, -1);
            values.put(gamesToday, 1);
            values.put(dayAverage,(float) score);
            values.put(dayPoints, score);
//            tempInt2 = cursor1.getInt(cursor1.getColumnIndex(dayPoints));
//            tempFloat = cursor1.getFloat(cursor1.getColumnIndex(dayAverage));

            // select all the current current rows to see if there is any games played yet
            cursor1 = db.rawQuery(" SELECT * FROM " + NAME_DB, null);

            // If this is the first game for this player
            if (cursor1.getCount() == 0) {
                values.put(totalGames, 1);
                values.put(totalPoints, score);
                values.put(dayPoints, score);
                values.put(lowScore, score);
                values.put(highScore, score);
                values.put(totalAverage, score);
            }

            // If this is not the first game for this player
            else {
                // get totalGames to add to it.
                cursor1.moveToLast();
                tempInt = cursor1.getInt(cursor1.getColumnIndex(totalGames));
                tempInt++;
                values.put(totalGames, tempInt);

                // Update the totalPoints for this player
                tempInt3 = cursor1.getInt(cursor1.getColumnIndex(totalPoints));
                tempInt3 += score;

                // Update the totalPoints and totalAverage for this player
                values.put(totalPoints, tempInt3);
                values.put(totalAverage, tempInt3 / tempInt);

                // get the low score and the high score for this player
                tempInt = cursor1.getInt(cursor1.getColumnIndex(lowScore));
                tempInt2 = cursor1.getInt(cursor1.getColumnIndex(highScore));

                // Update the low and high score for this player
                values.put(lowScore, tempInt < score ? tempInt : score);
                values.put(highScore, tempInt2 > score ? tempInt2 : score);
            }

            // insert the new row in the table
            Log.d(TAG, "update Stats: updating the stats after game 1 for the table " + NAME_DB);
            result = db.insert(NAME_DB, null, values);
            if(result == -1)
                return false;
        }

        // If this is the not the first game of the Day
        else if (cursor2.getCount() > 0) {
            // Initializing temp variables
            int intTemp1, intTemp2, intTemp3;

            // Get all the rows of from the table and move to the last row
            cursor1 = db.rawQuery(" SELECT * FROM " + NAME_DB, null);
            cursor1.moveToLast();

            // Determine whether to update game 2 or game 3
            intTemp1 = cursor1.getInt(cursor1.getColumnIndex(Game2));
            intTemp2 = cursor1.getInt(cursor1.getColumnIndex(Game3));
            // if the 2nd game
            if(intTemp1 == -1) {
                values.put(Game2, score);
                Log.d(TAG, "update Stats: updating the stats after game 2 for the table " + NAME_DB);

            }
            // If the 3rd game
            else if(intTemp2 == -1) {
                values.put(Game3, score);
                Log.d(TAG, "update Stats: updating the stats after game 3 for the table " + NAME_DB);
            }
            else if(intTemp2 > -1) {
                return false;
            }

            // Update the total games
            intTemp1 = cursor1.getInt(cursor1.getColumnIndex(totalGames));
            intTemp1++;
            values.put(totalGames, intTemp1);

            // Update the total points
            intTemp2 = cursor1.getInt(cursor1.getColumnIndex(totalPoints));
            intTemp2 += score;
            values.put(totalPoints, intTemp2);

            // Update the total Average
            values.put(totalAverage, (float) intTemp2 / (float) intTemp1);

            // Update the # of games for the day
            intTemp1 = cursor1.getInt(cursor1.getColumnIndex(gamesToday));
            intTemp1++;;
            values.put(gamesToday, intTemp1);

            // Update the # of points for the day
            intTemp2 = cursor1.getInt(cursor1.getColumnIndex(dayPoints));
            intTemp2 += score;
            values.put(dayPoints, intTemp2);

            // Update the average for the day
            values.put(dayAverage, (float) intTemp2 / (float) intTemp1);

            // Update the low and high score
            intTemp1 = cursor1.getInt(cursor1.getColumnIndex(lowScore));
            intTemp2 = cursor1.getInt(cursor1.getColumnIndex((highScore)));
            values.put(lowScore, intTemp1 < score ? intTemp1 : score);
            values.put(highScore, intTemp2 > score? intTemp2 : score);

            // update the results in the table
            result = db.update(NAME_DB, values, theDate + " = '"  +
                    tempDate + "'", null);
            if(result == -1)
                return false;
        }
        return true;
    }

    /**
     * Get the stats for the specified player
     * @param name
     */
    public HashMap<String, String> getStats(String name) {
        // List to add all the stats
        HashMap<String, String> stats = new HashMap<String, String>();

        // String to store a value from the database
        String tempString1, tempString2;

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + name, null);
        if(cursor.getCount() > 0) {
            cursor.moveToLast();
            tempString1 = cursor.getString(cursor.getColumnIndex(totalGames));
            stats.put(totalGames, tempString1);

            tempString1 = cursor.getString(cursor.getColumnIndex(totalAverage));
            stats.put(totalAverage, tempString1);

            tempString1 = cursor.getString(cursor.getColumnIndex(highScore));
            stats.put(highScore, tempString1);

            tempString1 = cursor.getString(cursor.getColumnIndex(lowScore));
            stats.put(lowScore, tempString1);

            tempString1 = cursor.getString(cursor.getColumnIndex(totalPoints));
            stats.put(totalPoints, tempString1);
        }

        return stats;
    }

    public int getLowScore(String name) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + name, null);
        if(cursor.getCount() > 0) {
            cursor.moveToLast();
            int low = cursor.getInt(cursor.getColumnIndex(lowScore));
            return low;
        }
        return -1;
    }

    public String lowestScore() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            String name, tempName;
            name = "";
            int score = 301, tempScore;
            do {
                tempName = cursor.getString(0);
                tempScore = getLowScore(tempName);
                if(tempScore < score) {
                    score = tempScore;
                    name = tempName;
                }
            } while(cursor.moveToNext());

            return name;
        }
        return null;
    }

    public int getHighScore(String name) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + name, null);
        if(cursor.getCount() > 0) {
            cursor.moveToLast();
            int low = cursor.getInt(cursor.getColumnIndex(lowScore));
            return low;
        }
        return -1;
    }

    public String highestScore() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            String name, tempName;
            name = "";
            int score = 0, tempScore;
            do {
                tempName = cursor.getString(0);
                tempScore = getHighScore(tempName);
                if(tempScore > score) {
                    score = tempScore;
                    name = tempName;
                }
            } while(cursor.moveToNext());

            return name;
        }
        return null;
    }

    public float getAverageScore(String name) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + name, null);
        if(cursor.getCount() > 0) {
            cursor.moveToLast();
            int low = cursor.getInt(cursor.getColumnIndex(lowScore));
            return low;
        }
        return -1;
    }

    public String averageScore() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            String name, tempName;
            name = "";
            float score = 0f, tempScore;
            do {
                tempName = cursor.getString(0);
                tempScore = getAverageScore(tempName);
                if(tempScore > score) {
                    score = tempScore;
                    name = tempName;
                }
            } while(cursor.moveToNext());

            return name;
        }
        return null;
    }

    public int getMostPoints(String name) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + name, null);
        if(cursor.getCount() > 0) {
            cursor.moveToLast();
            int low = cursor.getInt(cursor.getColumnIndex(lowScore));
            return low;
        }
        return -1;
    }

    public String mostPoints() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            String name, tempName;
            name = "";
            int score = 0, tempScore;
            do {
                tempName = cursor.getString(0);
                tempScore = getMostPoints(tempName);
                if(tempScore > score) {
                    score = tempScore;
                    name = tempName;
                }
            } while(cursor.moveToNext());

            return name;
        }
        return null;
    }

    public int getMostGames (String name) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + name, null);
        if(cursor.getCount() > 0) {
            cursor.moveToLast();
            int low = cursor.getInt(cursor.getColumnIndex(lowScore));
            return low;
        }
        return -1;
    }

    public String mostGames() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            String name, tempName;
            name = "";
            int score = 0, tempScore;
            do {
                tempName = cursor.getString(0);
                tempScore = getMostGames(tempName);
                if(tempScore > score) {
                    score = tempScore;
                    name = tempName;
                }
            } while(cursor.moveToNext());

            return name;
        }
        return null;
    }


}
