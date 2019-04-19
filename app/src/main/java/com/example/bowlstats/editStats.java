package com.example.bowlstats;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.content.Intent;
import android.widget.TextView;

public class editStats extends AppCompatActivity {
    private Button menu, highScore, lowScore, highAverage, mostPoints, mostGames;
    private TextView message;
    String name;
    int score;
    float score2;
    sqlHelper sh = new sqlHelper(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_stats);

        menu = findViewById(R.id.menu);
        highScore = findViewById(R.id.highScore);
        lowScore = findViewById(R.id.lowScore);
        highAverage = findViewById(R.id.highAverage);
        mostPoints = findViewById(R.id.mostPoints);
        mostGames = findViewById(R.id.mostGames);

        message = findViewById(R.id.message);


        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(editStats.this, MainActivity.class);
                startActivity(myIntent);
            }
        });

        // Display the player with the highest all time score
        highScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = sh.highestScore();
                score = sh.getHighScore(name);
                message.setText("\n\n" + name + " has the highest score with: \n" + score);
            }
        });

        // Display the player with the lowest all time score
        lowScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = sh.lowestScore();
                score = sh.getLowScore(name);
                message.setText("\n\n" + name + " has the lowest score with: \n" + score);
            }
        });

        // Display the player with the highest average score
        highAverage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = sh.averageScore();
                score2 = sh.getAverageScore(name);
                message.setText("\n\n" + name + " has the highest average with: \n" + score2);
            }
        });

        // Display the player who has the most overall points
        mostPoints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = sh.mostPoints();
                score = sh.getMostPoints(name);
                message.setText("\n\n" + name + " has the most all time points with: \n" + score);
            }
        });

        // Display the player who has played the most games
        mostGames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = sh.mostGames();
                score = sh.getMostGames(name);
                message.setText("\n\n" + name + " has played the most games with: \n" + score + " games");
            }
        });

    }
}
