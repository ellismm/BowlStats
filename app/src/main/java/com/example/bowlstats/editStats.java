package com.example.bowlstats;

import android.app.DatePickerDialog;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SoundEffectConstants;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLOutput;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class editStats extends AppCompatActivity {
    sqlHelper sh = new sqlHelper(this);
    DatePickerDialog picker;
    Button home, change;
    Spinner game;
    EditText date, editScore;
    TextView fromScore, fromText;
    int tempInt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_stats);

        home = findViewById(R.id.menu);
        change = findViewById(R.id.change);
        game = findViewById(R.id.game);
        date = findViewById(R.id.datePicker);
        editScore = findViewById((R.id.editScore));
        fromScore = findViewById(R.id.scoreText);

        // initialize the EditText field to the current date
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date newDate = new Date();
        String theDate = format.format(newDate);
        date.setText(theDate);

        // Initialize the game scroll
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.games, android.R.layout.simple_spinner_item);
        game.setAdapter(adapter);

        // Get the player that is editing the score
        // This name comes from choose player
        Intent oldIntent = getIntent();
        final String name = oldIntent.getStringExtra("player");

        // Get the score for game 1 for the current date if it exists
        tempInt = sh.getGameOneScore(name, theDate);
        fromScore.setText(Integer.toString(tempInt));

        // Clicking on the home button
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(editStats.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // Clicking on the date EditText
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate();
            }
        });

        // Clicking on the change score button
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = game.getSelectedItem().toString();
                tempInt = Integer.parseInt(fromScore.getText().toString());
                String tempDate = date.getText().toString();

                // If the current score is -1 then that means the player hasn't played a game yet
                if(tempInt == -1) {
                    toastMessage("There is no score to change for this game");
                }
                else {
                    // catch any errors in the input of the score change
                    try {
                        tempInt = Integer.parseInt(editScore.getText().toString());
                        if(tempInt < 0 || tempInt > 300) {
                            toastMessage("Your score must between 0 and 300!! " );
                        }
                        else {
                            if(temp.equals("Game 1")) {
                                sh.editScore(name, tempDate, "Game1", tempInt);
                            }

                            else if(temp.equals("Game 2")) {
                                sh.editScore(name, tempDate, "Game2", tempInt);
                            }

                            else if(temp.equals("Game 3")) {
                                sh.editScore(name, tempDate, "Game3", tempInt);
                            }
                            fromScore.setText(Integer.toString(tempInt));
                            toastMessage("The score has been change");
                        }
                    }
                    catch(Exception e) {
                        toastMessage("This is an invalid input");
                    }
                }
            }
        });

        // Display of the score of the Game selected
        game.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String item = (String) parent.getItemAtPosition(pos);
                int scoreInt;
                if(item.equals("Game 1")) {
                    scoreInt = sh.getGameOneScore(name, date.getText().toString());
                    System.out.println("scoreInt: " + scoreInt);
                    fromScore.setText(Integer.toString(scoreInt));
                    toastMessage("Game 1 " + date.getText().toString());
                }
                else if(item.equals("Game 2")) {
                    scoreInt = sh.getGameTwoScore(name, date.getText().toString());
                    fromScore.setText(Integer.toString(scoreInt));
                    toastMessage("Game 2 " + date.getText().toString());
                }
                else if(item.equals("Game 3")) {
                    scoreInt = sh.getGameThreeScore(name, date.getText().toString());
                    fromScore.setText(Integer.toString(scoreInt));
                    toastMessage("Game 3 " + date.getText().toString());
                }

                System.out.println("from score has attempted to change");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    /**
     * set the date for the date EditText
     */
    private void setDate() {
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);

//        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        String date = format.format(cldr.)

        picker = new DatePickerDialog(editStats.this, new DatePickerDialog.OnDateSetListener() {

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
                date.setText(yearS + "-" + monthS + "-" + dayS);

            }
        }, year, month, day);
        picker.show();

//        String item = game.getSelectedItem().toString();
//        int scoreInt;
//        if(item.equals("Game 1")) {
//            scoreInt = sh.getGameOne(item, date.getText().toString());
//            System.out.println("scoreInt: " + scoreInt);
//            fromScore.setText(Integer.toString(scoreInt));
//            toastMessage("Game 1 " + date.getText().toString());
//        }
//        else if(item.equals("Game 2")) {
//            scoreInt = sh.getGameTwo(item, date.getText().toString());
//            fromScore.setText(Integer.toString(scoreInt));
//            toastMessage("Game 2 " + date.getText().toString());
//        }
//        else if(item.equals("Game 3")) {
//            scoreInt = sh.getGameThree(item, date.getText().toString());
//            fromScore.setText(Integer.toString(scoreInt));
//            toastMessage("Game 3 " + date.getText().toString());
//        }
//
//        System.out.println("from score has attempted to change");
    }

    /**
     * customizable toast
     * @param message
     */
    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}
