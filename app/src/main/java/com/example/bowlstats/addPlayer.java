package com.example.bowlstats;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;
import java.util.*;

public class addPlayer extends AppCompatActivity {

    sqlHelper sh = new sqlHelper(this);
    SQLiteDatabase db= sh.getWritableDatabase();
    private Button menu;
    private TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_player);
        menu = findViewById(R.id.menu);
        txt = findViewById(R.id.names);

        sh.addPlayer("Mark");
        sh.addPlayer("Daniel");
        sh.addPlayer("Thu");

        List<String> players = new LinkedList<String>();
        players = sh.displayPlayers();


        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(addPlayer.this, MainActivity.class);
                startActivity(myIntent);
            }
        });


    }



}
