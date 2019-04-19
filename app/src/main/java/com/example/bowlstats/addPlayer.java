package com.example.bowlstats;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.*;

public class addPlayer extends AppCompatActivity {

    sqlHelper sh = new sqlHelper(this);
//    SQLiteDatabase db = sh.getWritableDatabase();getWritableDatabase
//    if(db == null) {
//        System.out.println("this database is null");
//    }
    private Button menu, add, remove;
    private EditText txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_player);
        menu = findViewById(R.id.menu);
        add = findViewById(R.id.add);
        remove = findViewById(R.id.remove);
        txt = findViewById(R.id.name);
        sh = new sqlHelper(this);

        /**
         * if the add button is clicked then add the new name in the database
         * check if the name is already in the database
         * create a new table if the name is new
         */
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEntry = txt.getText().toString();
                System.out.println(txt.getText().toString());
                if(txt.length() != 0) {
//                    SQLiteDatabase db = new SQLiteDatabase();
                    boolean repeat = sh.createTable(newEntry);
                    if(repeat) {
                        addPlayer(newEntry);
                        txt.setText("");
                    }
                    else {
                        toastMessage("This user already exists");
                    }
                }
                else
                    toastMessage("You must put something in the text field!");
            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = txt.getText().toString();
                sh.removeTable(name);
                toastMessage("Player removed!!");
            }
        });

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(addPlayer.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void addPlayer (String player) {
        boolean insertData = sh.addPlayer(player);

        if(insertData)
            toastMessage("Data Successfully Inserted!");
        else
            toastMessage("Something went wrong");
    }

    /**
     * customizable toast
     * @param message
     */
    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }



}
