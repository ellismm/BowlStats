package com.example.bowlstats;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

public class sqlHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private static final String TABLE_NAME = "stats_sheet";
    private static final String COL1 = "Date";
    private static final String COL2 = "Game1";
    private static final String COL3 = "Game2";
    private static final String COL4 = "Game3";
    private static final String COL5 = "Day Games";
    private static final String COL6 = "Total Games";
    private static final String COL7 = "Total Average";
    private static final String COL8 = "High Score";
    private static final String COL9 = "Low Score";
    private static final String COL10 = "Total points";



    public sqlHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" + COL1 + "DATE," + COL2 + "SMALLINT," +
                COL3 + "SMALLINT," + COL4 + "SMALLINT," + COL5 + "SMALLINT," + COL6 + "FLOAT," + COL7 +
                "SMALLINT," + COL8 + "SMALLINT," + COL9 + "SMALLINT," + COL10 + "SMALLINT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
