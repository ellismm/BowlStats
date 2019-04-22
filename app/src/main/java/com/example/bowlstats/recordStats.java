package com.example.bowlstats;

import android.app.DatePickerDialog;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;
import android.widget.DatePicker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class recordStats extends AppCompatActivity {
    DatePickerDialog picker;
    private Button record;
    private TextView nameToRecord;
    private EditText score, datePicker;
    sqlHelper sh = new sqlHelper(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_stats);
        record = findViewById(R.id.record);
        nameToRecord = findViewById(R.id.name);
        score  = findViewById(R.id.score);
        datePicker = findViewById(R.id.datePicker);

        // Find the player that is recording the stats
        // this info came from the choose player class
        Intent oldIntent = getIntent();
        final String message = oldIntent.getStringExtra("player");
        nameToRecord.setText(message + "'s score:");

        // Set the date to the current date
        DateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String tempDate2 = formatDate.format(date);
        datePicker.setText(tempDate2);

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
                    String tempDate = datePicker.getText().toString();

                    boolean added = sh.recordScore(message,
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

        // Choosing a date
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate();
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

    /**
     * Set the date in the date EditText
     */
    private void setDate() {
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);

        picker = new DatePickerDialog(recordStats.this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int month, int day) {
                month++;
                String dayS = Integer.toString(day);
                String yearS = Integer.toString(year);
                String monthS = Integer.toString(month);
                if(day < 10) {
                    dayS = "0" + dayS;
                }
                if(month < 10) {
                    monthS = "0" + monthS;
                }
                datePicker.setText(yearS + "-" + monthS + "-" + dayS);
            }
        }, year, month, day);
        picker.show();
    }
}
