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
import android.app.Activity;
import android.view.View;
import android.view.Menu;
import android.view.View.OnClickListener;
import java.util.*;
import android.database.Cursor;
import android.widget.Toast;

public class choosePlayers extends AppCompatActivity {
    private static final String TAG = "ListPlayers";
    private Button choose;
    private ListView playersList;
    sqlHelper sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_players);
        choose = findViewById(R.id.chooseButton);
        playersList = findViewById(R.id.allPlayers);
        playersList.setClickable(true);
        sh = new sqlHelper(this);

        choose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
        Log.d(TAG, "viewNames: Displaying players in the data base");

        //get the data and append to a list
        Cursor player = sh.getPlayers();
        final ArrayList<String> players = new ArrayList<String>();
        while(player.moveToNext()) {
            //get the value from the database in column 1
            //the n add it to the ArrayList
            players.add(player.getString(0));

        }

        // create the list adapter and set the adapter
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, players);
        playersList.setAdapter(adapter);

        playersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(choosePlayers.this, recordStats.class);

                String nameToRecord = (String) playersList.getItemAtPosition(position);
                intent.putExtra("playersList", nameToRecord);

                startActivity(intent);
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
