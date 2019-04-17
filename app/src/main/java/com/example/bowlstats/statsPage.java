package com.example.bowlstats;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.content.Intent;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.*;


public class statsPage extends AppCompatActivity {

    Button menu;
    ListView theStats;
    TextView playerName;
    HashMap<String, String> pStats;
    private static final String totalGames = "Total_Games";
    private static final String totalAverage = "Total_Average";
    private static final String highScore = "High_Score";
    private static final String lowScore = "Low_Score";
    private static final String totalPoints = "Total_Points";
    sqlHelper sh = new sqlHelper(this);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats_page);
        menu = findViewById(R.id.menu);
        theStats = findViewById(R.id.theStats);
        playerName = findViewById(R.id.playerName);
        //Get the name of the player to view the stats
        String thePlayer;
        Intent oldIntent = getIntent();
        thePlayer = oldIntent.getStringExtra("player");
        showStats(thePlayer);

        //set the header of the page
        playerName.setText("The stats for " + thePlayer);
        // Clicking the menu button
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(statsPage.this, MainActivity.class);
                startActivity(myIntent);
            }

        });
    }

    public void showStats(String player) {
        final ArrayList<String> playerStats = new ArrayList<>();
        pStats = sh.getStats(player);
        theStats = findViewById(R.id.theStats);
        String temp;

        // Add pstats to playerStats
        temp = "Total Games: " + pStats.get(totalGames);
        playerStats.add(temp);

        temp = "Average: " + pStats.get(totalAverage);
        playerStats.add(temp);

        temp = "Total Points: " + pStats.get(totalPoints);
        playerStats.add(temp);

        temp = "Low Score: " + pStats.get(lowScore);
        playerStats.add(temp);

        temp = "High Score: " + pStats.get(highScore);
        playerStats.add(temp);

        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, playerStats);
        theStats.setAdapter(adapter);
    }

}
