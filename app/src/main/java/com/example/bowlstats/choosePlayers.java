package com.example.bowlstats;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;
import android.app.Activity;
import android.view.View;
import android.view.Menu;
import android.view.View.OnClickListener;

public class choosePlayers extends AppCompatActivity {
    private Button choose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_players);
        choose = findViewById(R.id.chooseButton);

        choose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(choosePlayers.this, recordStats.class);
                startActivity(myIntent);

            }
        });
    }
}
