package com.example.bowlstats;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;

public class recordStats extends AppCompatActivity {

    private Button record;
    private TextView nameToRecord;
    sqlHelper sh = new sqlHelper(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_stats);
        record = findViewById(R.id.record);
        nameToRecord = findViewById(R.id.name);

        Intent oldIntent = getIntent();
        String message = oldIntent.getStringExtra("playersList");
        nameToRecord.setText(message);

        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sh.recordScore("mark", 300);

                Intent myIntent = new Intent(recordStats.this, MainActivity.class);
                startActivity(myIntent);
            }
        });
    }
}
