/**
 * Features to add
 * the average for the last 4 sessions
 * Whether it was the left or right lane
 * Get the sum of each game
 * number of games won
 *
 * April 18
 * Thu 96, 89, 99
 * Will 127, 148, 141
 * Danny 179, 152, 114
 * Mark 144, 193, 234
 *
 */

package com.example.bowlstats;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

import java.time.chrono.MinguoChronology;

public class MainActivity extends AppCompatActivity {
    private Button add_player, record, stats, viewStats, highScores;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        add_player = findViewById(R.id.addPlayer);
        record = findViewById(R.id.record);
        stats = findViewById(R.id.editStats);
        viewStats = findViewById((R.id.viewStats));
        highScores = findViewById(R.id.highScore);

        add_player.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, addPlayer.class);
                startActivity(myIntent);
            }
        });

        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, choosePlayers.class);
                myIntent.putExtra("From Main", "To Record");
                startActivity(myIntent);
            }
        });

        stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, choosePlayers.class);
                myIntent.putExtra("From Main", "To Edit");
                startActivity(myIntent);
            }
        });

        viewStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, choosePlayers.class);
                myIntent.putExtra("From Main", "To Stats");
                startActivity(myIntent);
            }
        });

        highScores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, highScores.class);
                startActivity(myIntent);
            }
        });
    }
}
