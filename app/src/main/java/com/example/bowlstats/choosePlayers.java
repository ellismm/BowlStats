package com.example.bowlstats;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.view.View;
import android.view.View.OnClickListener;
import java.util.*;
import android.database.Cursor;
import android.widget.Toast;

public class choosePlayers extends AppCompatActivity {
    private static final String TAG = "ListPlayers";


    private Button menu;
    private ListView playersList;
    sqlHelper sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_players);
        menu = findViewById(R.id.menu);
        playersList = findViewById(R.id.allPlayers);
        playersList.setClickable(true);
        sh = new sqlHelper(this);

        // Variable for where to direct to
        final String direct;

        // Assign direct
        Intent oldIntent = getIntent();
        direct = oldIntent.getStringExtra("From Main");

        menu.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(choosePlayers.this, MainActivity.class);
                startActivity(intent);
            }
        });
        Log.d(TAG, "viewNames: Displaying playerssss in the data base");

        //get the data and append to a list
        Cursor player = sh.getPlayers();
        final ArrayList<String> players = new ArrayList<String>();
        while(player.moveToNext()) {
            //get the value from the database in column 1
            //the n add it to the ArrayList
            players.add(player.getString(0));

        }
        player.close();

        // create the list adapter and set the adapter
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, players);
        playersList.setAdapter(adapter);

        // If one of the names from the listView is clicked on
        playersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent;
                // Get name that was click on
                String nameToRecord = (String) playersList.getItemAtPosition(position);

                // Direct to the recordStats page
                if(direct.equals("To Record")) {
                    intent = new Intent(choosePlayers.this, recordStats.class);
                    intent.putExtra("player", nameToRecord);
                    startActivity(intent);
                }
                // Direct to the view stats page
                else if(direct.equals("To Stats")) {
                    intent = new Intent(choosePlayers.this, statsPage.class);
                    intent.putExtra("player", nameToRecord);
                    startActivity(intent);
                }

                // Direct to the edit stats page
                else if(direct.equals("To Edit")) {
                    intent = new Intent(choosePlayers.this, editStats.class);
                    intent.putExtra("player", nameToRecord);
                    startActivity(intent);
                }

                else
                    System.out.println("This did not work");

            }
        });
    }

//    playersList.setOnClickListerner

    /**
     * customizable toast
     * @param message
     */
    private void toastMessage(String message) {
        Toast.makeText(this,message, Toast.LENGTH_LONG);
    }
}
