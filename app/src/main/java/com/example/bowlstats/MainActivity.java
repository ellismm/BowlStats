package com.example.bowlstats;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

import java.time.chrono.MinguoChronology;

public class MainActivity extends AppCompatActivity {
    private Button add_player, record, stats, viewStats;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        add_player = findViewById(R.id.addPlayer);
        record = findViewById(R.id.record);
        stats = findViewById(R.id.editStats);
        viewStats = findViewById((R.id.viewStats));

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
                startActivity(myIntent);
            }
        });

        stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, editStats.class);
                startActivity(myIntent);
            }
        });

        viewStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, statsPage.class);
                startActivity(myIntent);
            }
        });
    }
}
