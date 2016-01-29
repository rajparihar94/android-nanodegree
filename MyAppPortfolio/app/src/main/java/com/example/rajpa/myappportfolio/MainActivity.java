package com.example.rajpa.myappportfolio;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button spotify_streamer,scores_app,libary_app,built_it_bigger,bacon_reader,capstone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spotify_streamer = (Button)findViewById(R.id.spotify_streamer);
        scores_app = (Button)findViewById(R.id.scores_app);
        libary_app = (Button)findViewById(R.id.libary_app);
        built_it_bigger = (Button)findViewById(R.id.built_it_bigger);
        bacon_reader = (Button)findViewById(R.id.bacon_reader);
        capstone = (Button)findViewById(R.id.capstone);

        spotify_streamer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"This button will launch spotify streamer",Toast.LENGTH_SHORT).show();
            }
        });
        scores_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"This button will launch scores app",Toast.LENGTH_SHORT).show();
            }
        });
        libary_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"This button will launch library app",Toast.LENGTH_SHORT).show();
            }
        });
        built_it_bigger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"This button will launch built it bigger",Toast.LENGTH_SHORT).show();
            }
        });
        bacon_reader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"This button will launch bacon reader",Toast.LENGTH_SHORT).show();
            }
        });
        capstone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"This button will launch my capstone app",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
