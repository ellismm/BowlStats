package com.example.bowlstats;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class highScores extends AppCompatActivity {
    private Button home, highScore, lowScore, highAverage, mostPoints, mostGames, won,
            lastFour, gameOne, gameTwo, gameThree;
    private TextView message;
    String name;
    int score;
    float score2;
    sqlHelper sh = new sqlHelper(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores2);

        home = findViewById(R.id.home);
        highScore = findViewById(R.id.highScore);
        lowScore = findViewById(R.id.lowScore);
        highAverage = findViewById(R.id.highAverage);
        mostPoints = findViewById(R.id.mostPoints);
        mostGames = findViewById(R.id.mostGames);
        lastFour = findViewById(R.id.lastFour);
        won = findViewById(R.id.gamesWon);
        gameOne = findViewById(R.id.gameOne);
        gameTwo = findViewById(R.id.gameTwo);
        gameThree = findViewById(R.id.gameThree);

        message = findViewById(R.id.message);
        sh.winCalculations();


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(highScores.this, MainActivity.class);
                startActivity(myIntent);
            }
        });

        // Display the player with the highest all time score
        highScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = sh.highestScore();
                score = sh.getHighScore(name);
                System.out.println(score + " " + name);
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

        // Display the player who has the highest average for the last 4 sessions
        lastFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = sh.highestLastFour();
                score2 = sh.lastFour(name);
                message.setText("\n\n" + name + " has the highest average for the last 4 sessions with: \n" + score2);
            }
        });

        // Display the player who has won the most games
        won.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = sh.mostGamesWon();
                score = sh.mostGamesWonByPlayer(name);
                message.setText("\n\n" + name + " has won the most games with: \n" + score + " games");
            }
        });

        // Display the player who has highest game one average
        gameOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = sh.averageScoreGameOne();
                score2 = sh.getAverageScoreGameOne(name);
                message.setText("\n\n" + name + " has a Game 1 average of: \n" + score2);
            }
        });

        // Display the player who has highest game two average
        gameTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = sh.averageScoreGameTwo();
                score2 = sh.getAverageScoreGameTwo(name);
                message.setText("\n\n" + name + " has a Game 2 average of: \n" + score2);
            }
        });

        // Display the player who has highest game three average
        gameThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = sh.averageScoreGameThree();
                score2 = sh.getAverageScoreGameThree(name);
                message.setText("\n\n" + name + " has a Game 3 average of: \n" + score2);
            }
        });
    }
}
