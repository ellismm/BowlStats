package com.example.bowlstats;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import java.util.*;


public class statsPage extends AppCompatActivity {

    Button menu;
    HashMap<String, String> stats;
    String Game1, Game2, Game3, gamesToday, totalGames, dayAverage, totalAverage, highScore, lowScore,
            dayPoints, totalPoints;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats_page);
        menu = findViewById(R.id.menu);

        // Clicking the menu button
        menu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(statsPage.this, MainActivity.class);
                startActivity(myIntent);
            }

        });


    }

}
