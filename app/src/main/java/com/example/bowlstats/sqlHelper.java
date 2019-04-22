package com.example.bowlstats;


import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import android.database.Cursor;
import android.database.sqlite.SQLiteTableLockedException;
import android.util.Log;

public class sqlHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    static final String NAMES_TABLE = "Names";
    static final String GAMES_DATE = "Dates";

    // for the NAMES_TABLE table
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
    private static final String gameOneGames = "Game_One_Games";
    private static final String gameTwoGames = "Game_Two_Games";
    private static final String gameThreeGames = "Game_Three_Games";
    private static final String gameOnePoints = "Game_One_Points";
    private static final String gameTwoPoints = "Game_Two_Points";
    private static final String gameThreePoints = "Game_Three_Points";
    private static final String gameOneAverage = "Game_One_Average";
    private static final String gameTwoAverage = "Game_Two_Average";
    private static final String gameThreeAverage = "Game_Three_Average";



    public sqlHelper(Context context) {
        super(context, NAMES_TABLE, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE IF NOT EXISTS " + NAMES_TABLE + "(" +  COL1 + " TEXT " + ")";
//        String deleteTable = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(createTable);
        createTable = "CREATE TABLE IF NOT EXISTS " + GAMES_DATE + "(" + theDate + " DATE )";
        db.execSQL(createTable);
        System.out.println("Table created ");
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
        db.execSQL("DROP TABLE IF EXISTS " + NAMES_TABLE);
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
                    dayPoints + " SMALLINT(900), " + totalPoints +  " BIGINT, " + wonToday + " SMALLINT, " +
                    wonTotal + " SMALLINT, " + gameOneGames + " SMALLINT, " + gameTwoGames + " SMALLINT, " +
                    gameThreeGames + " SMALLINT, " + gameOnePoints + " BIGINT, " + gameTwoPoints +
                    " BIGINT, " + gameThreePoints + " BIGINT, " + gameOneAverage + " FLOAT, " +
                    gameTwoAverage + " FLOAT, " + gameThreeAverage + " FLOAT " + ") ";
            db.execSQL(createTable);
            return true;
        }
        return false;
    }

    // TODO: create a function that deletes a player from the database
    public void removeTable(String name) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + name);
        db.execSQL("DELETE FROM " + NAMES_TABLE + " WHERE " + COL1 + " = '" + name + "'");
    }

    /**
     * This function will add a given name to the Name database
     */
    public boolean addPlayer(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL1, name);

        Log.d(TAG, "addData: Adding " + name + " to " + NAMES_TABLE);
        long result = db.insert(NAMES_TABLE, null, values);

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
        String query = "SELECT * FROM " + NAMES_TABLE;
        Cursor player = db.rawQuery(query, null);
        return player;
    }

    /**
     * record new scores and updates stats
     * @param name
     * @param score
     * @return
     */
    public boolean recordScore(String name, int score, String date) {

        // Name of the specified persons database;
        NAME_DB = name;

        // Initializing temp variables to use throughout
        int tempInt, tempInt2, tempInt3;
        Float tempFloat, tempFloat2;
        long result;

        // Initializing cursors
        Cursor cursor1, cursor2;

        SQLiteDatabase db = getWritableDatabase();
//        SQLiteDatabase db2 = getReadableDatabase();
        ContentValues values = new ContentValues();
        ContentValues dateValues = new ContentValues();

        dateValues.put(theDate, date);

        cursor2 = db.rawQuery("SELECT * FROM " + NAME_DB + " WHERE " + theDate + " = '" +
                date + "'", null);
        System.out.println("number of rows in the table: " + cursor2.getCount());
        // If this is the first game of the day
        if(cursor2.getCount() == 0) {
            values.put(theDate, date);
            values.put(Game1, score);
            values.put(Game2, -1);
            values.put(Game3, -1);
            values.put(gamesToday, 1);
            values.put(dayAverage,(float) score);
            values.put(dayPoints, score);
            values.put(wonToday, 0);
            values.put(wonTotal, 0);


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
                values.put(gameOneGames, 1);
                values.put(gameOnePoints, score);
                values.put(gameOneAverage, (float) score);
                values.put(gameTwoGames, 0);
                values.put(gameTwoPoints, 0);
                values.put(gameTwoAverage, 0.0);
                values.put(gameThreeGames, 0);
                values.put(gameThreePoints, 0);
                values.put(gameThreeAverage, 0.0);
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
                values.put(totalAverage, (float) tempInt3 / (float) tempInt);

                // get the low score and the high score for this player
                tempInt = cursor1.getInt(cursor1.getColumnIndex(lowScore));
                tempInt2 = cursor1.getInt(cursor1.getColumnIndex(highScore));

                // Update the low and high score for this player
                values.put(lowScore, tempInt < score ? tempInt : score);
                values.put(highScore, tempInt2 > score ? tempInt2 : score);

                // Update the Game one games
                tempInt = cursor1.getInt(cursor1.getColumnIndex(gameOneGames));
                tempInt++;
                values.put(gameOneGames, tempInt);

                // Update the game one points
                tempInt2 = cursor1.getInt(cursor1.getColumnIndex(gameOnePoints));
                tempInt2 += score;
                values.put(gameOnePoints, tempInt2);

                // Update the game one average
                values.put(gameOneAverage, (float) tempInt2 / (float) tempInt);

                // Update the Game two games
                tempInt = cursor1.getInt(cursor1.getColumnIndex(gameTwoGames));
                values.put(gameTwoGames, tempInt);

                // Update the game two points
                tempInt2 = cursor1.getInt(cursor1.getColumnIndex(gameTwoPoints));
                values.put(gameTwoPoints, tempInt2);

                // Update the game two average
                values.put(gameTwoAverage, (float) tempInt2 / (float) tempInt);

                // Update the Game three games
                tempInt = cursor1.getInt(cursor1.getColumnIndex(gameThreeGames));
                tempInt++;
                values.put(gameThreeGames, tempInt);

                // Update the game three points
                tempInt2 = cursor1.getInt(cursor1.getColumnIndex(gameThreePoints));
                values.put(gameThreePoints, tempInt2);

                // Update the game three average
                values.put(gameThreeAverage, (float) tempInt2 / (float) tempInt);
            }

            // insert the new row in the table
            Log.d(TAG, "update Stats: updating the stats after game 1 for the table " + NAME_DB);
            result = db.insert(NAME_DB, null, values);
            db.insert(GAMES_DATE, null, dateValues);
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

                // Update the Game one games
                tempInt = cursor1.getInt(cursor1.getColumnIndex(gameTwoGames));
                tempInt++;
                values.put(gameTwoGames, tempInt);

                // Update the game one points
                tempInt2 = cursor1.getInt(cursor1.getColumnIndex(gameTwoPoints));
                tempInt2 += score;
                values.put(gameTwoPoints, tempInt2);

                // Update the game one average
                values.put(gameTwoAverage, (float) tempInt2 / (float) tempInt);

            }
            // If the 3rd game
            else if(intTemp2 == -1) {
                values.put(Game3, score);
                Log.d(TAG, "update Stats: updating the stats after game 3 for the table " + NAME_DB);

                // Update the Game one games
                tempInt = cursor1.getInt(cursor1.getColumnIndex(gameThreeGames));
                tempInt++;
                values.put(gameThreeGames, tempInt);

                // Update the game one points
                tempInt2 = cursor1.getInt(cursor1.getColumnIndex(gameThreeAverage));
                tempInt2 += score;
                values.put(gameThreeGames, tempInt2);

                // Update the game one average
                values.put(gameThreeGames, (float) tempInt2 / (float) tempInt);
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
                    date + "'", null);
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

            tempString1 = cursor.getString((cursor.getColumnIndex(wonTotal)));
            stats.put(wonTotal, tempString1);

            tempString1 = cursor.getString(cursor.getColumnIndex(gameOneAverage));
            stats.put(gameOneAverage, tempString1);

            tempString1 = cursor.getString(cursor.getColumnIndex(gameTwoAverage));
            stats.put(gameTwoPoints, tempString1);

            tempString1 = cursor.getString(cursor.getColumnIndex(gameThreeAverage));
            stats.put(gameThreeAverage, tempString1);
        }

        return stats;
    }

    /**
     * Getthe low score column from the last column for the specified player
     * @param name
     * @return
     */
    public int getLowScore(String name) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + name, null);
        if(cursor.getCount() > 0) {
            cursor.moveToLast();
            int low = cursor.getInt(cursor.getColumnIndex(lowScore));
            return low;
        }
        return 302;
    }

    /**
     * find the lowest score from all the players
     * @return
     */
    public String lowestScore() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + NAMES_TABLE, null);
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

    /**
     * Get the highest score column from the last row for the specified player
     * @param name
     * @return
     */
    public int getHighScore(String name) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + name, null);
        if(cursor.getCount() > 0) {
            cursor.moveToLast();
            int low = cursor.getInt(cursor.getColumnIndex(highScore));
            return low;
        }
        return -1;
    }

    /**
     * Find the player with highest Score
     * @return
     */
    public String highestScore() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + NAMES_TABLE, null);
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

    /**
     * Get the average score for a given game or a or the total from the last column for the specified player
     * @param name
     * @return
     */
    private float getAverageScoreMain(String name, String column) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + name, null);
        if(cursor.getCount() > 0) {
            cursor.moveToLast();
            int low = cursor.getInt(cursor.getColumnIndex(column));
            return low;
        }
        return -1;
    }

    /**
     * Get the overall average score column from the last column for the specified player
     * @param name
     * @return
     */
    public float getAverageScore(String name) {
        return getAverageScoreMain(name, totalAverage);
    }

    /**
     * Get the average for game one from the last column for the specified player
     * @param name
     * @return
     */
    public float getAverageScoreGameOne(String name) {
        return getAverageScoreMain(name, gameOneAverage);
    }

    /**
     * Get the average for game one from the last column for the specified player
     * @param name
     * @return
     */
    public float getAverageScoreGameTwo(String name) {
        return getAverageScoreMain(name, gameTwoAverage);
    }

    /**
     * Get the average for game one from the last column for the specified player
     * @param name
     * @return
     */
    public float getAverageScoreGameThree(String name) {
        return getAverageScoreMain(name, gameThreeAverage);
    }


    /**
     * Find the player with the highest overall average
     * @return
     */
    public String averageScore() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + NAMES_TABLE, null);
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

    /**
     * Find the player with the highest overall Game one average
     * @return
     */
    public String averageScoreGameOne() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + NAMES_TABLE, null);
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            String name, tempName;
            name = "";
            float score = 0f, tempScore;
            do {
                tempName = cursor.getString(0);
                tempScore = getAverageScoreGameOne(tempName);
                if(tempScore > score) {
                    score = tempScore;
                    name = tempName;
                }
            } while(cursor.moveToNext());

            return name;
        }
        return null;
    }

    /**
     * Find the player with the highest overall Game 2 average
     * @return
     */
    public String averageScoreGameTwo() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + NAMES_TABLE, null);
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            String name, tempName;
            name = "";
            float score = 0f, tempScore;
            do {
                tempName = cursor.getString(0);
                tempScore = getAverageScoreGameTwo(tempName);
                if(tempScore > score) {
                    score = tempScore;
                    name = tempName;
                }
            } while(cursor.moveToNext());

            return name;
        }
        return null;
    }

    /**
     * Find the player with the highest overall Game 3 average
     * @return
     */
    public String averageScoreGameThree() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + NAMES_TABLE, null);
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            String name, tempName;
            name = "";
            float score = 0f, tempScore;
            do {
                tempName = cursor.getString(0);
                tempScore = getAverageScoreGameThree(tempName);
                if(tempScore > score) {
                    score = tempScore;
                    name = tempName;
                }
            } while(cursor.moveToNext());

            return name;
        }
        return null;
    }

    /**
     * Get the most points column in the last row for the specified player
     * @param name
     * @return
     */
    public int getMostPoints(String name) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + name, null);
        if(cursor.getCount() > 0) {
            cursor.moveToLast();
            int low = cursor.getInt(cursor.getColumnIndex(totalPoints));
            return low;
        }
        return -1;
    }

    /**
     * Find the player with the most points
     * @return
     */
    public String mostPoints() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + NAMES_TABLE, null);
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

    /**
     * Get the most games column from the last row for the specified player
     * @param name
     * @return
     */
    public int getMostGames (String name) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + name, null);
        if(cursor.getCount() > 0) {
            cursor.moveToLast();
            int low = cursor.getInt(cursor.getColumnIndex(totalGames));
            return low;
        }
        return -1;
    }

    /**
     * find the player who has played the most games
     * @return
     */
    public String mostGames() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + NAMES_TABLE, null);
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

    public int getGameOne (String name, String date) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + name + " WHERE " + theDate + " = '" +
                date + "'", null);
        if(cursor.getCount() > 0) {
            cursor.moveToLast();
            int score = cursor.getInt(cursor.getColumnIndex(Game1));
            return score;
        }
        return -1;
    }

    public int getGameTwo (String name, String date) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + name + " WHERE " + theDate + " = '" +
                date + "'", null);
        if(cursor.getCount() > 0) {
            cursor.moveToLast();
            int score = cursor.getInt(cursor.getColumnIndex(Game2));
            System.out.println("The score of game 2 has not been recorded: " + score);
            return score;
        }
        return -1;
    }

    public int getGameThree (String name, String date) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + name + " WHERE " + theDate + " = '" +
                date + "'", null);
        if(cursor.getCount() > 0) {
            cursor.moveToLast();
            int score = cursor.getInt(cursor.getColumnIndex(Game3));
            return score;
        }
        return -1;
    }

    public void editScore(String name, String date, String game, int scoreChange) {
        SQLiteDatabase db = getWritableDatabase();;
        Cursor cursor = db.rawQuery("SELECT * FROM " + name + " WHERE "  + theDate + " = '" +
                date + "'", null);
        ContentValues values = new ContentValues();
        values.put(game, scoreChange);
        if(cursor.getCount() > 0) {
            cursor.moveToLast();
            db.update(name, values, theDate + " = '"  +
                    date + "'", null);

        }
        reEvaluateTable(name);
    }

    /**
     * If a score is changed the all the other columns should be reEvaluated
     * @param name
     */
    public void reEvaluateTable(String name) {
        String tempString, tempDate;
        int tempInt1, tempInt2, tempInt3, points = 0, games = 0, point , gamer = 1, high = -1, low = 301;
        float average;
        SQLiteDatabase db = getWritableDatabase();
        NAME_DB = name;
        Cursor cursor = db.rawQuery("SELECT * FROM " + NAME_DB, null);
        ContentValues values = new ContentValues();
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                //Get the scores of the games played
                tempDate = cursor.getString(cursor.getColumnIndex(theDate));
                tempInt1 = cursor.getInt(cursor.getColumnIndex(Game1));
                tempInt2 = cursor.getInt(cursor.getColumnIndex(Game2));
                tempInt3 = cursor.getInt(cursor.getColumnIndex(Game3));
                System.out.println("Trying to find a specific Date " + tempDate);

                // If the user played all 3 games
                if(tempInt3 != -1) {

                    if(tempInt3 > high) {
                        high = tempInt3;
                    }
                    if(tempInt2 > high) {
                        high = tempInt2;
                    }
                    if(tempInt3 < low) {
                        low = tempInt3;
                    }
                    if(tempInt2 < low) {
                        low = tempInt2;
                    }

                    point = tempInt1 + tempInt2 + tempInt3;
                    gamer = 3;
                    values.put(gamesToday, 3);
                    points += tempInt1 + tempInt2 + tempInt3;
                    games += 3;
                }
                // If the user only played game 1 and 2
                else if(tempInt2 != -1) {

                    if(tempInt2 > high) {
                        high = tempInt2;
                    }
                    if(tempInt2 < low) {
                        low = tempInt2;
                    }
                    point = tempInt1 + tempInt2;
                    gamer = 2;
                    values.put(gamesToday, 2);
                    points += tempInt1 + tempInt2;
                    games += 2;
                }
                // If the user played only the first game
                else {
                    point = tempInt1;
                    gamer = 1;
                    values.put(gamesToday, 1);
                    points += tempInt1;
                    games++;
                }

                if(tempInt1 > high) {
                    high = tempInt1;
                }
                if(tempInt1 < low) {
                    low = tempInt1;
                }

                // Put the updates in values contentValues
                values.put(totalGames, games);
                values.put(totalPoints, points);

                average = points / games;

                values.put(totalAverage, average);

                average = point / gamer;

                values.put(highScore, high);
                values.put(lowScore, low);
                values.put(dayPoints, point);
                values.put(dayAverage, average);

                // Update the table with the new changes
                db.update(NAME_DB, values, theDate +  " = '" + tempDate + "'", null);

            } while(cursor.moveToNext());
        }
        System.out.println("This table was successfully re-evaluated");
    }

    /**
     * This method updates the columns "wonToday" and "wonTotal" for each player in the database
     */
    public void winCalculations() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor date_cursor = db.rawQuery("SELECT * FROM " + GAMES_DATE, null);

        // updated games wonToday one date at a time
        if(date_cursor.getCount() > 0) {
            date_cursor.moveToFirst();
            String date = date_cursor.getString(date_cursor.getColumnIndex(theDate));
            do {
                Cursor name_cursor = db.rawQuery("SELECT * FROM " + NAMES_TABLE, null);
                if(name_cursor.getCount() > 0) {
                    winCalculationsOneRow(name_cursor, date);
                }
            } while(date_cursor.moveToNext());
        }

        // Update games won Total one name at a time
        Cursor name_cursor = db.rawQuery("SELECT * FROM " + NAMES_TABLE, null);
        if(name_cursor.getCount() > 0) {
            name_cursor.moveToFirst();
            do {
                String name = name_cursor.getString(name_cursor.getColumnIndex(COL1));
                updateWonTotal(name);
            } while(name_cursor.moveToNext());
        }
    }

    /**
     * update the gamesWonToday column on the specified date
     * @param name_cursor
     * @param date
     */
    private void winCalculationsOneRow(Cursor name_cursor, String date) {
        SQLiteDatabase db = getWritableDatabase();
        name_cursor.moveToFirst();
        String tempName = name_cursor.getString(name_cursor.getColumnIndex(COL1));
        String winner_one, winner_two, winner_three;
        winner_one = winner_two = winner_three = tempName;
        int score_one = -1, score_two = -1, score_three = -1, tempScore;

//        // Get the scores of the first pe
//        score_one = name_cursor.getInt(name_cursor.getColumnIndex(Game1));
//        score_two = name_cursor.getInt(name_cursor.getColumnIndex(Game2));
//        score_three = name_cursor.getInt(name_cursor.getColumnIndex(Game3));

        do {

            // Determine if the player has played on the specified date
            tempName = name_cursor.getString(name_cursor.getColumnIndex(COL1));
            Cursor data_cursor = db.rawQuery("SELECT * FROM " + tempName + " WHERE " + theDate  +
                    " = '" + date + "'", null);

            // If this player has played any games on the specified date;
            if(data_cursor.getCount() > 0) {
                data_cursor.moveToFirst();

                // Compare the score for the first game
                tempScore = getGameOne(tempName, date);
                if(tempScore > score_one) {
                    score_one = tempScore;
                    winner_one = tempName;
                }

                // Compare the score for the second game;
                tempScore = getGameTwo(tempName, date);
                if(tempScore > score_two) {
                    score_two = tempScore;
                    winner_two = tempName;
                }

                // Compare the score for the third game
                tempScore = getGameThree(tempName, date);
                if(tempScore > score_three) {
                    score_three = tempScore;
                    winner_three = tempName;
                }
            }
        } while(name_cursor.moveToNext());

        // Update the winner for the first game on the specified date
        ContentValues values = new ContentValues();
        Cursor tempCursor = db.rawQuery("SELECT " + wonToday + " FROM " + winner_one +
                " WHERE " + theDate + " = '" + date, null);
        tempCursor.moveToFirst();
        tempScore = tempCursor.getInt(tempCursor.getColumnIndex(wonToday));
        tempScore++;
        values.put(wonToday, tempScore);
        db.update(winner_one, values, theDate + " = '" + date, null);

        if(score_two != -1) {
            // Update the winner for the second game on the specified date
            tempCursor = db.rawQuery("SELECT " + wonToday + " FROM " + winner_two +
                    " WHERE " + theDate + " = '" + date, null);
            tempCursor.moveToFirst();
            tempScore = tempCursor.getInt(tempCursor.getColumnIndex(wonToday));
            tempScore++;
            values.put(wonToday, tempScore);
            db.update(winner_two, values, theDate + " = '" + date, null);
        }

        if(score_three != -1) {
            // Update the winner for the third game on the specified date
            tempCursor = db.rawQuery("SELECT " + wonToday + " FROM " + winner_three +
                    " WHERE " + theDate + " = '" + date, null);
            tempCursor.moveToFirst();
            tempScore = tempCursor.getInt(tempCursor.getColumnIndex(wonToday));
            tempScore++;
            values.put(wonToday, tempScore);
            db.update(winner_three, values, theDate + " = '" + date, null);
        }
    }

    /**
     * This method goes through every row one at a time of given players table and calculates total
     * games won
     * @param name
     */
    private void updateWonTotal(String name) {
        SQLiteDatabase db = getWritableDatabase();
        int totalWon = 0, tempWon;
        String date;
        Cursor cursor = db.rawQuery("SELECT " + wonToday + ", " + theDate +  " FROM " + name, null);
        // Determine if the player played any games yet
        if(cursor.getCount() > 0) {

            cursor.moveToFirst();
            // Update each row won at a time
            do {
                tempWon = cursor.getInt(cursor.getColumnIndex(wonToday));
                totalWon += tempWon;
                date = cursor.getString(cursor.getColumnIndex(theDate));
                ContentValues values = new ContentValues();
                values.put(wonTotal, totalWon);
                db.update(name, values, theDate + " = '" + date + "'", null);
            } while(cursor.moveToNext());
        }
    }

    /**
     * Returns the # of games won a specified date for a specified player
     * @param name
     * @param date
     * @return
     */
    public int getGamesWonToday(String name, String date) {
        SQLiteDatabase db = getReadableDatabase();
        int won;

        // Determine if the player "name" has played any games on this date
        Cursor cursor = db.rawQuery("SELECT " + wonToday + " FROM " +
                name + " WHERE " + theDate + " = '" + date + "'", null);

        // If the player has played any games on this date
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            won = cursor.getInt(cursor.getColumnIndex(wonToday));
            return won;
        }

        return -1;
    }

    /**
     * Returns the total number of games won up to the specified date for the specified player
     * @param name
     * @param date
     * @return
     */
    public int getGamesWonTotal(String name, String date) {
        SQLiteDatabase db = getReadableDatabase();
        int won;

        // Determine if the player has played any games on this date
        Cursor cursor = db.rawQuery("SELECT " + wonTotal + " FROM " +
                " WHERE " + theDate + " = '" + date + "'", null);

        // If the player has played any games on the specified date
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            won = cursor.getInt(cursor.getColumnIndex(wonTotal));
            return won;
        }

        // Player has not played any games on the specified date
        return -1;
    }

    /**
     * return how many total games won by a single given player
     * @param name
     * @return
     */
    public int mostGamesWonByPlayer(String name) {
        SQLiteDatabase db = getReadableDatabase();

        //Determine if the player has played any games
        Cursor data_cursor = db.rawQuery("SELECT " + wonTotal +  " FROM " + name, null);

        //If the player has played at least one game
        if(data_cursor.getCount() > 0) {
            data_cursor.moveToLast();
            int won = data_cursor.getInt(data_cursor.getColumnIndex(wonTotal));
            return won;
        }

        // If this player hasn't played any games
        return -1;
    }

    /**
     * Return the name of the person that has won the most games
     * @return
     */
    public String mostGamesWon() {
        SQLiteDatabase db = getReadableDatabase();
        String name, winner = null;
        int wins = -1, tempWins;

        // Determine if there currently are any players in the database
        Cursor name_cursor = db.rawQuery("SELECT * FROM " + NAMES_TABLE, null);

        // If there is at least won player in the DataBase
        if(name_cursor.getCount() > 0) {
            name_cursor.moveToFirst();
            do {
                name = name_cursor.getString(name_cursor.getColumnIndex(COL1));
                tempWins = mostGamesWonByPlayer(name);
                if(tempWins > wins) {
                    wins = tempWins;
                    winner = name;
                }
            } while(name_cursor.moveToNext());

            return winner;
        }

        // If there are no names in the database then there are now winners
       return null;
    }

    /**
     * Find the average of the last four sessions for a specified player
     * @return
     */
    public float lastFour(String name) {
        float average = -1f;
        int games = 0, points = 0;
        SQLiteDatabase db = getReadableDatabase();
        int i = 0;

        // Determine if this player has played any games
        Cursor cursor = db.rawQuery("SELECT " + gamesToday + ", " +  dayPoints + " FROM " +
                name, null);

        // If the player has played at least one game
        if(cursor.getCount() > 0) {
            cursor.moveToLast();
            do {
                games += cursor.getInt(cursor.getColumnIndex(gamesToday));
                points += cursor.getInt(cursor.getColumnIndex(dayPoints));
                i++;

            } while(cursor.moveToPrevious() && i < 4);

            // Calculate and return the average
            average = points / games;
            return average;
        }

        // This player has not played any games yet
        return -1;
    }

    /**
     * Return the player with highest last 4 sessions average
     * @return
     */
    public String highestLastFour() {
        SQLiteDatabase db = getReadableDatabase();
        String winner = null, loser;
        float averageWinner = -1f, averageLoser;

        // Determine if there are any players in the database
        Cursor cursor = db.rawQuery("SELECT * FROM " + NAMES_TABLE, null);

        // If there are any players in the database
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                loser = cursor.getString(cursor.getColumnIndex(COL1));
                averageLoser = lastFour(loser);
                if(averageLoser > averageWinner) {
                    winner = loser;
                    averageWinner = averageLoser;
                }

            } while(cursor.moveToNext());

            // Return the winner
            return winner;
        }

        // There are no players in the database
        return null;

    }

}
