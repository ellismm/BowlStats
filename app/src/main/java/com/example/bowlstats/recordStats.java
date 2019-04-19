package com.example.bowlstats;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class recordStats extends AppCompatActivity {

    private Button record;
    private TextView nameToRecord;
    private EditText score;
    sqlHelper sh = new sqlHelper(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_stats);
        record = findViewById(R.id.record);
        nameToRecord = findViewById(R.id.name);
        score  =findViewById(R.id.score);

        Intent oldIntent = getIntent();
        String message = oldIntent.getStringExtra("player");
        nameToRecord.setText(message);

        // When clicking the record button
        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theScore = -1;
                if(score.length() > 0) {
                    try {
                        theScore = Integer.parseInt(score.getText().toString());
                    }
                    catch (Exception e){
                        theScore = -1;
                    }
                }
                if(theScore > -1 && theScore <= 300) {
                    DateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = new Date();
                    String tempDate = formatDate.format(date);

                    boolean added = sh.recordScore(nameToRecord.getText().toString(),
                            Integer.parseInt(score.getText().toString()), tempDate);

                    if(added) {
                        toastMessage("Stats successfully updated");
                        score.setText("");
                    }
                    else {
                        toastMessage("Did not add record");
                    }

//                    Intent myIntent = new Intent(recordStats.this, MainActivity.class);
//                    startActivity(myIntent);
                }
                else {
                    toastMessage("This is an invalid input");
                    score.setText("");
                }
            }
        });
    }

    /**
     * customizable toast
     * @param message
     */
    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}
